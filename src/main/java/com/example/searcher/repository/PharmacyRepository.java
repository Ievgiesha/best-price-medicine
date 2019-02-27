package com.example.searcher.repository;

import com.example.searcher.entities.Pharmacy;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PharmacyRepository extends CrudRepository<Pharmacy,Long> {
}
