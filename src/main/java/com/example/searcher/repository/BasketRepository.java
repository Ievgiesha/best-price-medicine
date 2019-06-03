package com.example.searcher.repository;

import com.example.searcher.entities.Basket;
import com.example.searcher.entities.Item;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BasketRepository extends CrudRepository<Basket,Long> {
}
