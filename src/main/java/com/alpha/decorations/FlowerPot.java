package com.alpha.decorations;

import com.alpha.Priceable;
import com.alpha.plants.Flower;
import com.alpha.plants.FlowerType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FlowerPot extends FlowerDecoration implements Serializable {
    private FlowerType flowerType;

    public FlowerPot(List<Flower> flowers, FlowerType flowerType) {
        super(flowers);
        if (!areSameTypeFlowers(flowers, flowerType)) {
            throw new RuntimeException("Flowers are not the same type");

        }

        this.flowerType = flowerType;
    }

    public static void main(String[] args) {
        List<Priceable> priceables = new ArrayList<>();
        priceables.add(new FlowerPot(new ArrayList<Flower>() {{
            add(new Flower(123.0, "China", 12, FlowerType.CAMELLIA));
            add(new Flower(143.0, "China", 12, FlowerType.CAMELLIA));
            add(new Flower(133.0, "China", 12, FlowerType.CAMELLIA));

        }}, FlowerType.CAMELLIA));
        System.out.println(priceables);
    }

    private boolean areSameTypeFlowers(List<Flower> flowers, FlowerType flowerType) {
        for (Flower flower : flowers) {
            if (!flower.getFlowerType().equals(flowerType)) {
                return false;
            }
        }
        return true;
    }

    public FlowerType getFlowerType() {
        return flowerType;
    }

    public int calculatePrice() {
        int price = 0;
        for (com.alpha.plants.Flower flower : getFlowers()) {
            price += flower.calculatePrice();

        }

        return price;
    }

    @Override
    public String toString() {
        return "\nFlowerPot{" +
                "flowers=" + getFlowers() +
                ", flowerType=" + flowerType.name().toLowerCase() +
                "}\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FlowerPot)) return false;
        if (!super.equals(o)) return false;
        FlowerPot flowerPot = (FlowerPot) o;
        return getFlowerType() == flowerPot.getFlowerType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getFlowerType());
    }
}