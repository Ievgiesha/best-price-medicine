package com.example.searcher.entities;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    private Medicine medicine;

    @ManyToOne
    private Pharmacy pharmacy;

    @ManyToMany(mappedBy = "items", fetch = FetchType.LAZY)
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
