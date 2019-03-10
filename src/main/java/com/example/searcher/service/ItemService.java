package com.example.searcher.service;

import com.example.searcher.entities.Basket;
import com.example.searcher.entities.Item;
import com.example.searcher.entities.Medicine;
import com.example.searcher.entities.Pharmacy;
import com.example.searcher.repository.BasketRepository;
import com.example.searcher.repository.ItemRepository;
import com.example.searcher.repository.MedicineRepository;
import com.example.searcher.repository.PharmacyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class ItemService {

    @Autowired
    MedicineRepository medicineRepository;
    @Autowired
    PharmacyRepository pharmacyRepository;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    BasketRepository basketRepository;

    // 1. find medicine for every string provided
    public List<Medicine> findMedicine(List<String> medicineNames) throws Exception {
        List<Medicine> medicines = new ArrayList<>();
        for (String medicineName : medicineNames) {
            if (medicineRepository.findByName(medicineName).isPresent()) {
                medicines.add(medicineRepository.findByName(medicineName).get());
            }
        }
        return medicines;
    }

    // 2. for each pharmacy call method 1. and then 3. and return cheapest (ore 3 cheapest baskets)
    public List<Basket> findCheapestBaskets(List<String> medicineNames) throws Exception {
        List<Basket> basketList = new ArrayList<>();
        List<Medicine> medicineList = findMedicine(medicineNames);
        int count = 0;
        for (Pharmacy pharmacy : pharmacyRepository.findAll()) {
            Basket basket = createBasketForPharmacy(pharmacy, medicineList);
            basketList.add(basket);
        }

        for (int i = 0; i < basketList.size(); i++) {
            for (int j = 0; j < basketList.get(i).getItems().size(); j++) {
                BigDecimal temp = basketList.get(i).getCost();
                basketList.get(i).setCost(temp);
            }
        }
        return basketList.stream().sorted((b1, b2) -> b1.compareTo(b2)).collect(Collectors.toList());
    }

    // 3. gather basket of items and calculate basket cost
    public Basket createBasketForPharmacy(Pharmacy pharmacy, List<Medicine> medicines) {
        Basket basket = new Basket();
        List<Item> itemList = new ArrayList<>();
        for (Medicine medicine : medicines) {
            Optional<Item> item = itemRepository.findByMedicineAndPharmacy(medicine, pharmacy);
            if (item.isPresent()) {
                itemList.add(item.get());
            } else {
                return null;
            }
        }
        basket.setItems(itemList);
        basket.setPharmacy(pharmacy);
        basket.setCost(getCost(itemList));
        basketRepository.save(basket);
        return basket;
    }

    private BigDecimal getCost(List<Item> items) {
        BigDecimal result = items
                .stream()
                .map((item) -> item.getPrice())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return result;
    }


}
