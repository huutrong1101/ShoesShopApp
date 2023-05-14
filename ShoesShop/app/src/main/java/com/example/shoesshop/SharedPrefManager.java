package com.example.shoesshop;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.shoesshop.Activity.LoginActivity;
import com.example.shoesshop.Model.Category;
import com.example.shoesshop.Model.Order;
import com.example.shoesshop.Model.Product;
import com.example.shoesshop.Model.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class SharedPrefManager {
    //Khởi tạo các hằng key
    private static final String SHARE_PREF_NAME = "volleyregisterlogin";

    private static final String KEY_USERNAME = "keyusername";
    private static final String KEY_EMAIL = "keyemail";
    private static final String KEY_ID = "keyid";

    private static final String KEY_PASSWORD = "keypassword";

    private static final String KEY_ROLE = "keyrole";
    private static final String KEY_IMAGES = "keyimages";

    private static final String KEY_DATA = "keydatas";

    private static SharedPrefManager mIstance;
    private static Context ctx;

    public SharedPrefManager(Context context) {
        ctx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mIstance == null) {
            mIstance = new SharedPrefManager(context);
        }
        return mIstance;
    }

    public void userLogin(User user) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARE_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_ID, user.getId());
        editor.putString(KEY_USERNAME, user.getUsername());
        editor.putString(KEY_EMAIL, user.getEmail());
        editor.putString(KEY_PASSWORD, user.getPassword());
        editor.putString(KEY_ROLE, user.getRole());
        editor.putString(KEY_IMAGES, user.getImage());
        editor.apply();
    }

    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARE_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME, null) != null;
    }

    public User getUser() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARE_PREF_NAME, Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getString(KEY_ID, null),
                sharedPreferences.getString(KEY_USERNAME, null),
                sharedPreferences.getString(KEY_EMAIL, null),
                sharedPreferences.getString(KEY_PASSWORD, null),
                sharedPreferences.getString(KEY_ROLE, null),
                sharedPreferences.getString(KEY_IMAGES, null)
        );
    }


    public void logout() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARE_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        ctx.startActivity(new Intent(ctx, LoginActivity.class));
    }

    public void saveData(List<Product> productList) {
        SharedPreferences sharedPref = ctx.getSharedPreferences("DATA", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        Gson gson = new Gson();
        String jsonProductList = gson.toJson(productList);
        editor.putString("product_list", jsonProductList);
        editor.apply();
    }

    public List<Product> getData() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences("DATA", Context.MODE_PRIVATE);
        String jsonProductList = sharedPreferences.getString("product_list", "");
        Gson gson = new Gson();
        Type type = new TypeToken<List<Product>>() {
        }.getType();
        List<Product> productList = gson.fromJson(jsonProductList, type);
        return productList;
    }

    public void saveCateData(List<Category> categoryList) {
        SharedPreferences sharedPref = ctx.getSharedPreferences("DATACATE", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        Gson gson = new Gson();
        String jsonCateList = gson.toJson(categoryList);
        editor.putString("cate_list", jsonCateList);
        editor.apply();
    }

    public List<Category> getCateData() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences("DATACATE", Context.MODE_PRIVATE);
        String jsonCateList = sharedPreferences.getString("cate_list", "");
        Gson gson = new Gson();
        Type type = new TypeToken<List<Category>>() {
        }.getType();
        List<Category> categoryList = gson.fromJson(jsonCateList, type);
        return categoryList;
    }

    public void saveOrderData(List<Order> orderList) {
        SharedPreferences sharedPref = ctx.getSharedPreferences("DATAORDER", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        Gson gson = new Gson();
        String jsonOrderList = gson.toJson(orderList);
        editor.putString("order_list", jsonOrderList);
        editor.apply();
    }

    public List<Order> getOrderData() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences("DATAORDER", Context.MODE_PRIVATE);
        String jsonOrderList = sharedPreferences.getString("order_list", "");
        Gson gson = new Gson();
        Type type = new TypeToken<List<Order>>() {
        }.getType();
        List<Order> orderList = gson.fromJson(jsonOrderList, type);
        return orderList;
    }
}
