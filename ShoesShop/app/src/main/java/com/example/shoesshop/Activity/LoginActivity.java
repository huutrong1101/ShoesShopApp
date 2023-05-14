package com.example.shoesshop.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shoesshop.API.APIService;
import com.example.shoesshop.API.RetrofitClient;
import com.example.shoesshop.Model.User;
import com.example.shoesshop.R;
import com.example.shoesshop.Request.LoginRequest;
import com.example.shoesshop.Response.LoginResponse;
import com.example.shoesshop.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private APIService apiService;

    EditText login_email,login_pass;

    AppCompatButton login_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        anhXa();

        if (SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }

        apiService = RetrofitClient.getClient().create(APIService.class);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleLogin();
            }
        });
    }

    private void anhXa(){
        login_email = findViewById(R.id.login_email);
        login_pass = findViewById(R.id.login_pass);
        login_btn = findViewById(R.id.login_btn);
    }
    
    private void handleLogin(){
        final String email = login_email.getText().toString();
        final String password = login_pass.getText().toString();

        LoginRequest request = new LoginRequest(email, password);
        Call<LoginResponse> call = apiService.login(request);

        if (TextUtils.isEmpty(email)){
            login_email.setError("Please enter your email");
            login_email.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)){
            login_pass.setError("Please enter your password");
            login_pass.requestFocus();
            return;
        }

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                try {
                    if (response.isSuccessful()) {
                        LoginResponse loginResponse = response.body();
                        User user = loginResponse.getUser();

                        if (user.getRole().equals("shop")){
                            Toast.makeText(getApplicationContext(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                            SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);
                            finish();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Tài khoản không có quyền đăng nhập", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Lỗi:" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
