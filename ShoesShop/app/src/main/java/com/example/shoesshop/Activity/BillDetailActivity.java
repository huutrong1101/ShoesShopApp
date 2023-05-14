package com.example.shoesshop.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shoesshop.API.APIService;
import com.example.shoesshop.API.RetrofitClient;
import com.example.shoesshop.Adapter.BillAdapter;
import com.example.shoesshop.Adapter.OrderAdapter;
import com.example.shoesshop.Model.Order;
import com.example.shoesshop.Model.Product;
import com.example.shoesshop.Model.ProductInCart;
import com.example.shoesshop.R;
import com.example.shoesshop.Request.AddProductRequest;
import com.example.shoesshop.Request.OrderUpdateRequest;
import com.example.shoesshop.Response.ListOrderResponse;
import com.example.shoesshop.Response.OrderResponse;
import com.example.shoesshop.SharedPrefManager;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BillDetailActivity extends AppCompatActivity {

    private APIService apiService;

    private OrderAdapter adapter;

    TextView name,phone,shippingAddress,time,status,cartActivityTotalPriceTv;

    private RecyclerView recyclerView;

    private Order order;

    Button btn_confirm;

    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_detail);

        anhXa();

        getListItemInOrder();

        String id = getIntent().getStringExtra("id");

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmBill(id);
            }
        });
    }

    private void anhXa(){
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        shippingAddress = findViewById(R.id.shippingAddress);
        time = findViewById(R.id.time);
        status = findViewById(R.id.status);
        cartActivityTotalPriceTv = findViewById(R.id.cartActivityTotalPriceTv);
        btn_confirm = findViewById(R.id.btn_confirm);
        back = findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void confirmBill(String id){
        apiService = RetrofitClient.getClient().create(APIService.class);

        OrderUpdateRequest request = new OrderUpdateRequest("Completed");

        Call<OrderResponse> call = apiService.updateStatus(id,request);

        call.enqueue(new Callback<OrderResponse>() {
            @Override
            public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(BillDetailActivity.this, "Cập nhật trạng thái đơn hàng thành công", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(BillDetailActivity.this, BillActivity.class));
                } else {
                    Toast.makeText(BillDetailActivity.this, "Lấy dữ liệu thất bại 1", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t) {
                Log.e("Error", t.getMessage(), t);
            }
        });
    }


    public void getListItemInOrder(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView = findViewById(R.id.billDetailRecyclerView);
        recyclerView.setLayoutManager(linearLayoutManager);

        // lấy giá trị id từ Intent
        String id = getIntent().getStringExtra("id");
        List<Product> productList = SharedPrefManager.getInstance(getApplicationContext()).getData();

        //Get API -> sử dụng Retrofit để lấy dữ liệu từ api
        apiService = RetrofitClient.getClient().create(APIService.class);
        Call<OrderResponse> call = apiService.getOrderByID(id);

        call.enqueue(new Callback<OrderResponse>() {
            @Override
            public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                if (response.isSuccessful()) {
                    order = response.body().getData();

                    // lấy các giá trị text
                    String dateTimeString = order.getDateOrdered();
                    SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
                    SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy - HH:mm");

                    try {
                        Date date = inputFormat.parse(dateTimeString);
                        String formattedDateTime = outputFormat.format(date);

                        time.setText(formattedDateTime);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    name.setText(order.getName());
                    phone.setText(order.getPhone());
                    shippingAddress.setText(order.getShippingAddress());
                    status.setText(order.getStatus());

                    Integer totalPrice = 0;

                    List<ProductInCart> productInCart = new ArrayList<>();

                    if (order.getOrderItems() != null && productList != null) {
                        for (Product p : productList) {
                            for (ProductInCart pCart : order.getOrderItems()) {
                                if (p.getId().equals(pCart.getProductId())) {
                                    productInCart.add(pCart);
                                    totalPrice += pCart.getQuantity() * p.getPrice();
                                    break;
                                }
                            }
                        }
                    }

                    adapter = new OrderAdapter(productInCart, BillDetailActivity.this,cartActivityTotalPriceTv);
                    recyclerView.setAdapter(adapter);

                    DecimalFormat decimalFormat = new DecimalFormat("#,###.##");
                    String formattedTotalPrice = decimalFormat.format(totalPrice);
                    cartActivityTotalPriceTv.setText(formattedTotalPrice);
                } else {
                    Toast.makeText(BillDetailActivity.this, "Lấy dữ liệu thất bại 1", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t) {
                Log.e("Error", t.getMessage(), t);
            }
        });
    }
}