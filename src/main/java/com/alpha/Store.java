package com.alpha;


import com.alpha.decorations.Accessory;
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
    private List<FlowerDecoration> readyProducts;
    private List<Order> soldProducts = new ArrayList<>();
    private String country;
    private Set<Customer> customers = new LinkedHashSet<>();
    private Integer balance;

    public Store(List<Plant> inventoryOfPlants, List<FlowerDecoration> readyProducts, String country) {
        this.inventoryOfPlants = inventoryOfPlants;
        this.readyProducts = readyProducts;
        this.country = country;
        this.balance = 0;
    }


    //getters
    public List<Plant> getInventoryOfPlants() {
        return inventoryOfPlants;
    }

    public List<FlowerDecoration> getReadyProducts() {
        return readyProducts;
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
        for (Plant plant : getInventoryOfPlants()) {
            if (plant instanceof Flower) {
                flowerTypes.add(((Flower) plant).getFlowerType());
            }
        }
        return flowerTypes;
    }

    public List<Flower> getFlowersByType(FlowerType flowerType) {
        List<Flower> flowers = new ArrayList<>();
        List<Plant> plants = getInventoryOfPlants();
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
        for (FlowerDecoration flowerDecoration : readyProducts) {
            if (flowerDecoration instanceof FlowerBouquet) {
                flowerBouquets.add((FlowerBouquet) flowerDecoration);
            }

        }
        return flowerBouquets;
    }

    public List<FlowerPot> getFlowerPots() {
        List<FlowerPot> flowerPots = new ArrayList<>();
        for (FlowerDecoration flowerDecoration : readyProducts) {
            if (flowerDecoration instanceof FlowerPot) {
                flowerPots.add((FlowerPot) flowerDecoration);
            }

        }
        return flowerPots;
    }

    public List<FlowerDecoration> getFlowerDecoration(ShowFilter showFilter) {
        List<FlowerDecoration> flowerDecorations = new ArrayList<>();
        for (FlowerDecoration flowerDecoration : readyProducts) {
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
            flowerDecorations.add(flowerDecoration);
        }
        return flowerDecorations;
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
    private FlowerBouquet createFlowerBouquetOrder(List<Flower> flowers, EnumSet<Accessory> accessories) {
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
            return new FlowerBouquet(flowers, accessories);
        }
        throw new RuntimeException("There\'s no such flowers");
    }

    void createAndBuyFlowerBouquet(List<Flower> flowers, EnumSet<Accessory> accessories, Customer customer, Delivery delivery, BuyMethod buyMethod) {
        FlowerBouquet flowerBouquet = createFlowerBouquetOrder(flowers, accessories);
        Order order = new Order(flowerBouquet, delivery, buyMethod);
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

    private FlowerPot createFlowerPotOrder(List<Flower> flowers, FlowerType flowerType) {

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

    void createAndBuyFlowerPot(List<Flower> flowers, FlowerType flowerType, Customer customer, Delivery delivery, BuyMethod buyMethod) {
        FlowerPot flowerPot = createFlowerPotOrder(flowers, flowerType);
        Order order = new Order(flowerPot, delivery, buyMethod);
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

    //methods for buying simple plants and ready products
    void buyPlant(Plant plant, Customer customer, Delivery delivery, BuyMethod buyMethod) {
        if (!inventoryOfPlants.contains(plant)) {
            throw new RuntimeException("There\'s no such plant");
        }
        Order order = new Order(plant, delivery, buyMethod);
        if (order.calculatePrice() > customer.getBalance()) {
            throw new RuntimeException("You don\'t have enough money");
        }
        this.balance += order.calculatePrice();
        customer.setBalance(customer.getBalance() - order.calculatePrice());
        inventoryOfPlants.remove(plant);
        soldProducts.add(order);
        customer.getBoughtProducts().add(order);
    }

    void buyReadyProduct(FlowerDecoration flowerDecoration, Customer customer, Delivery delivery, BuyMethod buyMethod) {
        if (!readyProducts.contains(flowerDecoration)) {
            throw new RuntimeException("There\'s no such flower decoration");
        }
        Order order = new Order(flowerDecoration, delivery, buyMethod);
        if (order.calculatePrice() > customer.getBalance()) {
            throw new RuntimeException("You don\'t have enough money");
        }
        this.balance += order.calculatePrice();
        customer.setBalance(customer.getBalance() - order.calculatePrice());
        readyProducts.remove(flowerDecoration);
        soldProducts.add(order);
        customer.getBoughtProducts().add(order);
    }


    

    private boolean isNative(Plant plant) {
        return plant.getCountry().equals(country);
    }

}

