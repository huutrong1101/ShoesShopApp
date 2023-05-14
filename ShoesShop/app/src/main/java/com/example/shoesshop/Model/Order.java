package com.example.shoesshop.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Order {
    @SerializedName("status")
    private String status;
    @SerializedName("_id")
    private String id;
    @SerializedName("orderItems")
    private List<ProductInCart> orderItems;
    @SerializedName("shippingAddress")
    private String shippingAddress;
    @SerializedName("name")
    private String name;
    @SerializedName("phone")
    private String phone;
    @SerializedName("user")
    private String user;
    @SerializedName("dateOrdered")
    private String dateOrdered;
    @SerializedName("__v")
    private String __v;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<ProductInCart> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<ProductInCart> orderItems) {
        this.orderItems = orderItems;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDateOrdered() {
        return dateOrdered;
    }

    public void setDateOrdered(String dateOrdered) {
        this.dateOrdered = dateOrdered;
    }

    public String get__v() {
        return __v;
    }

    public void set__v(String __v) {
        this.__v = __v;
    }
}
