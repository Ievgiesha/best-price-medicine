package com.example.searcher.controllers;

import com.example.searcher.service.MedicineService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medicines")
public class MedicineController {

    private final MedicineService medicineService;

    public MedicineController(MedicineService medicineService) {
        this.medicineService = medicineService;
    }

    @PostMapping
    public List<String> collectingListOfMedicine(@RequestBody String medicineName) throws Exception {
        return medicineService.findStringOfMedicine(medicineName);
    }


}
