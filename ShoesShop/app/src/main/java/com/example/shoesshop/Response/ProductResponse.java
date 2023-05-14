package com.example.shoesshop.Response;

import com.example.shoesshop.Model.Product;

public class ProductResponse {

    private boolean success;

    private Product data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Product getData() {
        return data;
    }

    public void setData(Product data) {
        this.data = data;
    }
}
