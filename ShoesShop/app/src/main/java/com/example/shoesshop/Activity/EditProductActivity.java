package com.example.shoesshop.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.shoesshop.API.APIService;
import com.example.shoesshop.API.RetrofitClient;
import com.example.shoesshop.Model.Category;
import com.example.shoesshop.Model.Product;
import com.example.shoesshop.Model.User;
import com.example.shoesshop.R;
import com.example.shoesshop.Request.AddProductRequest;
import com.example.shoesshop.Response.DeleteProductResponse;
import com.example.shoesshop.Response.ProductResponse;
import com.example.shoesshop.SharedPrefManager;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProductActivity extends AppCompatActivity {
    private APIService apiService;
    Spinner categorySpinner, brandSpinner, sizeTypeSpinner, sizeSpinner;
    String category = "";
    String brand = "";
    String sizeType = "";

    String name = "";

    Button editBtn;
    Button choose_img;

    String[] sizeTypeList = {"Select Size", "M", "XL", "S", "2XL"};

    Product product;

    private EditText nameEt,priceEt,colorEt,descriptionEt;
    private ProgressBar progressBar;

    List<Category> listCate, listBrand;

    String id,cateID;

    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

        apiService = RetrofitClient.getClient().create(APIService.class);

        id = getIntent().getStringExtra("id");

        listCate = SharedPrefManager.getInstance(getApplicationContext()).getCateData();

        listBrand = SharedPrefManager.getInstance(getApplicationContext()).getCateData();

        String[] categoriesList = {"Select Category"};
        String[] brandList = {"Select Brand"};

        if (listCate != null) {
            for (Category category : listCate) {
                String categoryName = category.getName();
                categoriesList = Arrays.copyOf(categoriesList, categoriesList.length + 1);
                categoriesList[categoriesList.length - 1] = categoryName;
            }
        } else {
            Toast.makeText(this, "Không có giá trị", Toast.LENGTH_SHORT).show();
        }

        if (listBrand != null) {
            for (Category brand : listBrand) {
                String brandName = brand.getName();
                brandList = Arrays.copyOf(brandList, brandList.length + 1);
                brandList[brandList.length - 1] = brandName;
            }
        } else {
            Toast.makeText(this, "Không có giá trị", Toast.LENGTH_SHORT).show();
        }

        initAll();

        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(EditProductActivity.this, android.R.layout.simple_list_item_1, categoriesList);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);

        ArrayAdapter<String> brandAdapter = new ArrayAdapter<String>(EditProductActivity.this, android.R.layout.simple_list_item_1, brandList);
        brandAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        brandSpinner.setAdapter(brandAdapter);

        ArrayAdapter<String> sizeTypeAdapter = new ArrayAdapter<String>(EditProductActivity.this, android.R.layout.simple_list_item_1, sizeTypeList);
        sizeTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sizeTypeSpinner.setAdapter(sizeTypeAdapter);

        getProduct(id);

        SettingClickListners();

        choose_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = SharedPrefManager.getInstance(getApplicationContext()).getUser();

                String name = nameEt.getText().toString();
                String price = priceEt.getText().toString();
                String color = colorEt.getText().toString();
                String description = descriptionEt.getText().toString();

                //Get API -> sử dụng Retrofit để lấy dữ liệu từ api
                AddProductRequest request = new AddProductRequest(name,Integer.parseInt(price),Integer.parseInt(price) * 2,
                        description, 0,cateID,
                        true,brand,user.getId(),sizeType,color);
                Call<DeleteProductResponse> call = apiService.editProduct(id,request);
                call.enqueue(new Callback<DeleteProductResponse>() {
                    @Override
                    public void onResponse(Call<DeleteProductResponse> call, Response<DeleteProductResponse> response) {
                        if (response.isSuccessful()) {
                            Intent intent = new Intent(EditProductActivity.this, UploadImageActivity.class);
                            intent.putExtra("id", product.getId());
                            startActivity(intent); // Chỉ chạy Intent một lần với thông tin đính kèm

                            Toast.makeText(EditProductActivity.this, "Cập nhật nội dung sản phẩm thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(EditProductActivity.this, "Lấy dữ liệu thất bại 1", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<DeleteProductResponse> call, Throwable t) {
                        Toast.makeText(EditProductActivity.this, "Lấy dữ liệu thất bại 2", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = SharedPrefManager.getInstance(getApplicationContext()).getUser();

                String name = nameEt.getText().toString();
                String price = priceEt.getText().toString();
                String color = colorEt.getText().toString();
                String description = descriptionEt.getText().toString();

                //Get API -> sử dụng Retrofit để lấy dữ liệu từ api
                AddProductRequest request = new AddProductRequest(name,Integer.parseInt(price),Integer.parseInt(price) * 2,
                        description, 0,cateID,
                        true,brand,user.getId(),sizeType,color);
                Call<DeleteProductResponse> call = apiService.editProduct(id,request);
                call.enqueue(new Callback<DeleteProductResponse>() {
                    @Override
                    public void onResponse(Call<DeleteProductResponse> call, Response<DeleteProductResponse> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(EditProductActivity.this, "Cập nhật nội dung sản phẩm thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(EditProductActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(EditProductActivity.this, "Lấy dữ liệu thất bại 1", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<DeleteProductResponse> call, Throwable t) {
                        Toast.makeText(EditProductActivity.this, "Lấy dữ liệu thất bại 2", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void SettingClickListners() {
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position < listCate.size()) {
                    category = String.valueOf(parent.getItemAtPosition(position));
                    cateID = listCate.get(position).getId();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        brandSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                brand = String.valueOf(parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        sizeTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sizeType = String.valueOf(parent.getItemAtPosition(position));
                if (sizeType.equals("")) {

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void getProduct(String id){
        apiService = RetrofitClient.getClient().create(APIService.class);

        Call<ProductResponse> call = apiService.getProductById(id);

        // xử dụng retrofit để lấy dữ liệu sản phẩm theo id
        call.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if (response.isSuccessful()) {
                    ProductResponse productResponse = response.body();
                    product = productResponse.getData();

                    StringBuilder stringBuilder = new StringBuilder();
                    for(String value : product.getColor()) {
                        stringBuilder.append(value).append(" ");
                    }

                    nameEt.setText(product.getName());
                    priceEt.setText(String.valueOf(product.getPrice()));
                    colorEt.setText(stringBuilder.toString());
                    descriptionEt.setText(product.getDescription());

                    for (int i = 0; i < listCate.size(); i++) {
                       if (listCate.get(i).getId().equals(product.getCategoryId())){
                           categorySpinner.setSelection(i);
                           brandSpinner.setSelection(i);
                       }
                    }
                } else {
                    Toast.makeText(EditProductActivity.this, "Lấy dữ liệu thất bại 1", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                Toast.makeText(EditProductActivity.this, "Lấy dữ liệu thất bại 2", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initAll() {
        categorySpinner = findViewById(R.id.product_category_Spinner);
        brandSpinner = findViewById(R.id.product_brand_Spinner);
        sizeTypeSpinner = findViewById(R.id.product_size_type_Spinner);
        sizeSpinner = findViewById(R.id.product_size_Spinner);
        progressBar = findViewById(R.id.progress_bar);

        editBtn = findViewById(R.id.add_btn);
        choose_img = findViewById(R.id.choose_img);

        nameEt=findViewById(R.id.product_name_et);
        priceEt=findViewById(R.id.price_et);
        colorEt=findViewById(R.id.color_et);
        descriptionEt=findViewById(R.id.description_tv);

        back = findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}