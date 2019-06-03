package com.example.searcher.entities;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "Basket_id")
    private Basket basket;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Medicine medicine;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Pharmacy pharmacy;

    private BigDecimal price;

    @Override
    public String toString() {
        return "Item{" + System.lineSeparator() +
                "      id=" + id +
                ", medicine=" + medicine +
                ", pharmacy=" + pharmacy +
                ", price=" + price +
                '}';
    }


}
