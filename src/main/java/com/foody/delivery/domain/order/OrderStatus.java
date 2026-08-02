package com.foody.delivery.domain.order;

public enum OrderStatus {
    RECEIVED("received"),
    PREPARING("preparing"),
    OUT_FOR_DELIVERY("out_for_delivery"),
    DELIVERED("delivered"),
    CANCELLED("cancelled")
    ;

    private String status;

    OrderStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
