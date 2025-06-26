package org.yearup.models;

import java.math.BigDecimal;

public class OrderLineItem {
    int lineItemId;
    int orderId;
    int productId;
    BigDecimal salesPrice;
    int quantity;
    BigDecimal discount;

    public OrderLineItem(int lineItemId, int orderId, int productId, BigDecimal salesPrice, int quantity, BigDecimal discount) {
        this.lineItemId = lineItemId;
        this.orderId = orderId;
        this.productId = productId;
        this.salesPrice = salesPrice;
        this.quantity = quantity;
        this.discount = discount;
    }

    public OrderLineItem(){

    }


    public int getLineItemId() {
        return lineItemId;
    }

    public void setLineItemId(int lineItemId) {
        this.lineItemId = lineItemId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getSalesPrice() {
        return salesPrice;
    }

    public void setSalesPrice(BigDecimal salesPrice) {
        this.salesPrice = salesPrice;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }
}
