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
public class BasketService {

    @Autowired
    MedicineRepository medicineRepository;
    @Autowired
    PharmacyRepository pharmacyRepository;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    BasketRepository basketRepository;
    @Autowired
    MedicineService medicineService;

    // 1. find medicine for every string provided
    // 2. for each pharmacy call method 1. and then 3. and return cheapest (ore 3 cheapest baskets)
    public List<Basket> findCheapestBaskets(MedicineRequestHolder medicineRequestHolder) throws Exception {
        List<String> medicineNames = medicineRequestHolder.getMedicineNames();
        List<Basket> basketList = new ArrayList<>();
        List<Medicine> medicineList = medicineService.findMedicine(medicineNames);
        for (Pharmacy pharmacy : pharmacyRepository.findAll()) {
            Optional<Basket> maybeBasket = createBasketForPharmacy(pharmacy, medicineList);
            maybeBasket.ifPresent(basketList::add);
        }
        return basketList.stream().sorted((b1, b2) -> b1.compareTo(b2)).collect(Collectors.toList());
    }

    // 3. gather basket of items and calculate basket cost
    public Optional<Basket> createBasketForPharmacy(Pharmacy pharmacy, List<Medicine> medicines) {
        Basket basket = new Basket();
        List<Item> itemList = new ArrayList<>();
        for (Medicine medicine : medicines) {
            Optional<Item> item = itemRepository.findByMedicineAndPharmacy(medicine, pharmacy);
            if (item.isPresent()) {
                itemList.add(item.get());
            } else {
                System.out.println("Error...., in itemRepository not item with Pharmacy - " + pharmacy + "and Medicine - " + medicine);
                return Optional.empty();
            }
        }
        basket.setItems(itemList);
        basket.setPharmacy(pharmacy);
        basket.setCost(calculateItemsCost(itemList));
        basketRepository.save(basket);
        return Optional.of(basket);
    }

    private BigDecimal calculateItemsCost(List<Item> items) {
        BigDecimal result = items
                .stream()
                .map((item) -> item.getPrice())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return result;
    }
}
