package com.example.searcher.entities;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Medicine medicine;

    @ManyToOne(fetch = FetchType.EAGER)
    private Pharmacy pharmacy;

    @ManyToMany(mappedBy = "items")
    private List<Basket> basket;

    private BigDecimal price;

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", medicine=" + medicine +
                ", pharmacy=" + pharmacy +
                ", price=" + price +
                '}';
    }
}
