package com.example.shoesshop.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shoesshop.API.APIService;
import com.example.shoesshop.API.RetrofitClient;
import com.example.shoesshop.Adapter.BillAdapter;
import com.example.shoesshop.Model.Category;
import com.example.shoesshop.Model.Order;
import com.example.shoesshop.Model.Product;
import com.example.shoesshop.Model.User;
import com.example.shoesshop.R;
import com.example.shoesshop.Response.CategoryResponse;
import com.example.shoesshop.Response.ListOrderResponse;
import com.example.shoesshop.Response.ListProductResponse;
import com.example.shoesshop.SharedPrefManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RelativeLayout logoutBtn, newProduct, viewProduct, viewCustomersOrders,chart;

    private TextView top_text_view;

    private APIService apiService;

    private List<Product> productList;

    private List<Category> cateList;

    private List<Order> listOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        anhXa();

        getProductData();

        getCateData();

        getListOrder();

        User user = SharedPrefManager.getInstance(getApplicationContext()).getUser();
        top_text_view.setText("Welcome " + user.getUsername());

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPrefManager.getInstance(getApplicationContext()).logout();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });
        newProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddProductActivity.class);
                startActivity(intent);
            }
        });
        viewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ViewAllProduct.class);
                startActivity(intent);
            }
        });
        viewCustomersOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, BillActivity.class);
                startActivity(intent);
            }
        });
        chart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ChartActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getProductData() {
        apiService = RetrofitClient.getClient().create(APIService.class);

        //Get API -> sử dụng Retrofit để lấy dữ liệu từ api
        Call<ListProductResponse> call = apiService.getAllProduct();
        call.enqueue(new Callback<ListProductResponse>() {
            @Override
            public void onResponse(Call<ListProductResponse> call, Response<ListProductResponse> response) {
                if (response.isSuccessful()) {
                    productList = response.body().getData();
                    SharedPrefManager.getInstance(getApplicationContext()).saveData(productList);
                } else {
                    Toast.makeText(MainActivity.this, "Lấy dữ liệu thất bại 1", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ListProductResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Lấy dữ liệu thất bại 2", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getListOrder(){
        apiService = RetrofitClient.getClient().create(APIService.class);

        Call<ListOrderResponse> call = apiService.getAllOrder();

        call.enqueue(new Callback<ListOrderResponse>() {
            @Override
            public void onResponse(Call<ListOrderResponse> call, Response<ListOrderResponse> response) {
                if (response.isSuccessful()) {
                    listOrder = response.body().getData();

                    List<Product> productList = SharedPrefManager.getInstance(getApplicationContext()).getData();
                    User user = SharedPrefManager.getInstance(getApplicationContext()).getUser();

                    List<Order> listOrderShop = new ArrayList<>();

                    for (Order o : listOrder){
                        for (Product p : productList){
                            if (o.getOrderItems().get(0).getProductId().equals(p.getId()) && p.getShopId().equals(user.getId())){
                                listOrderShop.add(o);
                            }
                        }
                    }

                    Collections.reverse(listOrderShop);
                    SharedPrefManager.getInstance(getApplicationContext()).saveOrderData(listOrderShop);

                } else {
                    Toast.makeText(MainActivity.this, "Lấy dữ liệu thất bại 1", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ListOrderResponse> call, Throwable t) {
                Log.e("Error", t.getMessage(), t);
            }
        });
    }

    private void getCateData(){
        //Get API -> sử dụng Retrofit để lấy dữ liệu từ api
        Call<CategoryResponse> call = apiService.getAllCategory();
        call.enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                if (response.isSuccessful()) {
                    cateList = response.body().getData();

                    SharedPrefManager.getInstance(getApplicationContext()).saveCateData(cateList);
                } else {
                    Toast.makeText(MainActivity.this, "Lấy dữ liệu thất bại 1", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Lấy dữ liệu thất bại 2", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void anhXa() {
        logoutBtn = findViewById(R.id.logout_btn);
        newProduct = findViewById(R.id.new_product_layout);
        viewProduct = findViewById(R.id.view_all_products);
        viewCustomersOrders = findViewById(R.id.view_customer_order);
        top_text_view = findViewById(R.id.top_text_view);
        chart = findViewById(R.id.chart);
    }
}