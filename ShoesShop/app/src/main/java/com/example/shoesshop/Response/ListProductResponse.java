package com.example.shoesshop.Response;

import com.example.shoesshop.Model.Product;

import java.util.List;

public class ListProductResponse {
/**/
    private boolean success;
    private int count;
    private List<Product> data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Product> getData() {
        return data;
    }

    public void setData(List<Product> data) {
        this.data = data;
    }
}
