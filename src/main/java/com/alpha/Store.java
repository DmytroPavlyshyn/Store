package com.alpha;


import com.alpha.decorations.Accessory;
import com.alpha.decorations.FlowerBouquet;
import com.alpha.decorations.FlowerDecoration;
import com.alpha.decorations.FlowerPot;
import com.alpha.plants.*;

import java.util.*;


public class Store {

    private List<Plant> inventoryOfPlants = new ArrayList<>();
    private List<FlowerDecoration> readyProducts = new ArrayList<>();
    private List<Priceable> soldProducts = new ArrayList<>();
    private String country;

    public Store(List<Plant> inventoryOfPlants, List<FlowerDecoration> readyProducts, String country) {
        this.inventoryOfPlants = inventoryOfPlants;
        this.readyProducts = readyProducts;
        this.country = country;
    }

    public static void main(String[] args) {
        //------------------------------------------------------------------------------------------------
        List<FlowerDecoration> priceables = new ArrayList<>();
        priceables.add(new FlowerBouquet(new ArrayList<Flower>() {{
            add(new Flower(123.0, "China", 12, FlowerType.CAMELLIA));
        }}, EnumSet.allOf(Accessory.class)));
        //------------------------------------------------------------------------------------------------
        List<Plant> plants = new ArrayList<Plant>() {
            {
                add(new Flower(123.0, "China", 12, FlowerType.CAMELLIA));
                add(new Flower(123.0, "Ukraine", 12, FlowerType.CHAMOMILE));
                add(new Flower(123.0, "China", 12, FlowerType.FRANGIPANI));
                add(new Flower(123.0, "London", 12, FlowerType.DAHLIA));
                add(new Flower(123.0, "China", 12, FlowerType.ROSE));
                add(new Flower(123.0, "China", 12, FlowerType.CAMELLIA));
                add(new Tree(123.0, "London", 12, TreeType.JACARANDA));
                add(new Tree(123.0, "China", 12, TreeType.POINSETTIA));
                add(new Tree(123.0, "China", 12, TreeType.PALM));
            }
        };
        //------------------------------------------------------------------------------------------------
        Store store = new Store(plants, priceables, "China");
        store.buyFlowerBouquet(new ArrayList<Flower>() {{
            add(new Flower(123.0, "China", 12, FlowerType.CAMELLIA));
            // add(new Flower(123.0, "China", 12, FlowerType.FRANGIPANI));
            add(new Flower(123.0, "China", 12, FlowerType.FRANGIPANI));

        }}, EnumSet.allOf(Accessory.class));
        //------------------------------------------------------------------------------------------------
        System.out.println(store.getPlants(ShowFilter.OVERSEA));

    }

    //getters
    public List<Plant> getInventoryOfPlants() {
        return inventoryOfPlants;
    }

    public List<FlowerDecoration> getReadyProducts() {
        return readyProducts;
    }

    public List<Priceable> getSoldProducts() {
        return soldProducts;
    }

    public String getCountry() {
        return country;
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

    void buyFlowerBouquet(List<Flower> flowers, EnumSet<Accessory> accessories) {
        FlowerBouquet flowerBouquet = createFlowerBouquetOrder(flowers, accessories);
        for (Flower flower : flowerBouquet.getFlowers()) {
            inventoryOfPlants.remove(flower);
        }
        soldProducts.add(flowerBouquet);
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

    void buyFlowerPot(List<Flower> flowers, FlowerType flowerType) {
        FlowerPot flowerPot = createFlowerPotOrder(flowers, flowerType);
        for (Flower flower : flowerPot.getFlowers()) {
            inventoryOfPlants.remove(flower);
        }
        soldProducts.add(flowerPot);
    }


    void buyPlant(Plant plant) {
        if (!inventoryOfPlants.contains(plant)) {
            throw new RuntimeException("There\'s no such plant");
        }
        inventoryOfPlants.remove(plant);
        soldProducts.add(plant);
    }

    void buyFlowerDecoration(FlowerDecoration flowerDecoration) {
        if (!readyProducts.contains(flowerDecoration)) {
            throw new RuntimeException("There\'s no such flower decoration");
        }
        readyProducts.remove(flowerDecoration);
        soldProducts.add(flowerDecoration);
    }


    //methods for console output


    boolean isNative(Plant plant) {
        return plant.getCountry().equals(country);
    }


    void showReadyProducts() {
        System.out.println(readyProducts);
    }

    void showSoldProducts() {
        System.out.println(soldProducts);
    }

    ;


}

