package com.alpha.decorations;

import com.alpha.plants.Flower;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class FlowerBouquet extends FlowerDecoration implements Serializable {
    WrapperType wrapperType;


    public FlowerBouquet(List<Flower> flowers, WrapperType wrapperType) {
        super(flowers);
        this.wrapperType = wrapperType;
    }

    public WrapperType getWrapperType() {
        return wrapperType;
    }

    public int calculatePrice() {
        int price = 0;
        for (Flower flower : getFlowers()) {
            price += flower.calculatePrice();
        }
        price += wrapperType.getPrice();
        return price;
    }

    @Override
    public String toString() {
        return "\nFlowerBouquet{" +
                "wrapperType=" + wrapperType +
                ", flowers=" + getFlowers() +
                "}\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FlowerBouquet)) return false;
        if (!super.equals(o)) return false;
        FlowerBouquet that = (FlowerBouquet) o;
        return getWrapperType() == that.getWrapperType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getWrapperType());
    }
}
