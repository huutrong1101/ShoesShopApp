package com.example.shoesshop.Model;

import com.google.gson.annotations.SerializedName;

public class ProductInCart {
    @SerializedName("_id")
    private String id;
    @SerializedName("quantity")
    private int quantity;
    @SerializedName("productId")
    private String productId;

    public ProductInCart(int quantity, String productId) {
        this.quantity = quantity;
        this.productId = productId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
