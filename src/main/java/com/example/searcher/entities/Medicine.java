package com.example.searcher.entities;

import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@Entity
@ToString
@EqualsAndHashCode
public class Medicine {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;

    public Medicine(String name) {
        this.name = name;
    }

    public Medicine() {
    }
}
