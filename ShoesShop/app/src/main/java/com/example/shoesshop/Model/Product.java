package com.example.shoesshop.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Product {
    @SerializedName("_id")
    private String id;
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
    private String image;
    private String shopId;
    private List<String> size;
    private List<String> color;

    private int quantity;

    public Product(String id, String name, int price, int priceOld, String description, int like, String categoryId, boolean isSpecial, String brand, String image, String shopId, List<String> size, List<String> color, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.priceOld = priceOld;
        this.description = description;
        this.like = like;
        this.categoryId = categoryId;
        this.isSpecial = isSpecial;
        this.brand = brand;
        this.image = image;
        this.shopId = shopId;
        this.size = size;
        this.color = color;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPriceOld() {
        return priceOld;
    }

    public void setPriceOld(int priceOld) {
        this.priceOld = priceOld;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public boolean isSpecial() {
        return isSpecial;
    }

    public void setSpecial(boolean special) {
        isSpecial = special;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public List<String> getSize() {
        return size;
    }

    public void setSize(List<String> size) {
        this.size = size;
    }

    public List<String> getColor() {
        return color;
    }

    public void setColor(List<String> color) {
        this.color = color;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
