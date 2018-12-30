package com.alpha;


import com.alpha.decorations.WrapperType;
import com.alpha.decorations.FlowerBouquet;
import com.alpha.decorations.FlowerDecoration;
import com.alpha.decorations.FlowerPot;
import com.alpha.plants.Flower;
import com.alpha.plants.FlowerType;
import com.alpha.plants.Plant;
import com.alpha.plants.Tree;

import java.util.*;


public class Store {

    private List<Plant> inventoryOfPlants;
    private List<FlowerDecoration> readyFlowerDecorations;
    private List<Order> soldProducts = new ArrayList<>();
    private String country;
    private Set<Customer> customers = new LinkedHashSet<>();
    private Integer balance;

    public Store(List<Plant> inventoryOfPlants, List<FlowerDecoration> readyFlowerDecorations, String country) {
        this.inventoryOfPlants = inventoryOfPlants;
        this.readyFlowerDecorations = readyFlowerDecorations;
        this.country = country;
        this.balance = 0;
    }


    //getters
    public List<Plant> getPlants() {
        return inventoryOfPlants;
    }

    public List<FlowerDecoration> getReadyFlowerDecorations() {
        return readyFlowerDecorations;
    }

    public List<Order> getSoldProducts() {
        return soldProducts;
    }

    public String getCountry() {
        return country;
    }

    public Set<Customer> getCustomers() {
        return customers;
    }

    public List<Plant> getPlants(ShowFilter showFilter) {
        List<Plant> plants = new ArrayList<>();
        for (Plant plant : inventoryOfPlants) {
            if (showFilter.equals(ShowFilter.NATIVE)) {
                if (!isNative(plant)) {
                    continue;
                }
            }
            if (showFilter.equals(ShowFilter.OVERSEA)) {
                if (isNative(plant)) {
                    continue;
                }
            }
            plants.add(plant);
        }
        return plants;
    }

    public List<Flower> getFlowers(ShowFilter showFilter) {
        List<Flower> flowers = new ArrayList<>();
        List<Plant> plants = getPlants(showFilter);
        for (Plant plant : plants) {
            if (plant instanceof Flower) {
                flowers.add((Flower) plant);
            }
        }
        return flowers;
    }

    public List<Tree> getTrees(ShowFilter showFilter) {
        List<Tree> trees = new ArrayList<>();
        List<Plant> plants = getPlants(showFilter);
        for (Plant plant : plants) {
            if (plant instanceof Tree) {
                trees.add((Tree) plant);
            }
        }
        return trees;
    }

    LinkedHashSet<FlowerType> getAvailableFlowerTypes() {
        LinkedHashSet<FlowerType> flowerTypes = new LinkedHashSet<>();
        for (Plant plant : getPlants()) {
            if (plant instanceof Flower) {
                flowerTypes.add(((Flower) plant).getFlowerType());
            }
        }
        return flowerTypes;
    }

    public List<Flower> getFlowersByType(FlowerType flowerType) {
        List<Flower> flowers = new ArrayList<>();
        List<Plant> plants = getPlants();
        for (Plant plant : plants) {
            if (plant instanceof Flower) {
                if (((Flower) plant).getFlowerType().equals(flowerType)) {
                    flowers.add((Flower) plant);
                }
            }
        }
        return flowers;
    }

    public List<FlowerBouquet> getFlowerBouquets() {
        List<FlowerBouquet> flowerBouquets = new ArrayList<>();
        for (FlowerDecoration flowerDecoration : readyFlowerDecorations) {
            if (flowerDecoration instanceof FlowerBouquet) {
                flowerBouquets.add((FlowerBouquet) flowerDecoration);
            }

        }
        return flowerBouquets;
    }

    public List<FlowerPot> getFlowerPots() {
        List<FlowerPot> flowerPots = new ArrayList<>();
        for (FlowerDecoration flowerDecoration : readyFlowerDecorations) {
            if (flowerDecoration instanceof FlowerPot) {
                flowerPots.add((FlowerPot) flowerDecoration);
            }

        }
        return flowerPots;
    }


    public List<FlowerPot> getFlowerPots(ShowFilter showFilter) {
        List<FlowerPot> flowerPots = new ArrayList<>();
        for (FlowerDecoration flowerDecoration : readyFlowerDecorations) {
            if (!(flowerDecoration instanceof FlowerPot)) {
                continue;
            }
            if (showFilter.equals(ShowFilter.NATIVE)) {
                if (!isNative(flowerDecoration.getFlowers().get(0))) {
                    continue;
                }
            }
            if (showFilter.equals(ShowFilter.OVERSEA)) {
                if (isNative(flowerDecoration.getFlowers().get(0))) {
                    continue;
                }
            }
            flowerPots.add((FlowerPot) flowerDecoration);
        }
        return flowerPots;
    }

