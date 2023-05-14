package com.example.shoesshop.Response;

import com.example.shoesshop.Model.Category;

import java.util.List;

public class CategoryResponse {
    private boolean success;
    private int count;
    private List<Category> data;

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

    public List<Category> getData() {
        return data;
    }

    public void setData(List<Category> data) {
        this.data = data;
    }
}
