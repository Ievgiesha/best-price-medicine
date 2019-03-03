package com.example.searcher.repository;

import com.example.searcher.entities.Basket;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BasketRepository extends CrudRepository<Basket,Long> {
}
