package com.example.searcher;

import com.example.searcher.entities.Medicine;
import com.example.searcher.repository.MedicineRepository;
import com.example.searcher.service.MedicineService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.when;

public class MedicineServiceTest {
    @Autowired
    MedicineRepository medicineRepository;
    @Autowired
    MedicineService medicineService;

    @Test
    public void findMedicineTest() throws Exception {
        //given
        List<String> list = new ArrayList<>();
        list.add("Paracetamol");
        list.add("Nasivin");
        Medicine paracetamol = new Medicine();
        Medicine nasivin = new Medicine();
        List<Medicine> medicineList = new ArrayList<>();
        medicineList.add(paracetamol);
        medicineList.add(nasivin);

        when(medicineRepository.findByName("Paracetamol")).thenReturn(Optional.of(paracetamol));
        when(medicineRepository.findByName("Nasivin")).thenReturn(Optional.of(nasivin));
        //when
        List<Medicine> result = medicineService.findMedicine(list);
        //then
        assertTrue(result.contains(paracetamol));
        assertTrue(result.contains(nasivin));
    }

}
