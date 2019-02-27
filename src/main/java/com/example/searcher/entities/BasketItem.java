package com.example.searcher.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class BasketItem {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @ManyToOne
    private Basket basket;
    @ManyToOne
    private Item item;
}
