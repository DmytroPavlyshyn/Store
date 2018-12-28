package com.alpha;

import com.alpha.decorations.Accessory;
import com.alpha.decorations.FlowerBouquet;
import com.alpha.decorations.FlowerDecoration;
import com.alpha.decorations.FlowerPot;
import com.alpha.plants.*;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


public class StoreForUser {
    Scanner scanner = new Scanner(System.in);
    private Store store;

    public StoreForUser(Store store) {
        this.store = store;
    }

    public static void main(String[] args) {
        //------------------------------------------------------------------------------------------------
        List<FlowerDecoration> flowerDecorations = new ArrayList<>();
        flowerDecorations.add(new FlowerBouquet(new ArrayList<Flower>() {{
            add(new Flower(123.0, "China", 12, FlowerType.CAMELLIA));
        }}, EnumSet.allOf(Accessory.class)));
        flowerDecorations.add(new FlowerBouquet(new ArrayList<Flower>() {{
            add(new Flower(123.0, "Poland", 12, FlowerType.ROSE));
        }}, EnumSet.allOf(Accessory.class)));
        flowerDecorations.add(new FlowerBouquet(new ArrayList<Flower>() {{
            add(new Flower(123.0, "England", 12, FlowerType.DAHLIA));
        }}, EnumSet.allOf(Accessory.class)));
        flowerDecorations.add(new FlowerBouquet(new ArrayList<Flower>() {{
            add(new Flower(123.0, "Sweden", 12, FlowerType.MAGNOLIA));
        }}, EnumSet.allOf(Accessory.class)));
        flowerDecorations.add(new FlowerBouquet(new ArrayList<Flower>() {{
            add(new Flower(123.0, "China", 12, FlowerType.SUNFLOWER));
        }}, EnumSet.allOf(Accessory.class)));
        flowerDecorations.add(new FlowerPot(new ArrayList<Flower>() {{
            add(new Flower(123.0, "England", 12, FlowerType.DAHLIA));
        }}, FlowerType.DAHLIA));
        flowerDecorations.add(new FlowerPot(new ArrayList<Flower>() {{
            add(new Flower(123.0, "Italy", 12, FlowerType.DAHLIA));
        }}, FlowerType.DAHLIA));
        flowerDecorations.add(new FlowerPot(new ArrayList<Flower>() {{
            add(new Flower(123.0, "Greek", 12, FlowerType.DAHLIA));
        }}, FlowerType.DAHLIA));
        flowerDecorations.add(new FlowerPot(new ArrayList<Flower>() {{
            add(new Flower(123.0, "China", 12, FlowerType.DAHLIA));
        }}, FlowerType.DAHLIA));
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
        Store store = new Store(plants, flowerDecorations, "China");
        StoreForUser storeForUser = new StoreForUser(store);
        while (true) {
            storeForUser.help();
            switch (new Scanner(System.in).nextInt()) {
                case 1:
                    storeForUser.buyPlant(Flower.class);
                    break;
                case 2:
                    storeForUser.buyPlant(Tree.class);
                    break;
                case 3:
                    storeForUser.buyFlowerDecoration(FlowerBouquet.class);
                    break;
                case 4:
                    storeForUser.buyFlowerDecoration(FlowerPot.class);
                    break;
                case 5:
                    //System.out.println(storeForUser.createBouquet());
                    storeForUser.createAndBuyFlowerPot();
                    break;
                case 6:
                    //System.out.println(storeForUser.createBouquet());
                    //storeForUser.createAndBuyBouquet();
                    storeForUser.createFlowerPot();
                    break;
            }
        }
    }

    public void help() {
        System.out.println("What do you want to buy ?" +
                "\n1 - Buy flower" + // 1 - all flowers, 2 - only native, 3 - overseas
                "\n2 - Buy tree" +
                "\n3 - Flower bouquet" +
                "\n4 - Flower pot" +
                "\n5 - Create and buy bouquet" +
                "\n6 - Create and buy pot" +
                "\n7 - ");
    }


    private int readNumberInBounds(int from, int to) {
        int choice;
        do {
            choice = scanner.nextInt();
            if (!(choice < from || choice > to)) {
                return choice;
            }
            System.out.println("Enter correct answer");

        } while (true);
    }

    public void buyPlant(Class<? extends Plant> plantClass) {
        if (store.getPlants(ShowFilter.ALL).isEmpty()) {
            System.out.println("There\'s no  available plants, try again later");
            return;
        }
        System.out.println("What type of " + plantClass.getSimpleName().toLowerCase() + " do you want: \n\t1 - All\n\t2 - Native\n\t3 - Oversea");


        ShowFilter choiceEn = ShowFilter.values()[readNumberInBounds(1, 3) - 1];


        List<Plant> plantsOfChosentType = new ArrayList<>();
        if (plantClass.equals(Flower.class)) {
            plantsOfChosentType = store.getFlowers(choiceEn).stream().map((a) -> (Plant) a).collect(Collectors.toList());

        }
        if (plantClass.equals(Tree.class)) {
            plantsOfChosentType = store.getTrees(choiceEn).stream().map((a) -> (Plant) a).collect(Collectors.toList());
        }
        if (plantsOfChosentType.isEmpty()) {
            System.out.println("There\'s no available such " + plantClass.getSimpleName().toLowerCase());
            return;
        }
        System.out.println("Choose" + plantClass.getSimpleName().toLowerCase());
        int i = 1;
        for (Plant plant : plantsOfChosentType) {
            System.out.println(i++ + ". " + plant);
        }


        store.buyPlant(plantsOfChosentType.get(readNumberInBounds(1, i - 1) - 1));
    }

