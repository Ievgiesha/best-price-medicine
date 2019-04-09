package com.example.searcher.controllers;

import com.example.searcher.entities.Item;
import com.example.searcher.service.MedicineRequestHolder;
import com.example.searcher.service.MedicineService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medicines")
public class MedicineController {

    private final MedicineService medicineService;

    public MedicineController(MedicineService medicineService) {
        this.medicineService = medicineService;
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE},
            path = "/fullNameOfMedicine")
    public List<Item> findListItemsForMedicine(@RequestBody MedicineRequestHolder medicineRequestHolder) throws Exception {
        return medicineService.findItemForMedicine(medicineRequestHolder);
    }

    @PostMapping
    public List<String> collectingListOfMedicine(@RequestBody String medicineName) throws Exception {
        return medicineService.findStringOfMedicine(medicineName);
    }
}
