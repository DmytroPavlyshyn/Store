package com.alpha.plants;

import com.alpha.Priceable;

import java.io.Serializable;
import java.util.Objects;

public class Plant implements Priceable, Serializable {
    private Double length;
    private String country;
    private Integer price;


    public Plant(Double length, String country, Integer price) {
        this.length = length;
        this.country = country;
        this.price = price;
    }

    public Double getLength() {
        return length;
    }

    public String getCountry() {
        return country;
    }



    public int calculatePrice() {
        return price;
    }
    @Override
    public String toString() {
        return "\nPlant{" +
                "length=" + length +
                ", country='" + country + '\'' +
                "}\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Plant)) return false;
        Plant plant = (Plant) o;
        return Objects.equals(getLength(), plant.getLength()) &&
                Objects.equals(getCountry(), plant.getCountry()) &&
                Objects.equals(calculatePrice(), plant.calculatePrice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLength(), getCountry(), calculatePrice());
    }
}
