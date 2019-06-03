package com.example.searcher.repository;

import com.example.searcher.entities.Item;
import com.example.searcher.entities.Medicine;
import com.example.searcher.entities.Pharmacy;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends CrudRepository<Item, Long> {
    List<Item> findByMedicine(Medicine medicine);

    Optional<Item> findByMedicineAndPharmacy(Medicine medicine, Pharmacy pharmacy);
}
