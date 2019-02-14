package com.example.searcher.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Basket {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id_basket;

    @ManyToMany(mappedBy = "item",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    List<Item> items;

    @OneToOne(mappedBy = "pharmacy",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    private Pharmacy pharmacy;

    private BigDecimal cost = getCost(items);


    private BigDecimal getCost(List<Item> items) {
        BigDecimal result = items
                .stream()
                .map(Item::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return result;
    }

}
