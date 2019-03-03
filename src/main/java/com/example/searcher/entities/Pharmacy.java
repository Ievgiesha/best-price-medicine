package com.example.searcher.entities;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
public class Pharmacy {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nameOfStore;

    @OneToMany(mappedBy = "pharmacy", fetch = FetchType.LAZY)
    private List<Basket> baskets;

    @Override
    public String toString() {
        return "Pharmacy{" +
                "id=" + id +
                ", nameOfStore='" + nameOfStore + '\'' +
                '}';
    }
}
