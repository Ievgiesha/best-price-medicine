package com.example.searcher.entities;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
public class Basket implements Comparable<Basket> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(mappedBy = "basket")
    private List<Item> items;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pharmacy_id")
    private Pharmacy pharmacy;

    private BigDecimal cost;

    @Override
    public int compareTo(Basket basket) {
        return this.getCost().compareTo(basket.getCost());
    }

    @Override
    public String toString() {
        return "Basket{" + System.lineSeparator() +
                "id=" + id +
                ", items=" + items +
                ", pharmacy=" + pharmacy +
                ", cost=" + cost +
                '}' + System.lineSeparator();
    }
}
