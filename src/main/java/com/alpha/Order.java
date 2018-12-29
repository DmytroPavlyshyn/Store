package com.alpha;

public class Order implements Priceable {
    private Priceable priceable;
    private DeliveryMethod deliveryMethod;
    private BuyMethod buyMethod;

    public Order(Priceable priceable, DeliveryMethod deliveryMethod, BuyMethod buyMethod) {
        this.priceable = priceable;
        this.deliveryMethod = deliveryMethod;
        this.buyMethod = buyMethod;
    }

    public Priceable getPriceable() {
        return priceable;
    }

    public DeliveryMethod getDeliveryMethod() {
        return deliveryMethod;
    }

    public BuyMethod getBuyMethod() {
        return buyMethod;
    }

    @Override
    public int calculatePrice() {
        return priceable.calculatePrice() + (int)((double)priceable.calculatePrice()* getDeliveryMethod().getPercents());
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Order{");
        sb.append("priceable=").append(priceable);
        sb.append(", deliveryMethod=").append(deliveryMethod);
        sb.append(", buyMethod=").append(buyMethod);
        sb.append('}');
        return sb.toString();
    }
}
