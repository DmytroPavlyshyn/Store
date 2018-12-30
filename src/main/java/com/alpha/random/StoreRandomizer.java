package com.alpha.random;

import com.alpha.Store;
import com.alpha.decorations.WrapperType;
import com.alpha.decorations.FlowerBouquet;
import com.alpha.decorations.FlowerDecoration;
import com.alpha.decorations.FlowerPot;
import com.alpha.plants.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StoreRandomizer {
    Random random;

    public StoreRandomizer(long seed) {
        this.random = new Random(seed);
    }

    private static String[] counties = {"Mexico", "Ukraine", "France", "Brazil", "China", "Turkey"};
    List<Plant> plants = new ArrayList<>();
    List<FlowerDecoration> flowerDecorations = new ArrayList<>();

   public Store nextStore(int numberOfPlants, int numberOfFlowerDecorations) {
        for (int i = 0; i < numberOfPlants; i++) {
            plants.add(random.nextBoolean() ? nextFlower() : nextTree());
        }
        for (int i = 0; i < numberOfFlowerDecorations; i++) {
            flowerDecorations.add(random.nextBoolean() ? nextFlowerPot():nextFlowerBouquet());
        }
        return new Store(plants, flowerDecorations, counties[0]);
    }
    public Store nextStore(int numberOfFlowers, int numberOfTrees, int numberOfFlowerPots, int numberOfFlowerBouquet) {
        for (int i = 0; i < numberOfFlowers; i++) {
            plants.add(nextFlower());
        }
        for (int i = 0; i < numberOfTrees; i++) {
            plants.add(nextTree());
        }
        for (int i = 0; i < numberOfFlowerPots; i++) {
            flowerDecorations.add(nextFlowerPot());
        }
        for (int i = 0; i < numberOfFlowerBouquet; i++) {
            flowerDecorations.add(nextFlowerBouquet());
        }
        return new Store(plants, flowerDecorations, counties[0]);
    }

    public Plant nextPlant() {
        double length = 1 + random.nextInt(100) + (random.nextInt(100) * 1.0) * 0.01;
        String country = counties[random.nextInt(counties.length)];
        int price = 1 + random.nextInt(30);
        return new Plant(length, country, price);
    }

    public  Flower nextFlower() {
        Plant randPlant = nextPlant();
        FlowerType[] flowerTypes = FlowerType.values();
        FlowerType randFlowerType = flowerTypes[random.nextInt(flowerTypes.length)];
        return new Flower(randPlant.getLength(), randPlant.getCountry(), randPlant.calculatePrice(), randFlowerType);
    }

    public Tree nextTree() {
        Plant randPlant = nextPlant();
        TreeType[] flowerTypes = TreeType.values();
        TreeType randFlowerType = flowerTypes[random.nextInt(flowerTypes.length)];
        return new Tree(randPlant.getLength(), randPlant.getCountry(), randPlant.calculatePrice(), randFlowerType);
    }

    public  FlowerPot nextFlowerPot() {
        List<Flower> flowers = new ArrayList<>();

        int numberOfFlowers = 1 + random.nextInt(4);
        FlowerType[] flowerTypes = FlowerType.values();
        FlowerType randFlowerType = flowerTypes[random.nextInt(flowerTypes.length)];

        while (flowers.size() != numberOfFlowers) {
            Flower flower = nextFlower();
            if (flower.getFlowerType() == randFlowerType) {
                flowers.add(flower);
            }
        }
        return new FlowerPot(flowers,randFlowerType);
    }
    public FlowerBouquet nextFlowerBouquet(){
        List<Flower> flowers = new ArrayList<>();
        int numberOfFlowers = 1 + random.nextInt(4);
        for (int i = 0; i < numberOfFlowers; i++) {
            flowers.add(nextFlower());
        }
        WrapperType[] wrapperTypes = WrapperType.values();
        WrapperType randWrapperType = wrapperTypes[random.nextInt(wrapperTypes.length)];
        return new FlowerBouquet(flowers, randWrapperType);
    }
    public static void main(String[] args) {
        StoreRandomizer storeRandomizer = new StoreRandomizer(47);
        Store store = storeRandomizer.nextStore(0,5,3,4);
        System.out.println(store.getPlants());
        System.out.println("-----------------------------------------------------------------");
        System.out.println(store.getReadyFlowerDecorations());

    }

}
