package com.example.searcher.service;

import com.example.searcher.entities.Item;
import com.example.searcher.entities.Medicine;
import com.example.searcher.entities.Pharmacy;
import com.example.searcher.repository.ItemRepository;
import com.example.searcher.repository.MedicineRepository;
import com.example.searcher.repository.PharmacyRepository;
import lombok.extern.log4j.Log4j;
import org.apache.log4j.Logger;
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

@Log4j
@Service
public class MedicineService {
    @Autowired
    MedicineRepository medicineRepository;
    @Autowired
    PharmacyRepository pharmacyRepository;
    @Autowired
    ItemRepository itemRepository;

    final static Logger logger = Logger.getLogger(MedicineService.class.getName());

    public List<Medicine> findMedicine(List<String> medicineNames) throws Exception {
        List<Medicine> medicines = new ArrayList<>();
        for (String medicineName : medicineNames) {
            if (medicineRepository.findByName(medicineName).isEmpty()) {
                Medicine medicine = new Medicine(medicineName);
                medicineRepository.save(medicine);
                medicines.add(medicine);
            } else {
                medicines.add(medicineRepository.findByName(medicineName).get());
            }
        }
        return medicines;
    }

    public List<Item> findItemForMedicine(MedicineRequestHolder medicineRequestHolder) throws Exception {
        String name = medicineRequestHolder.getSimpleName();
        String fullNameOfMedicine = medicineRequestHolder.getFullNameOfMedicine();

        try {
            String urlAddressForMedicine = findURLAddressForMedicine(name, fullNameOfMedicine);
            Document doc = findDocument(urlAddressForMedicine);
            List<Pharmacy> pharmacyList = updatePharmaciesInDatabase(doc);
            List<BigDecimal> priceList = findPriceOfMedicine(doc);
            updateListOfItemsInDatabase(fullNameOfMedicine, pharmacyList, priceList);
        } catch (Exception e) {
            logger.error("Something went wrong during item list update from Ceneo", e);
        }

        Optional<Medicine> maybeMedicine = medicineRepository.findByName(fullNameOfMedicine);
        if (maybeMedicine.isPresent()) {
            return itemRepository.findByMedicine(maybeMedicine.get());
        } else {
            return new ArrayList<>();
        }
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

    public List<String> findShopListFullNamesOfMedicine(String simpleNameOfMedicine) throws IOException {
        Elements elementsMedicines;
        List<String> shopListFullNamesOfMedicine = new ArrayList<>();
        Document doc = findDocumentForSimpleNameOfMedicine(simpleNameOfMedicine);
        elementsMedicines = doc.select("a.go-to-product");
        for (Element el : elementsMedicines.select("a")) {
            shopListFullNamesOfMedicine.add(el.text());
            // System.out.println(el.select("a.go-to-product").text());
        }

        return shopListFullNamesOfMedicine;
    }

    public Document findDocumentForSimpleNameOfMedicine(String simpleNameOfMedicine) throws IOException {
        Document doc = (Document) Jsoup.connect("https://www.ceneo.pl/;szukaj-" + simpleNameOfMedicine)
                .userAgent("Mozilla")
                .cookie("auth", "token")
                .timeout(7000)
                .get();
        return doc;
    }


    public String findURLAddressForMedicine(String name, String fullNameOfMedicine) throws IOException {
        Elements elementsUrl;
        String url = "";
        Document doc = findDocumentForSimpleNameOfMedicine(name);
        elementsUrl = doc.select("a.js_seoUrl");
        for (Element element : elementsUrl) {
            String check = element.attr("title");
            if (check.equals(fullNameOfMedicine)) {
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

    public List<Pharmacy> updatePharmaciesInDatabase(Document doc) throws IOException {
        List<Pharmacy> pharmacyList = new ArrayList<>();
        Elements elementsAddress;
        elementsAddress = doc.select("a.js_product-offer-link");
        for (Element el : elementsAddress.select(" a")) {
            String pharmacyName = el.text().substring(9);
            Optional<Pharmacy> maybePharmacy = pharmacyRepository.findByName(pharmacyName);
            if (maybePharmacy.isPresent()) {
                pharmacyList.add(maybePharmacy.get());
            } else {
                Pharmacy pharmacy = new Pharmacy();
                pharmacy.setName(pharmacyName);
                pharmacyRepository.save(pharmacy);
                pharmacyList.add(pharmacy);
            }
        }
        return pharmacyList;
    }

    List<BigDecimal> findPriceOfMedicine(Document doc) {
        Elements elementsPrice;
        List<BigDecimal> priceList = new ArrayList<>();

        elementsPrice = doc.select("a.product-price");
        for (Element el : elementsPrice.select("span[class=price]")) {
            String price = el.val("penny").text();
            BigDecimal prices = new BigDecimal(price.replaceAll(" ", "").replaceAll(",", "."));
            priceList.add(prices);
        }
        return priceList;
    }

    private void updateListOfItemsInDatabase(String name, List<Pharmacy> pharmacyList, List<BigDecimal> priceList) throws
            IOException {
        Medicine medicine = updateMedicine(name);
        if (pharmacyList.size() != priceList.size()) {
            System.out.println("ERROR!  Price list size is different from medicine list size!");
            System.out.println("Size of pharmacyList  " + pharmacyList.size() + "    Size of PriceList   " + priceList.size());
        }
        int sizeOfLoop;
        if (pharmacyList.size() < priceList.size()) {
            sizeOfLoop = pharmacyList.size();
        } else {
            sizeOfLoop = priceList.size();
        }
        for (int k = 0; k < sizeOfLoop; k++) {
            Optional<Item> maybeItem = itemRepository.findByMedicineAndPharmacy(medicine, pharmacyList.get(k));
            if (maybeItem.isPresent()) {
                Item found = maybeItem.get();
                found.setPrice(priceList.get(k));
                itemRepository.save(found);
            } else {
                Item item = new Item();
                item.setMedicine(medicine);
                item.setPharmacy(pharmacyList.get(k));
                item.setPrice(priceList.get(k));
                itemRepository.save(item);
            }
        }
    }

    private Medicine updateMedicine(String name) {
        Optional<Medicine> maybeMedicine = medicineRepository.findByName(name);
        if (maybeMedicine.isEmpty()) {
            Medicine medicine = new Medicine();
            medicine.setName(name);
            medicineRepository.save(medicine);
            return medicine;
        } else {
            return maybeMedicine.get();
        }
    }
}
