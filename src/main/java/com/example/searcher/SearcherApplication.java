package com.example.searcher;

import com.example.searcher.entities.Medicine;
import com.example.searcher.repository.MedicineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SearcherApplication implements CommandLineRunner {

    @Autowired
    private MedicineRepository medicineRepository;

    public static void main(String[] args) {
        SpringApplication.run(SearcherApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Medicine paracetamol = new Medicine();
        paracetamol.setName("Paracetamol");
        medicineRepository.save(paracetamol);

        System.out.println("Medicines "+medicineRepository.findAll());
    }
}

