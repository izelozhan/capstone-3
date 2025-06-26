package org.yearup.models;

import java.sql.Date;
import java.time.LocalDateTime;

public class Order {
    int userId;
    LocalDateTime date;
    String address;
    String city;
    String state;
    String zip;
    Double shipping_amount;

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Double getShipping_amount() {
        return shipping_amount;
    }

    public void setShipping_amount(Double shipping_amount) {
        this.shipping_amount = shipping_amount;
    }
}
