package com.example.shoesshop.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.shoesshop.API.APIService;
import com.example.shoesshop.API.RetrofitClient;
import com.example.shoesshop.Model.Product;
import com.example.shoesshop.R;
import com.example.shoesshop.Response.DeleteProductResponse;
import com.example.shoesshop.Response.ProductResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailActivity extends AppCompatActivity {

    private APIService apiService;

    Product product;
    TextView tvNameProduct, tvPrice, tvCount, tvDescription;
    ImageView ivImage, ivBack, ivLike;
    Button btn_delete,btn_edit;

    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        // lấy giá trị id từ Intent
        id = getIntent().getStringExtra("id");

        anhXa();

        LoadProductDetail();

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteProduct(id);
            }
        });

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editProduct(id);
            }
        });
    }

    private void deleteProduct(String id){
        apiService = RetrofitClient.getClient().create(APIService.class);

        Call<DeleteProductResponse> call = apiService.deleteProduct(id);
        // xử dụng retrofit để lấy dữ liệu sản phẩm theo id
        call.enqueue(new Callback<DeleteProductResponse>() {
            @Override
            public void onResponse(Call<DeleteProductResponse> call, Response<DeleteProductResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ProductDetailActivity.this, "Đã xóa sản phẩm thành công", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(ProductDetailActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(ProductDetailActivity.this, "Lấy dữ liệu thất bại 1", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DeleteProductResponse> call, Throwable t) {
                Toast.makeText(ProductDetailActivity.this, "Lấy dữ liệu thất bại 2", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void editProduct(String id){
        Intent intent = new Intent(ProductDetailActivity.this, EditProductActivity.class);
        intent.putExtra("id", id);
        startActivity(intent); // Chỉ chạy Intent một lần với thông tin đính kèm
    }

    private void LoadProductDetail() {

        apiService = RetrofitClient.getClient().create(APIService.class);

        Call<ProductResponse> call = apiService.getProductById(id);

        // xử dụng retrofit để lấy dữ liệu sản phẩm theo id
        call.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if (response.isSuccessful()) {
                    ProductResponse productResponse = response.body();
                    product = productResponse.getData();

                    // gắn lại các giá trị tvNameProduct,tvPrice,ivImage,tvDescription
                    tvNameProduct.setText(product.getName());
                    tvPrice.setText(String.valueOf(product.getPrice()));
                    Glide.with(getApplicationContext()).load(product.getImage()).into(ivImage);
                    tvDescription.setText(product.getDescription());
                } else {
                    Toast.makeText(ProductDetailActivity.this, "Lấy dữ liệu thất bại 1", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                Toast.makeText(ProductDetailActivity.this, "Lấy dữ liệu thất bại 2", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void anhXa() {
        tvNameProduct = findViewById(R.id.prod_name);
        tvPrice = findViewById(R.id.prices);
        tvDescription = findViewById(R.id.detailsText);
        ivImage = findViewById(R.id.big_image);
        ivBack = findViewById(R.id.back_button);
        ivLike = findViewById(R.id.like_button);
        btn_edit = findViewById(R.id.btn_edit);
        btn_delete = findViewById(R.id.btn_delete);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}