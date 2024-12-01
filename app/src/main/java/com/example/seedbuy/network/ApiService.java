package com.example.seedbuy.network;

import com.example.seedbuy.model.LoginRequest;
import com.example.seedbuy.model.LoginResponse;
import com.example.seedbuy.model.Order;
import com.example.seedbuy.model.Product;
import com.example.seedbuy.model.ProductResponse;
import com.example.seedbuy.model.PurchaseRequest;
import com.example.seedbuy.model.PurchaseResponse;
import com.example.seedbuy.model.RegistrationRequest;
import com.example.seedbuy.model.RegistrationResponse;
import com.example.seedbuy.model.SellerRegistrationRequest;


import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiService {

    // POST request for buyer registration
    @POST("registerBuyer")
    Call<RegistrationResponse> registerBuyer(@Body RegistrationRequest request);

    // POST request for seller registration
    @POST("registerSeller")
    Call<RegistrationResponse> registerSeller(@Body SellerRegistrationRequest request);

    // POST request for login
    @POST("login")
    Call<LoginResponse> login(@Body LoginRequest request);

    @Multipart
    @POST("products")
    Call<Product> addProduct(@Part("name") RequestBody name,
                             @Part("price") RequestBody price,
                             @Part("quantity") RequestBody quantity,
                             @Part("category") RequestBody category,
                             @Part MultipartBody.Part image);

    @GET("products")
    Call<ProductResponse> getAllProducts();

    @POST("purchase")
    Call<PurchaseResponse> purchase(@Body PurchaseRequest purchaseRequest);

    @GET("orders")  // The endpoint for fetching all orders
    Call<List<Order>> getAllOrders();
    @GET("products/{id}")
    Call<Product> getProductById(@Path("id") String productId);

    @Multipart
    @PUT("products/{id}")
    Call<Product> updateProduct(@Path("id") String productId,
                                @Part("name") RequestBody name,
                                @Part("price") RequestBody price,
                                @Part("quantity") RequestBody quantity,
                                @Part("category") RequestBody category,
                                @Part MultipartBody.Part image);



    @DELETE("products/{id}")
    Call<Void> deleteProduct(@Path("id") String productId);

}
