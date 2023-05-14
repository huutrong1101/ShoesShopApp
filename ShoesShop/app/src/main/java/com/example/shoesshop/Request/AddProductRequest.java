package com.example.shoesshop.Request;

import com.example.shoesshop.Model.Category;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AddProductRequest {
    private String name;
    private int price;
    @SerializedName("price_old")
    private int priceOld;
    private String description;
    private int like;

    @SerializedName("category_id")
    private String categoryId;

    @SerializedName("special")
    private boolean isSpecial;
    private String brand;
    private String shopId;
    private String size;
    private String color;

    public AddProductRequest(String name, int price, int priceOld, String description, int like, String categoryId, boolean isSpecial, String brand, String shopId, String size, String color) {
        this.name = name;
        this.price = price;
        this.priceOld = priceOld;
        this.description = description;
        this.like = like;
        this.categoryId = categoryId;
        this.isSpecial = isSpecial;
        this.brand = brand;
        this.shopId = shopId;
        this.size = size;
        this.color = color;
    }
}
