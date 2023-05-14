package com.example.shoesshop.API;

import com.example.shoesshop.Request.AddProductRequest;
import com.example.shoesshop.Request.AddToCartRequest;
import com.example.shoesshop.Request.AddToOrderRequest;
import com.example.shoesshop.Request.LoginRequest;
import com.example.shoesshop.Request.OrderUpdateRequest;
import com.example.shoesshop.Request.RegisterRequest;
import com.example.shoesshop.Response.CartResponse;
import com.example.shoesshop.Response.CategoryResponse;
import com.example.shoesshop.Response.DeleteCartResponse;
import com.example.shoesshop.Response.DeleteProductResponse;
import com.example.shoesshop.Response.ListOrderResponse;
import com.example.shoesshop.Response.ListProductResponse;
import com.example.shoesshop.Response.LoginResponse;
import com.example.shoesshop.Response.OrderResponse;
import com.example.shoesshop.Response.ProductResponse;
import com.example.shoesshop.Response.RegisterResponse;
import com.example.shoesshop.Response.UserResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIService {

    // USER
    @POST("api/v1/auth/login")
    Call<LoginResponse> login(@Body LoginRequest request);

    @POST("api/v1/auth/register")
    Call<RegisterResponse> register(@Body RegisterRequest request);

    @Multipart
    @PUT("api/v1/users/uploadImage")
    Call<UserResponse> updateUser(@Part("id") RequestBody userId,
                                  @Part MultipartBody.Part image);

    @Headers({"Content-Type: application/json"})
    @PUT("api/v1/users/edit/{id}")
    Call<UserResponse> updateProfile(@Path("id") String id,
                                     @Body RegisterRequest request);

    // PRODUCT
    @GET("api/v1/product")
    Call<ListProductResponse> getAllProduct();

    @GET("api/v1/product/{id}")
    Call<ProductResponse> getProductById(@Path("id") String id);


    @POST("api/v1/product/add")
    Call<ProductResponse> addNewProduct(@Body AddProductRequest request);

    @DELETE("api/v1/product/delete/{id}")
    Call<DeleteProductResponse> deleteProduct(@Path("id") String id);

    @PUT("api/v1/product/edit/{id}")
    Call<DeleteProductResponse> editProduct(@Path("id") String id, @Body AddProductRequest request);


    @Multipart
    @PUT("api/v1/product/uploadImage")
    Call<ProductResponse> updateProduct(@Part("id") RequestBody pID,
                                  @Part MultipartBody.Part image);

    // CATEGORY
    @GET("api/v1/category")
    Call<CategoryResponse> getAllCategory();

    @GET("api/v1/category/{id}")
    Call<ListProductResponse> getProductOfCategory(@Path("id") String id);

    @GET("api/v1/category/{id}")
    Call<ListProductResponse> getListSortByLike(@Path("id") String id, @Query("sort") String sortBy);


    // CART
    @GET("api/v1/cart")
    Call<CartResponse> getAllCart();

    @GET("api/v1/cart/{userId}")
    Call<CartResponse> getCart(@Path("userId") String userId);

    @POST("api/v1/cart/add")
    Call<CartResponse> addToCart(@Body AddToCartRequest request);

    @PUT("api/v1/cart/edit/{type}/{userId}/{pId}")
    Call<CartResponse> handleQuantity(@Path("type") String type,
                                      @Path("userId") String userId,
                                      @Path("pId") String pId);

    @DELETE("api/v1/cart/delete/{userID}")
    Call<DeleteCartResponse> deleteCart(@Path("userID") String userID);

    // ORDER
    @GET("api/v1/order")
    Call<ListOrderResponse> getAllOrder();

    @POST("api/v1/order/add")
    Call<OrderResponse> addOrder(@Body AddToOrderRequest order);

    @GET("api/v1/order/{id}")
    Call<ListOrderResponse> getListOrderByUserID(@Path("id") String id);

    @GET("api/v1/order/item/{id}")
    Call<OrderResponse> getOrderByID(@Path("id") String id);

    @PUT("api/v1/order/edit/{id}")
    Call<OrderResponse> updateStatus(@Path("id") String id, @Body OrderUpdateRequest order);
}
