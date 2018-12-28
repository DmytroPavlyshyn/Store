package com.alpha.decorations;

import com.alpha.Priceable;
import com.alpha.plants.Flower;

import java.io.Serializable;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;

public class FlowerBouquet extends FlowerDecoration implements Serializable {
    private EnumSet<Accessory> accessories;

    public FlowerBouquet(List<Flower> flowers, EnumSet<Accessory> accessories) {
        super(flowers);
        this.accessories = accessories;
    }

    public EnumSet<Accessory> getAccessories() {
        return accessories;
    }



    public int calculatePrice() {
        int price = 0;
        for (Flower flower : getFlowers()) {
            price += flower.calculatePrice();

        }
        for(Accessory accessory: accessories){
            price +=accessory.getPrice();
        }
        return price;
    }

    @Override
    public String toString() {
        return "\nFlowerBouquet{" +
                "accessories=" + accessories +
                ", flowers=" + getFlowers() +
                "}\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FlowerBouquet)) return false;
        if (!super.equals(o)) return false;
        FlowerBouquet that = (FlowerBouquet) o;
        return Objects.equals(getAccessories(), that.getAccessories());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getAccessories());
    }
}
