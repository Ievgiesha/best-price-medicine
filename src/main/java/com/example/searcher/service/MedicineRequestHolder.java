package com.example.searcher.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@Component
public class MedicineRequestHolder {
    String simpleName;
    String fullNameOfMedicine;
    List<String> medicineNames;

    public MedicineRequestHolder(String simpleName, String fullNameOfMedicine) {
        this.simpleName = simpleName;
        this.fullNameOfMedicine = fullNameOfMedicine;
    }
    public MedicineRequestHolder(List<String> medicineNames){
        this.medicineNames = medicineNames;
    }

    public MedicineRequestHolder() {
    }

    public String getSimpleName() {
        return simpleName;
    }

    public void setSimpleName(String simpleName) {
        this.simpleName = simpleName;
    }

    public String getFullNameOfMedicine() {
        return fullNameOfMedicine;
    }

    public void setFullNameOfMedicine(String fullNameOfMedicine) {
        this.fullNameOfMedicine = fullNameOfMedicine;
    }

    public List<String> getMedicineNames() {
        return medicineNames;
    }

    public void setMedicineNames(List<String> medicineNames) {
        this.medicineNames = medicineNames;
    }
}
