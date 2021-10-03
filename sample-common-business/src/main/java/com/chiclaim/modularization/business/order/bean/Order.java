package com.chiclaim.modularization.business.order.bean;

/**
 * Description：
 *
 * Created by kumu on 2017/7/29.
 */

public class Order {

    private String orderId;

    private String orderName;

    private double orderPrice;


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public double getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(double orderPrice) {
        this.orderPrice = orderPrice;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", orderName='" + orderName + '\'' +
                ", orderPrice=" + orderPrice +
                '}';
    }
}
