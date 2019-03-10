package com.example.searcher.service;

import com.example.searcher.entities.Basket;
import com.example.searcher.entities.Item;
import com.example.searcher.entities.Medicine;
import com.example.searcher.repository.ItemRepository;
import com.example.searcher.repository.MedicineRepository;
import com.example.searcher.repository.PharmacyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static ch.qos.logback.core.joran.spi.ConsoleTarget.findByName;

@Service
public class BasketService {
    @Autowired
    MedicineRepository medicineRepository;

    @Autowired
    PharmacyRepository pharmacyRepository;

    @Autowired
    ItemRepository itemRepository;






}