    public Integer getBalance() {
        return balance;
    }

    public Customer getCustomer(int index) {
        if (index < 0) {
            return null;
        }
        Iterator<Customer> customerIterator = customers.iterator();
        for (int i = 0; i < index; i++) {
            customerIterator.next();
        }
        return customerIterator.next();
    }

    //methods for creating and buying orders
    FlowerBouquet createFlowerBouquet(List<Flower> flowers, WrapperType wrapperType) {
        List<Flower> flowers1 = new ArrayList<>(flowers);
        for (Plant plant : inventoryOfPlants) {
            if (!(plant instanceof Flower)) {
                continue;
            }
            if (flowers1.contains(plant)) {
                flowers1.remove(plant);
            }
        }
        if (flowers1.size() == 0) {
            return new FlowerBouquet(flowers, wrapperType);
        }
        throw new RuntimeException("There\'s no such flowers");
    }

    void createAndBuyFlowerBouquet(List<Flower> flowers, WrapperType wrapperType, Customer customer, DeliveryMethod deliveryMethod, BuyMethod buyMethod) {
        FlowerBouquet flowerBouquet = createFlowerBouquet(flowers, wrapperType);
        Order order = new Order(flowerBouquet, deliveryMethod, buyMethod);
        if (flowerBouquet.calculatePrice() > customer.getBalance()) {
            throw new RuntimeException("You don\'t have enough money");
        }
        this.balance += order.calculatePrice();
        customer.setBalance(customer.getBalance() - order.calculatePrice());
        for (Flower flower : flowerBouquet.getFlowers()) {
            inventoryOfPlants.remove(flower);
        }
        soldProducts.add(order);
        customer.getBoughtProducts().add(order);
    }

    FlowerPot createFlowerPot(List<Flower> flowers, FlowerType flowerType) {

        List<Flower> flowers1 = new ArrayList<>(flowers);
        for (Plant plant : inventoryOfPlants) {
            if (!(plant instanceof Flower)) {
                continue;
            }
            if (flowers1.contains(plant)) {
                flowers1.remove(plant);
            }
        }
        if (flowers1.size() == 0) {
            return new FlowerPot(flowers, flowerType);
        }
        throw new RuntimeException("There\'s no such flowers");
    }

    void createAndBuyFlowerPot(List<Flower> flowers, FlowerType flowerType, Customer customer, DeliveryMethod deliveryMethod, BuyMethod buyMethod) {
        FlowerPot flowerPot = createFlowerPot(flowers, flowerType);
        Order order = new Order(flowerPot, deliveryMethod, buyMethod);
        if (order.calculatePrice() > customer.getBalance()) {
            throw new RuntimeException("You don\'t have enough money");
        }
        this.balance += order.calculatePrice();
        customer.setBalance(customer.getBalance() - order.calculatePrice());
        for (Flower flower : flowerPot.getFlowers()) {
            inventoryOfPlants.remove(flower);
        }
        soldProducts.add(order);
        customer.getBoughtProducts().add(order);
    }


    void buy(Priceable priceable, Customer customer, DeliveryMethod deliveryMethod, BuyMethod buyMethod) {
        if (priceable instanceof FlowerDecoration) {
            if (!readyFlowerDecorations.contains(priceable)) {
                throw new RuntimeException("There\'s no such flower decoration");
            }
        }
        else if (priceable instanceof Plant) {
            if (!inventoryOfPlants.contains(priceable)) {
                throw new RuntimeException("There\'s no such flower");
            }
        }
        else {
            throw new RuntimeException("The there is no such suitable purchase method here");
        }
        Order order = new Order(priceable, deliveryMethod, buyMethod);
        if (order.calculatePrice() > customer.getBalance()) {
            throw new RuntimeException("You don\'t have enough money");
        }
        this.balance += order.calculatePrice();
        customer.setBalance(customer.getBalance() - order.calculatePrice());

        if (priceable instanceof FlowerDecoration) {
            readyFlowerDecorations.remove(priceable);
        }
        if (priceable instanceof Plant) {
            inventoryOfPlants.remove(priceable);
        }
        soldProducts.add(order);
        customer.getBoughtProducts().add(order);
    }

    private boolean isNative(Plant plant) {
        return plant.getCountry().equals(country);
    }

}

