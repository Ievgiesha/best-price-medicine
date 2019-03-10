package com.example.searcher.repository;

import com.example.searcher.entities.Medicine;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface MedicineRepository extends CrudRepository<Medicine, Long> {
     public Optional<Medicine> findByName(String name);
}