    public void buyFlowerDecoration(Class<? extends FlowerDecoration> flowerDecorationClass) {
        if (store.getReadyProducts().isEmpty()) {
            System.out.println("There\'s no  available decorations, try again later");
            return;
        }
        ShowFilter choiceEn = ShowFilter.ALL;
        if (flowerDecorationClass.equals(FlowerPot.class)) {
            System.out.println("What type of " + flowerDecorationClass.getSimpleName().toLowerCase() + " do you want: \n\t1 - All\n\t2 - Native\n\t3 - Oversea");
            choiceEn = ShowFilter.values()[readNumberInBounds(1, 3) - 1];
        }

        List<FlowerDecoration> flowerDecorationsOfChosenType = new ArrayList<>();
        for (FlowerDecoration flowerDecoration : store.getFlowerDecoration(choiceEn)) {
            if (flowerDecorationClass.isInstance(flowerDecoration)) {
                flowerDecorationsOfChosenType.add(flowerDecoration);
            }
        }
        if (flowerDecorationsOfChosenType.isEmpty()) {
            System.out.println("There\'s no available such " + flowerDecorationClass.getSimpleName().toLowerCase());
            return;
        }
        int i = 1;
        for (FlowerDecoration flowerDecoration : flowerDecorationsOfChosenType) {
            System.out.println(i++ + ". " + flowerDecoration);
        }

        store.buyFlowerDecoration(flowerDecorationsOfChosenType.get(readNumberInBounds(1, i - 1) - 1));
    }

    FlowerBouquet createBouquet() {
        List<Flower> availableFlowers = store.getFlowers(ShowFilter.ALL);
        List<Flower> chosenFlowers = new ArrayList<>();
        if (availableFlowers.isEmpty()) {
            System.out.println("There\'s no  available plants, try again later");
            return null;
        }
        int i = 1;
        for (Flower flower : availableFlowers) {
            System.out.println(i++ + ". " + flower);
        }
        Pattern pattern = Pattern.compile("(?<index>\\d+)");
        Scanner scanner = new Scanner(System.in);
        Matcher matcher = pattern.matcher(scanner.nextLine());
        while (matcher.find()) {
            Flower flower = availableFlowers.get(Integer.parseInt(matcher.group("index")) - 1);
            chosenFlowers.add(flower);
        }
        return new FlowerBouquet(chosenFlowers, EnumSet.allOf(Accessory.class));
    }

    void createAndBuyBouquet() {
        try {
            FlowerBouquet flowerBouquet = createBouquet();
            if (flowerBouquet == null) {
                return;
            }
            store.buyFlowerBouquet(flowerBouquet.getFlowers(), flowerBouquet.getAccessories());
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("You entered some wrong indexes, try again!");
        }
    }

    FlowerPot createFlowerPot() {
        List<Flower> availableFlowers = store.getFlowers(ShowFilter.ALL);
        List<Flower> chosenFlowers = new ArrayList<>();
        if (availableFlowers.isEmpty()) {
            System.out.println("There\'s no  available plants, try again later");
            return null;
        }
        LinkedHashSet<FlowerType> availableFlowerTypes = store.getAvailableFlowerTypes();
        System.out.println("What type of type of flowers do you want ?");
        int i = 1;
        for (FlowerType flowerType : availableFlowerTypes) {
            System.out.println(i++ + ". " + flowerType);
        }
        Iterator<FlowerType> flowerTypeIterator = availableFlowerTypes.iterator();
        FlowerType chosenFlowerType = null;
        int to = readNumberInBounds(1, i - 1);
        for (int j = 0; j < to; j++) {
            chosenFlowerType = flowerTypeIterator.next();
        }
        System.out.println(chosenFlowerType);
        System.out.println();
        List<Flower> flowersOfChosenType = store.getFlowersByType(chosenFlowerType);
        i = 1;
        for (Flower flower : flowersOfChosenType) {
            System.out.println(i++ + ". " + flower);
        }
        Pattern pattern = Pattern.compile("(?<index>\\d+)");
        scanner.nextLine();
        Matcher matcher = pattern.matcher(scanner.nextLine());
        while (matcher.find()) {
            Flower flower = flowersOfChosenType.get(Integer.parseInt(matcher.group("index")) - 1);
            chosenFlowers.add(flower);
        }
        System.out.println(chosenFlowers);
        return new FlowerPot(chosenFlowers, chosenFlowerType);
    }

    void createAndBuyFlowerPot(){
        try {
            FlowerPot flowerPot = createFlowerPot();
            if (flowerPot == null) {
                return;
            }
            store.buyFlowerPot(flowerPot.getFlowers(),flowerPot.getFlowerType());
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("You entered some wrong indexes, try again!");
        }
    }
}
