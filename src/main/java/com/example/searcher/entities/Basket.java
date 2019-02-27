package com.example.searcher.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@Entity
public class Basket {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id_basket;

   @OneToMany(mappedBy = "basket",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    List<BasketItem> basketItems;

    @ManyToOne(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    private Pharmacy pharmacy;

    private BigDecimal cost = getCost(basketItems);


    private BigDecimal getCost(List<BasketItem> items) {
        BigDecimal result = items
                .stream()
                .map((basketItem) -> basketItem.getItem().getPrice())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return result;
    }

}
