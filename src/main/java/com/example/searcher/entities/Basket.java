package com.example.searcher.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Basket {


    List<Item> items;


    private Pharmacy pharmacy;

    private BigDecimal cost;

    private BigDecimal getCost(List<Item> items) { //TODO move to service
        BigDecimal result = items
                .stream()
                .map((item) -> item.getPrice())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return result;
    }

}
