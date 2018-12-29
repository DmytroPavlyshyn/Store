package com.alpha.decorations;

import java.io.Serializable;

public enum Accessory implements Serializable {
    TAPE(45),LACE(34), ;
    int price;

    Accessory(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }
}
