package com.example.searcher.service;

import com.example.searcher.entities.Medicine;
import com.example.searcher.repository.MedicineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MedicineService {
    @Autowired
    MedicineRepository medicineRepository;

    public List<Medicine> findMedicine(List<String> medicineNames) throws Exception {
        List<Medicine> medicines = new ArrayList<>();
        for (String medicineName : medicineNames) {
            if (medicineRepository.findByName(medicineName).isPresent()) {
                medicines.add(medicineRepository.findByName(medicineName).get());
            }
        }
        return medicines;
    }
}
