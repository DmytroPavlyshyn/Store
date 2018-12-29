package com.alpha;

public class Order implements Priceable {
    private Priceable priceable;
    private Delivery delivery;
    private BuyMethod buyMethod;

    public Order(Priceable priceable, Delivery delivery, BuyMethod buyMethod) {
        this.priceable = priceable;
        this.delivery = delivery;
        this.buyMethod = buyMethod;
    }

    public Priceable getPriceable() {
        return priceable;
    }

    public Delivery getDelivery() {
        return delivery;
    }

    public BuyMethod getBuyMethod() {
        return buyMethod;
    }

    @Override
    public int calculatePrice() {
        return priceable.calculatePrice() + (int)((double)priceable.calculatePrice()* getDelivery().getPercents());
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Order{");
        sb.append("priceable=").append(priceable);
        sb.append(", delivery=").append(delivery);
        sb.append(", buyMethod=").append(buyMethod);
        sb.append('}');
        return sb.toString();
    }
}
