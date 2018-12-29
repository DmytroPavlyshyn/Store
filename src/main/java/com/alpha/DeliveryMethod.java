package com.alpha;



public enum DeliveryMethod {
    NOVA_POSHTA(0.2), UKR_POSHTA(0.1), CORIER_DELIVERY(0.4), WITHOUT(0.0);
    double percents;

    DeliveryMethod(double percents) {
        this    .percents = percents;
    }

    public double getPercents() {
        return percents;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer(this.name()+ "{");
        sb.append("percents=").append(percents);
        sb.append('}');
        return sb.toString();
    }
}
