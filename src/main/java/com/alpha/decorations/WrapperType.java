package com.alpha.decorations;


import java.io.Serializable;

public enum WrapperType implements Serializable {
    PAPER(5), CARDBOARD(10),POLYETHYLENE(20);
    int price;

    WrapperType(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("WrapperType{");
        sb.append("price=").append(price);
        sb.append('}');
        return sb.toString();
    }
}
