package com.example.shoesshop.Response;

import com.example.shoesshop.Model.User;
import com.google.gson.annotations.SerializedName;

public class RegisterResponse {
    @SerializedName("success")
    private boolean success;

    @SerializedName("user")
    private User user;

    public boolean isSuccess() {
        return success;
    }

    public User getUser() {
        return user;
    }
}
