package com.example.shoesshop.Request;

import com.example.shoesshop.Model.ProductInCart;

import java.util.List;

public class AddToOrderRequest {
    private String shippingAddress;
    private String name;
    private String phone;
    private String user;
    private List<ProductInCart> orderItems;

    public AddToOrderRequest(String shippingAddress, String name, String phone, String user, List<ProductInCart> orderItems) {
        this.shippingAddress = shippingAddress;
        this.name = name;
        this.phone = phone;
        this.user = user;
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

    public List<ProductInCart> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<ProductInCart> orderItems) {
        this.orderItems = orderItems;
    }
}
