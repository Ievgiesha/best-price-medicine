package com.example.searcher;

import com.example.searcher.entities.Basket;
import com.example.searcher.entities.Item;
import com.example.searcher.entities.Medicine;
import com.example.searcher.entities.Pharmacy;
import com.example.searcher.repository.BasketRepository;
import com.example.searcher.repository.ItemRepository;
import com.example.searcher.repository.MedicineRepository;
import com.example.searcher.repository.PharmacyRepository;
import com.example.searcher.service.BasketService;
import com.example.searcher.service.MedicineRequestHolder;
import com.example.searcher.service.MedicineService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static junit.framework.Assert.assertSame;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class BasketServiceTest {
    @Mock
    private MedicineRepository medicineRepository;
    @InjectMocks
    private BasketService basketService;
    @Mock
    ItemRepository itemRepository;
    @Mock
    PharmacyRepository pharmacyRepository;
    @Mock
    BasketRepository basketRepository;
    @Autowired
    MedicineService medicineService;


    @Test
    public void creteBasketForPharmacyTest() {
        //given
        Pharmacy ziko = new Pharmacy();
        ziko.setName("Ziko");
        Basket basket = new Basket();
        Item item = new Item();
        Medicine paracetamol = new Medicine();
        paracetamol.setName("Paracetamol");
        item.setMedicine(paracetamol);
        item.setPharmacy(ziko);
        item.setPrice(BigDecimal.valueOf(0.41));
        List<Medicine> listMedicine = new ArrayList<>();
        listMedicine.add(paracetamol);
        List<Item> itemList = new ArrayList<>();
        itemList.add(item);
        basket.setItems(itemList);

        when(itemRepository.findByMedicineAndPharmacy(paracetamol, ziko)).thenReturn(Optional.of(item));
        //when
        Optional<Basket> result = basketService.createBasketForPharmacy(ziko, listMedicine);
        //then
        assertSame(result, basket);

    }

    @Test
    public void findCheapestBasketTest() throws Exception {
        //given
        MedicineRequestHolder medicineRequestHolder = new MedicineRequestHolder();
        List<String> stringList = new ArrayList<>();
        stringList.add("Paracetamol");
        stringList.add("Nasivin");
        Pharmacy ziko = new Pharmacy();
        ziko.setName("Ziko");
        Basket basket = new Basket();
        Item item = new Item();
        Medicine paracetamol = new Medicine();
        Medicine nasivin = new Medicine();
        paracetamol.setName("Paracetamol");
        item.setMedicine(paracetamol);
        item.setPharmacy(ziko);
        item.setPrice(BigDecimal.valueOf(0.41));
        List<Medicine> listMedicine = new ArrayList<>();
        listMedicine.add(paracetamol);
        listMedicine.add(nasivin);
        List<Item> itemList = new ArrayList<>();
        itemList.add(item);
        basket.setItems(itemList);
        basket.setCost(BigDecimal.valueOf(0.41));
        basket.setPharmacy(ziko);
        List<Pharmacy> pharmacyList = new ArrayList<>();
        pharmacyList.add(ziko);
        List<Basket> basketList = new ArrayList<>();
        basketList.add(basket);
        when(medicineRepository.findByName("Paracetamol")).thenReturn(Optional.of(paracetamol));
        when(medicineRepository.findByName("Nasivin")).thenReturn(Optional.of(nasivin));
        when(pharmacyRepository.findAll()).thenReturn(pharmacyList);
        when(basketRepository.save(basket)).thenReturn(basket);
        when(itemRepository.findByMedicineAndPharmacy(paracetamol,ziko)).thenReturn(Optional.of(item));
        //when
        medicineRequestHolder.setMedicineNames(stringList);
       List<Basket> result = basketService.findCheapestBaskets(medicineRequestHolder);
        //then
        assertEquals(1, result.size());
        assertEquals(basket, result.get(0));
    }
}