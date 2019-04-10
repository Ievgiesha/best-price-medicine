package com.example.searcher.service;

import com.example.searcher.entities.Item;
import com.example.searcher.entities.Medicine;
import com.example.searcher.entities.Pharmacy;
import com.example.searcher.repository.MedicineRepository;
import com.example.searcher.repository.PharmacyRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MedicineService {
    @Autowired
    MedicineRepository medicineRepository;
    @Autowired
    PharmacyRepository pharmacyRepository;



    public List<Medicine> findMedicine(List<String> medicineNames) throws Exception {
        List<Medicine> medicines = new ArrayList<>();
        for (String medicineName : medicineNames) {
            if (medicineRepository.findByName(medicineName).isPresent()) {
                medicines.add(medicineRepository.findByName(medicineName).get());
            }
        }
        return medicines;
    }

    public List<Item> findItemForMedicine(String fullNameOfMedicine) throws Exception {
        String urlAddressForMedicine = findURLAddressForMedicine(fullNameOfMedicine);
        Document doc = findDocument(urlAddressForMedicine);
        List<Pharmacy> pharmacyList = findListOfPharmacy(doc);
        List<BigDecimal> priceList = findPriceOfMedicine(doc);
        List<Item> itemList = createListOfItems(fullNameOfMedicine, pharmacyList, priceList);
        return itemList;
    }

    public List<String> findStringOfMedicine(String medicineName) throws IOException {
        List<String> stringList;
        Elements e = new Elements();
        stringList = findShopListFullNamesOfMedicine(medicineName);
        for (Element item : e) {
            stringList.add(item.text());

        }
        for (String s : stringList) {
            System.out.println(s);
        }
        return stringList;
    }

    public List<String> findShopListFullNamesOfMedicine(String enterMedicine) throws IOException {
        Elements elementsMedicines;
        List<String> shopListFullNamesOfMedicine = new ArrayList<>();
        Document doc = (Document) Jsoup.connect("https://www.ceneo.pl/;szukaj-" + enterMedicine)
                .userAgent("Mozilla")
                .cookie("auth", "token")
                .timeout(3000)
                .get();
        elementsMedicines = doc.select("a.go-to-product");
        for (Element el : elementsMedicines.select("a")) {
            shopListFullNamesOfMedicine.add(el.text());
            // System.out.println(el.select("a.go-to-product").text());
        }

        return shopListFullNamesOfMedicine;
    }

    public String findURLAddressForMedicine(String name) throws IOException {

        Elements elementsUrl;
        String url = "";
        Document doc = (Document) Jsoup.connect("https://www.ceneo.pl/;szukaj-" + name)
                .userAgent("Mozilla")
                .cookie("auth", "token")
                .timeout(3000)
                .get();
        elementsUrl = doc.select("a.js_seoUrl");
        for (Element element : elementsUrl) {
            String check = element.attr("title");
            if (check.equals(name)) {
                url = element.attr("abs:href");
                break;
            }
        }
        // System.out.println("Method findUrlAddressFOrMedicine "+url);
        return url;
    }

    public Document findDocument(String urlAddressForMedicine) throws IOException {
        Document doc = (Document) Jsoup.connect("https://www.ceneo.pl/" + urlAddressForMedicine)
                .userAgent("Mozilla")
                .cookie("auth", "token")
                .timeout(3000)
                .get();
        return doc;
    }

    public List<Pharmacy> findListOfPharmacy(Document doc) throws IOException {
        List<Pharmacy> pharmacyList = new ArrayList<>();
        Elements elementsAddress;
        elementsAddress = doc.select("a.js_product-offer-link");
        for (Element el : elementsAddress.select(" a")) {
            Pharmacy pharmacy = new Pharmacy();
            pharmacy.setName(el.text().substring(9));
            // System.out.println(pharmacy.toString());
            pharmacyRepository.save(pharmacy);
            pharmacyList.add(pharmacy);
        }
        return pharmacyList;
    }

    List<BigDecimal> findPriceOfMedicine(Document doc) {
        Elements elementsPrice;
        List<BigDecimal> priceList = new ArrayList<>();
        // elementsPrice = doc.select("a.go-to-shop");
        // elementsPrice = doc.select("span.price");
        elementsPrice = doc.select("a.product-price");
        for (Element el : elementsPrice.select("span[class=price]")) {
            String price = el.val("penny").text();
            BigDecimal prices = new BigDecimal(price.replaceAll(" ", "").replaceAll(",", "."));
        priceList.add(prices);
        }
        return priceList;
    }

    private List<Item> createListOfItems(String name, List<Pharmacy> pharmacyList, List<BigDecimal> priceList) throws IOException {
        List<Item> itemList = new ArrayList<>();
        Medicine medicine = updateMedicine(name);
        if(pharmacyList.size() != priceList.size()){
            System.out.println("ERROR!  Price list size is different from medicine list size!");
            System.out.println("Size of pharmacyList  "+ pharmacyList.size()+"    Size of PriceList   "+priceList.size());
        }
        int sizeOfLoop;
        if (pharmacyList.size() < priceList.size()) {
            sizeOfLoop = pharmacyList.size();
        } else {
            sizeOfLoop = priceList.size();
        }
        for (int k = 0; k < sizeOfLoop; k++) {
            Item item = new Item();
            item.setMedicine(medicine);
            item.setPharmacy(pharmacyList.get(k));
            item.setPrice(priceList.get(k));
            itemList.add(item);
            System.out.println(item.toString());
        }
        return itemList;
    }

    private Medicine updateMedicine(String name) {
        Optional<Medicine> maybeMedicine = medicineRepository.findByName(name);
        if(maybeMedicine.isEmpty()) {
            Medicine medicine = new Medicine();
            medicine.setName(name);
            medicineRepository.save(medicine);
            return medicine;
        }else{
            return maybeMedicine.get();
        }
    }
}
