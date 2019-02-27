package com.example.searcher.repository;

import com.example.searcher.entities.Medicine;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface MedicineRepository extends CrudRepository<Medicine, Long> {
}
