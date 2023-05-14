package com.example.shoesshop.Response;

import com.example.shoesshop.Model.User;

public class UserResponse {
    private boolean success;

    private User data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }
}
