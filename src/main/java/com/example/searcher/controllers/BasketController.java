package com.example.searcher.controllers;

import com.example.searcher.entities.Basket;
import com.example.searcher.service.ItemService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/basket")
public class BasketController {

    private final ItemService itemService;

    public BasketController(ItemService itemService) {
        this.itemService = itemService;
    }



    @PostMapping
    public List<Basket> findBasketListByStringList(@RequestBody List<String> medicineNames) throws Exception {

        return itemService.findCheapestBaskets(medicineNames);
    }
}
