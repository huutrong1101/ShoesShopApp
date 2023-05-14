package com.example.shoesshop.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shoesshop.API.APIService;
import com.example.shoesshop.API.RetrofitClient;
import com.example.shoesshop.Adapter.BillAdapter;
import com.example.shoesshop.Model.Category;
import com.example.shoesshop.Model.Order;
import com.example.shoesshop.Model.Product;
import com.example.shoesshop.Model.ProductInCart;
import com.example.shoesshop.Model.User;
import com.example.shoesshop.R;
import com.example.shoesshop.Response.ListOrderResponse;
import com.example.shoesshop.SharedPrefManager;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BillActivity extends AppCompatActivity {
    private RecyclerView recyclerView;

    private APIService apiService;

    private List<Order> listOrder;

    private BillAdapter adapter;

    private ImageView back;

    private TextView total_revenue_textview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);

        total_revenue_textview = findViewById(R.id.total_revenue_textview);

        back = findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        getListOrder();
    }

    private void getListOrder(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView = findViewById(R.id.unpaid_recycler_view);
        recyclerView.setLayoutManager(linearLayoutManager);

        Integer totalPrice = 0;
        List<Order> listOrderShop = SharedPrefManager.getInstance(getApplicationContext()).getOrderData();
        List<Product> productList = SharedPrefManager.getInstance(getApplicationContext()).getData();

        for (Order o : listOrderShop){
            for (Product p : productList) {
                for (ProductInCart pCart : o.getOrderItems()) {
                    if (p.getId().equals(pCart.getProductId())) {
                        totalPrice += pCart.getQuantity() * p.getPrice();
                        break;
                    }
                }
            }
        }
        DecimalFormat decimalFormat = new DecimalFormat("#,###.##");
        String formattedTotalPrice = decimalFormat.format(totalPrice);
        total_revenue_textview.setText("Tổng tiền: " + formattedTotalPrice +"đ");

        adapter = new BillAdapter(BillActivity.this, listOrderShop);
        recyclerView.setAdapter(adapter);
    }
}