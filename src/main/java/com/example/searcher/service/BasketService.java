package com.example.searcher.service;

import com.example.searcher.entities.Basket;
import com.example.searcher.entities.Item;
import com.example.searcher.repository.ItemRepository;
import com.example.searcher.repository.MedicineRepository;
import com.example.searcher.repository.PharmacyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class BasketService {
 @Autowired
    MedicineRepository medicineRepository;

 @Autowired
    PharmacyRepository pharmacyRepository;

 @Autowired
    ItemRepository itemRepository;


    private BigDecimal getCost(List<Item> items) {
        BigDecimal result = items
                .stream()
                .map((item) -> item.getPrice())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return result;
    }

  







}
