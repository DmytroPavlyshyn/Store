package com.alpha.plants;

import java.io.Serializable;
import java.util.Objects;

public class Flower extends Plant implements Serializable {
    FlowerType flowerType;

    public Flower(Double length, String country, Integer price, FlowerType flowerType) {
        super(length, country, price);
        this.flowerType = flowerType;
    }

    public FlowerType getFlowerType() {
        return flowerType;
    }

    @Override
    public String toString() {
        return "\n" + this.getFlowerType() + "{" +
                super.toString()
                + "} \n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Flower)) return false;
        if (!super.equals(o)) return false;
        Flower flower = (Flower) o;
        return getFlowerType() == flower.getFlowerType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getFlowerType());
    }
}
