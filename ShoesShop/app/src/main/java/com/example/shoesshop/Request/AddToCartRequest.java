package com.example.shoesshop.Request;

import com.example.shoesshop.Model.ProductInCart;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AddToCartRequest {
    @SerializedName("userId")
    private String userId;

    @SerializedName("products")
    private List<ProductInCart> products;

    public AddToCartRequest(String userId, List<ProductInCart> products) {
        this.userId = userId;
        this.products = products;
    }
}
