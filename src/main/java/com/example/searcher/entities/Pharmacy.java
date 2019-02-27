package com.example.searcher.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Entity
@Data
public class Pharmacy {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String nameOfStore;

   public Pharmacy(){}

   public Pharmacy(String nameOfStore) {
       this.nameOfStore = nameOfStore;
   }
}
