package com.alpha.decorations;

import com.alpha.Priceable;
import com.alpha.plants.Flower;

import java.util.List;
import java.util.Objects;

public abstract class FlowerDecoration implements Priceable {
    private List<Flower> flowers;

    public FlowerDecoration(List<Flower> flowers) {
        this.flowers = flowers;
    }

    public List<Flower> getFlowers() {
        return flowers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FlowerDecoration)) return false;
        FlowerDecoration that = (FlowerDecoration) o;
        return Objects.equals(getFlowers(), that.getFlowers());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFlowers());
    }
}
