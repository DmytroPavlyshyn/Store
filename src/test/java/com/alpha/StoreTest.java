package com.alpha;

import com.alpha.decorations.FlowerBouquet;
import com.alpha.decorations.FlowerDecoration;
import com.alpha.decorations.FlowerPot;
import com.alpha.decorations.WrapperType;
import com.alpha.io.ProductsIO;
import com.alpha.plants.Flower;
import com.alpha.plants.FlowerType;
import com.alpha.plants.Plant;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class StoreTest {
    Store store;
    List<Plant> plants;
    List<FlowerDecoration> flowerDecorations;
    Customer customer;

    @Before
    public void setUp() {
        ProductsIO productsIO1 = new ProductsIO("FlowerProducts.dat");
        ProductsIO productsIO2 = new ProductsIO("FlowerDecorations.dat");
        plants = new ArrayList<>();
        flowerDecorations = new ArrayList<>();
        try {
            plants = productsIO1.read().stream().map((a) -> (Plant) a).collect(Collectors.toList());
            flowerDecorations = productsIO2.read().stream().map((a) -> (FlowerDecoration) a).collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        store = new Store(new ArrayList<Plant>(plants), new ArrayList<FlowerDecoration>(flowerDecorations), "Ukraine");
        customer = new Customer("first", "last", 1000, 1);
        store.getCustomers().add(customer);
    }

    @Test
    public void testBuyAllPlants() {
        int size = plants.size();
        for (Plant plant : plants) {
            store.buy(plant, customer, DeliveryMethod.NOVA_POSHTA, BuyMethod.CASH);
        }
        assertTrue(store.getPlants().isEmpty());
    }

    @Test
    public void testBuyAllFlowerDecorations() {
        int size = plants.size();
        for (FlowerDecoration flowerDecoration : flowerDecorations) {
            store.buy(flowerDecoration, customer, DeliveryMethod.NOVA_POSHTA, BuyMethod.CASH);
        }
        assertTrue(store.getReadyFlowerDecorations().isEmpty());
    }

    @Test
    public void testBuyNotSuitableType() {
        boolean wasThrown = false;
        try {
            store.buy(new Priceable() {
                @Override
                public int calculatePrice() {
                    return 0;
                }
            }, customer, DeliveryMethod.NOVA_POSHTA, BuyMethod.CASH);
        } catch (RuntimeException e) {
            wasThrown = true;
        }
        assertTrue(wasThrown);
    }

    @Test
    public void testCreateFlowerBouquet() {
        List<Flower> flowers = store.getFlowers(ShowFilter.NATIVE);
        WrapperType wrapperType = WrapperType.PAPER;
        FlowerBouquet flowerBouquet = store.createFlowerBouquet(flowers, wrapperType);
        assertEquals(flowerBouquet.getFlowers(), flowers);
        assertEquals(flowerBouquet.getWrapperType(), wrapperType);

    }

    @Test
    public void testCreateAndBuyFlowerBouquet() {
        List<Flower> flowers = store.getFlowers(ShowFilter.NATIVE);
        WrapperType wrapperType = WrapperType.PAPER;
        FlowerBouquet flowerBouquet = store.createFlowerBouquet(flowers, wrapperType);
        store.createAndBuyFlowerBouquet(flowers, wrapperType,customer,DeliveryMethod.NOVA_POSHTA,BuyMethod.CASH);
        assertEquals(store.getSoldProducts().get(0).getPriceable(),flowerBouquet);
    }

    @Test
    public void testCreateFlowerPot() {
        FlowerType flowerType = FlowerType.CAMELLIA;
        List<Flower> flowers = store.getFlowersByType(flowerType);
        FlowerPot flowerBouquet = store.createFlowerPot(flowers, FlowerType.CAMELLIA);
        assertEquals(flowerBouquet.getFlowers(), flowers);
        assertEquals(flowerBouquet.getFlowerType(), flowerType);

    }

    @Test
    public void testCreateAndBuyFlowerPot() {
        FlowerType flowerType = FlowerType.CAMELLIA;
        List<Flower> flowers = store.getFlowersByType(flowerType);
        FlowerPot flowerPot = store.createFlowerPot(flowers, FlowerType.CAMELLIA);
        store.createAndBuyFlowerPot(flowers,flowerType,customer,DeliveryMethod.NOVA_POSHTA,BuyMethod.CASH);
        assertEquals(store.getSoldProducts().get(0).getPriceable(), flowerPot);
    }
}
