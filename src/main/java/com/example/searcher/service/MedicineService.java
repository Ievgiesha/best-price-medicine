package com.example.searcher.service;

import com.example.searcher.entities.Medicine;
import com.example.searcher.repository.MedicineRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
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

    public List<String> findStringOfMedicine(String medicineName)throws  IOException{
        List<String> stringList;
        Elements e = new Elements();
        stringList = findMedicineListOfMedicine(medicineName);
        for(Element item : e){
            stringList.add(item.text());

        }
        for(String s : stringList){
        System.out.println(s);}
        return stringList;
    }

    public List<String> findMedicineListOfMedicine (String enterMedicine)throws IOException {
        Elements elementsMedicines;
        List<String> elementsList = new ArrayList<>();
        Document doc = (Document) Jsoup.connect("https://www.ceneo.pl/;szukaj-" + enterMedicine)
               // .data("Paracetamol", "Ziko")
                .userAgent("Mozilla")
                .cookie("auth", "token")
                .timeout(3000)
                .get();
        elementsMedicines = doc.select("a.go-to-product");
        for (Element el : elementsMedicines.select("a")) {
            elementsList.add(el.text());
            // System.out.println(el.select("a.go-to-product").text());
        }

        return elementsList;
    }
}
