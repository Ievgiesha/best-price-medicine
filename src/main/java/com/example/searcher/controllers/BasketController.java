package com.example.searcher.controllers;

import com.example.searcher.entities.Basket;
import com.example.searcher.service.BasketService;
import com.example.searcher.service.MedicineRequestHolder;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/basket")
public class BasketController {

    private final BasketService basketService;

    public BasketController(BasketService basketService) {
        this.basketService = basketService;
    }



    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Basket> findBasketListByStringList(@RequestBody MedicineRequestHolder medicineNames) throws Exception {

        return basketService.findCheapestBaskets(medicineNames);
    }
}
