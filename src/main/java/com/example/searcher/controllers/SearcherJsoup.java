package com.example.searcher.controllers;

import com.example.searcher.entities.Item;
import com.example.searcher.entities.Pharmacy;
import com.example.searcher.repository.ItemRepository;
import com.example.searcher.repository.PharmacyRepository;
import com.example.searcher.service.MedicineService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class SearcherJsoup {
    ItemRepository itemRepository;
    PharmacyRepository pharmacyRepository;
    List<Item> itemList = new ArrayList<>();
    List<Pharmacy> pharmacyList = new ArrayList<>();
    List<BigDecimal> priceList = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        MedicineService medicineService = new MedicineService();
        SearcherJsoup searcherJsoup = new SearcherJsoup();
        String urlAddressForMedicine = "61604644#tab=click";
        Item item = new Item();
        String paracetamol = "paracetamol";
        String nasivin = "nasivin";
        String pantenol = "pantenol";
        //  searcherJsoup.findShopListOfMedicine(pantenol);
        // searcherJsoup.findMedicineListOfMedicine(paracetamol);
        // medicineService.findStringOfMedicine(nasivin);
        // medicineService.findStringOfMedicine(pantenol);
        // Document doc = searcherJsoup.findDocument(urlAddressForMedicine);
        //searcherJsoup.findAddressOfMedicine(doc);
        // searcherJsoup.findPriceOfMedicine(doc);
        // searcherJsoup.findURLAddressForMedicine("Pantenol Kojąca pianka do twarzy i ciała dla dzieci 150ml");
        searcherJsoup.controllerMethod("pantenol");
    }

    //1  find lis
    public void controllerMethod(String simpleName) throws IOException {
        SearcherJsoup searcherJsoup = new SearcherJsoup();
        List<String> medicineList = new ArrayList<>();
        List<String> medicineListTwo = new ArrayList<>();
        List<BigDecimal> listOfBigDecimal = new ArrayList<>();
        medicineList = findShopListOfMedicine(simpleName);
        String urlAddressForMedicine = searcherJsoup.findURLAddressForMedicine("Pantenol Kojąca pianka do twarzy i ciała dla dzieci 150ml");
        Document doc = searcherJsoup.findDocument(urlAddressForMedicine);
        listOfBigDecimal = findPriceOfMedicine(doc);
    }

    public List<String> findShopListOfMedicine(String simpleName) throws IOException {
        Elements elementsMedicines, elementsHref;
        List<String> shopListFullNamesOfMedicine = new ArrayList<>();

        Document doc = (Document) Jsoup.connect("https://www.ceneo.pl/;szukaj-" + simpleName)
                .userAgent("Mozilla")
                .cookie("auth", "token")
                .timeout(3000)
                .get();
        elementsMedicines = doc.select("a.go-to-product");
        //TODO filter elementsMedicines to get URL of medicine with name full medicine name

        //TODO go to product page, and get there list of pharmacies-prices (table)
        //  System.out.println("Elements: "+elementsMedicines.text());
        for (Element el : elementsMedicines.select("a")) {
            shopListFullNamesOfMedicine.add(el.text());
            // System.out.println("Method findShopListOfMedicine "+el.text()); //List of fullNamesOfMedicines
        }
        return shopListFullNamesOfMedicine;
    }

    Document findDocument(String urlAddressForMedicine) throws IOException {
        Document doc = (Document) Jsoup.connect("https://www.ceneo.pl/" + urlAddressForMedicine)
                .userAgent("Mozilla")
                .cookie("auth", "token")
                .timeout(3000)
                .get();
        return doc;


    }

    List<Pharmacy> findAddressOfMedicine(Document doc) throws IOException {
        Elements elementsAddress;
        elementsAddress = doc.select("a.js_product-offer-link");
        for (Element el : elementsAddress.select(" a")) {
            Pharmacy pharmacy = new Pharmacy();
            pharmacy.setName(el.text().substring(9));
            pharmacyRepository.save(pharmacy);
            pharmacyList.add(pharmacy);
        }
        return pharmacyList;
    }

    List<BigDecimal> findPriceOfMedicine(Document doc) {
        Elements elementsPrice;
        // elementsPrice = doc.select("a.go-to-shop");
        // elementsPrice = doc.select("span.price");
        elementsPrice = doc.select("a.product-price");
        for (Element el : elementsPrice.select("span[class=price]")) {
            String price = el.val("penny").text();
            BigDecimal prices = new BigDecimal(price.replaceAll(" ", "").replaceAll(",", "."));
            System.out.println(prices);
        }
        return priceList;
    }

    String findURLAddressForMedicine(String name) throws IOException {
        Element elementsUrl;
        Element elementsUrl2;
        Document doc = (Document) Jsoup.connect("https://www.ceneo.pl/;szukaj-" + name)
                .userAgent("Mozilla")
                .cookie("auth", "token")
                .timeout(3000)
                .get();
        elementsUrl = doc.select("a.js_seoUrl").first();
        String url = elementsUrl.attr("abs:href");
        // System.out.println("Method findUrlAddressFOrMedicine "+url);
        return url;
    }
}