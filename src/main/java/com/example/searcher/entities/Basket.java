package com.example.searcher.entities;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Basket {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="basket_item",
    joinColumns = @JoinColumn(name="basket_id",referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name="item_id", referencedColumnName = "id"))
    private List<Item> items;

    @ManyToOne
    @JoinColumn(name="pharmacy_id")
    private Pharmacy pharmacy;


    private BigDecimal price;




}
