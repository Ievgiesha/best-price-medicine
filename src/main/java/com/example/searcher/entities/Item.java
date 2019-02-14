package com.example.searcher.entities;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    private Medicine medicine;

    @ManyToOne
    private Pharmacy pharmacy;

    private BigDecimal price;
}
