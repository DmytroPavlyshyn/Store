package com.alpha;

import com.alpha.decorations.Accessory;
import com.alpha.decorations.FlowerBouquet;
import com.alpha.decorations.FlowerDecoration;
import com.alpha.decorations.FlowerPot;
import com.alpha.plants.*;
import com.alpha.random.StoreRandomizer;

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

        Store store = new StoreRandomizer(47).nextStore(10,10,10,10);
        Customer customer = new Customer("Don", "Vova", 1000, 1);
        store.getCustomers().add(customer);
        StoreForUser storeForUser = new StoreForUser(store);

        while (true) {
            storeForUser.help();
            switch (new Scanner(System.in).nextInt()) {
                case 1:
                    storeForUser.buyPlant(Flower.class, customer);
                    break;
                case 2:
                    storeForUser.buyPlant(Tree.class, customer);
                    break;
                case 3:
                    storeForUser.buyFlowerDecoration(FlowerBouquet.class, customer);
                    break;
                case 4:
                    storeForUser.buyFlowerDecoration(FlowerPot.class, customer);
                    break;
                case 5:
                    storeForUser.createAndBuyBouquet(customer);
                    break;
                case 6:
                    storeForUser.createAndBuyFlowerPot(customer);
                    break;
                case 7:
                    System.out.println(storeForUser.getStore().getFlowers(ShowFilter.ALL));
                    break;
                case 8:
                    System.out.println(storeForUser.getStore().getTrees(ShowFilter.ALL));
                    break;
                case 9:
                    System.out.println(storeForUser.getStore().getFlowerBouquets());
                    break;
                case 10:
                    System.out.println(storeForUser.getStore().getFlowerPots());
                    break;
                case 11:
                    System.out.println(customer.getBalance() + " $");
                    break;
                case 12:
                    System.out.println(customer.getBoughtProducts());
                    break;
                default:
                    System.out.println("Wrong command");
                    break;
            }
            System.out.println("Do you want to continue 1 - yes, 2 - no?");
            if (storeForUser.readNumberInBounds(1, 2) == 2) {
                return;
            }
            storeForUser.scanner.nextLine();
        }
    }

    public Store getStore() {
        return store;
    }

    public void help() {
        System.out.println("What do you want to buy ?" +
                "\n1  - Buy flower" + // 1 - all flowers, 2 - only native, 3 - overseas
                "\n2  - Buy tree" +
                "\n3  - Flower bouquet" +
                "\n4  - Flower pot" +
                "\n5  - Create and buy bouquet" +
                "\n6  - Create and buy pot" +
                "\n7  - Show all available flowers" +
                "\n8  - Show all available trees" +
                "\n9  - Show all available flower bouquets" +
                "\n10 - Show all available flower pots" +
                "\n11 - Show my balance" +
                "\n12 - Show bought products"
        );
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

    private DeliveryMethod chooseDeliverMethod() {
        int i = 1;
        for (DeliveryMethod deliveryMethod : DeliveryMethod.values()) {
            System.out.println(i++ + ". " + deliveryMethod);
        }
        return DeliveryMethod.values()[readNumberInBounds(1, DeliveryMethod.values().length) - 1];
    }

    private BuyMethod chooseBuyMethod() {
        int i = 1;
        for (BuyMethod buyMethod : BuyMethod.values()) {
            System.out.println(i++ + ". " + buyMethod);
        }
        return BuyMethod.values()[readNumberInBounds(1, BuyMethod.values().length) - 1];
    }

    public void buyPlant(Class<? extends Plant> plantClass, Customer customer) {
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


//        store.buyPlant(plantsOfChosentType.get(readNumberInBounds(1, i - 1) - 1), customer, chooseDeliverMethod(), chooseBuyMethod());
        store.buy(plantsOfChosentType.get(readNumberInBounds(1, i - 1) - 1), customer, chooseDeliverMethod(), chooseBuyMethod());

    }

    public void buyFlowerDecoration(Class<? extends FlowerDecoration> flowerDecorationClass, Customer customer) {
        if (store.getReadyFlowerDecorations().isEmpty()) {
            System.out.println("There\'s no  available decorations, try again later");
            return;
        }
        ShowFilter choiceEn = ShowFilter.ALL;
        if (flowerDecorationClass.equals(FlowerPot.class)) {
            System.out.println("What type of " + flowerDecorationClass.getSimpleName().toLowerCase() + " do you want: \n\t1 - All\n\t2 - Native\n\t3 - Oversea");
            choiceEn = ShowFilter.values()[readNumberInBounds(1, 3) - 1];
        }

        List<FlowerDecoration> flowerDecorationsOfChosenType = new ArrayList<>();

        if (flowerDecorationClass.equals(FlowerBouquet.class)) {
            flowerDecorationsOfChosenType.addAll(store.getFlowerBouquets());
        } else if (flowerDecorationClass.equals(FlowerPot.class)) {
            flowerDecorationsOfChosenType.addAll(store.getFlowerPots(choiceEn));
        }

        if (flowerDecorationsOfChosenType.isEmpty()) {
            System.out.println("There\'s no available such " + flowerDecorationClass.getSimpleName().toLowerCase());
            return;
        }
        int i = 1;
        for (FlowerDecoration flowerDecoration : flowerDecorationsOfChosenType) {
            System.out.println(i++ + ". " + flowerDecoration);
        }

//        store.buyReadyFlowerDecoration(flowerDecorationsOfChosenType.get(readNumberInBounds(1, i - 1) - 1), customer, chooseDeliverMethod(), chooseBuyMethod());
        store.buy(flowerDecorationsOfChosenType.get(readNumberInBounds(1, i - 1) - 1), customer, chooseDeliverMethod(), chooseBuyMethod());

    }

    FlowerBouquet createBouquet() {
        List<Flower> availableFlowers = store.getFlowers(ShowFilter.ALL);
        List<Flower> chosenFlowers = new ArrayList<>();
        EnumSet<Accessory> chosenAccessories = EnumSet.noneOf(Accessory.class);
        if (availableFlowers.isEmpty()) {
            System.out.println("There\'s no  available plants, try again later");
            return null;
        }
        int i = 1;
        for (Flower flower : availableFlowers) {
            System.out.println(i++ + ". " + flower);
        }
        Pattern pattern = Pattern.compile("(?<index>\\d+)");
        Matcher matcher = pattern.matcher(scanner.nextLine());
        while (matcher.find()) {
            Flower flower = availableFlowers.get(Integer.parseInt(matcher.group("index")) - 1);
            chosenFlowers.add(flower);
        }
        i = 1;
        for (Accessory accessory : Accessory.values()) {
            System.out.println(i++ + ". " + accessory);
        }
        matcher = pattern.matcher(scanner.nextLine());
        while (matcher.find()) {
            Accessory accessory = Accessory.values()[Integer.parseInt(matcher.group("index")) - 1];
            chosenAccessories.add(accessory);
        }
        return new FlowerBouquet(chosenFlowers, chosenAccessories);
    }

    void createAndBuyBouquet(Customer customer) {
        try {
            FlowerBouquet flowerBouquet = createBouquet();
            if (flowerBouquet == null) {
                return;
            }
            store.createAndBuyFlowerBouquet(flowerBouquet.getFlowers(), flowerBouquet.getAccessories(), customer, chooseDeliverMethod(), chooseBuyMethod());
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

    void createAndBuyFlowerPot(Customer customer) {
        try {
            FlowerPot flowerPot = createFlowerPot();
            if (flowerPot == null) {
                return;
            }
            store.createAndBuyFlowerPot(flowerPot.getFlowers(), flowerPot.getFlowerType(), customer, chooseDeliverMethod(), chooseBuyMethod());
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("You entered some wrong indexes, try again!");
        }
    }
}
