package com.example.searcher;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SearcherApplication implements CommandLineRunner {

   /* @Autowired
    private MedicineRepository medicineRepository;

    @Autowired
    private PharmacyRepository pharmacyRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private BasketRepository basketRepository;

    @Autowired
    ItemService itemService;*/


    public static void main(String[] args) {
        SpringApplication.run(SearcherApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

      /*  Medicine paracetamol = new Medicine();
        paracetamol.setName("Paracetamol");
        medicineRepository.save(paracetamol);
        Medicine nasivin = new Medicine();
        nasivin.setName("Nasivin");
        medicineRepository.save(nasivin);

        Pharmacy ziko = new Pharmacy();
        ziko.setName("Ziko");
        pharmacyRepository.save(ziko);
        Pharmacy allecco = new Pharmacy();
        allecco.setName("Allecco");
        pharmacyRepository.save(allecco);

        Item paraZiko = new Item();
        paraZiko.setMedicine(paracetamol);
        paraZiko.setPharmacy(ziko);
        paraZiko.setPrice(BigDecimal.valueOf(0.45));
        itemRepository.save(paraZiko);
        Item nasiZiko = new Item();
        nasiZiko.setMedicine(nasivin);
        nasiZiko.setPharmacy(ziko);
        nasiZiko.setPrice(BigDecimal.valueOf(1.95));
        itemRepository.save(nasiZiko);
        Item paraAllecco = new Item();
        paraAllecco.setMedicine(paracetamol);
        paraAllecco.setPharmacy(allecco);
        paraAllecco.setPrice(BigDecimal.valueOf(0.55));
        itemRepository.save(paraAllecco);
        Item nasiAllecco = new Item();
        nasiAllecco.setMedicine(nasivin);
        nasiAllecco.setPharmacy(allecco);
        nasiAllecco.setPrice(BigDecimal.valueOf(2.05));
        itemRepository.save(nasiAllecco);


       /*  Basket firstBasket = new Basket();
         List<Item> itemsFirst = new ArrayList<>();
         itemsFirst.add(paraZiko);
         itemsFirst.add(nasiZiko);
         firstBasket.setItems(itemsFirst);
         firstBasket.setPharmacy(ziko);*/





        System.out.println("Medicines " + medicineRepository.findAll());
        System.out.println("Pharmacy " + pharmacyRepository.findAll());
        System.out.println("Item " + itemRepository.findAll());
        //System.out.println("Basket "+firstBasket.toString());
    }
}

