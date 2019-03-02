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
@Entity
public class Basket {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToMany
    @JoinTable(name="basket_item",
    joinColumns = @JoinColumn(name="basket_id",referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name="item_id", referencedColumnName = "id"))
    private List<Item> items;

    @ManyToOne
    @JoinColumn(name="pharmacy_id")
    private Pharmacy pharmacy;

    private BigDecimal price;

    private BigDecimal getCost(List<Item> items) { //TODO move to service
        BigDecimal result = items
                .stream()
                .map((item) -> item.getPrice())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return result;
    }


}
