package com.example.seedbuy.network;

import com.example.seedbuy.model.LoginRequest;
import com.example.seedbuy.model.LoginResponse;
import com.example.seedbuy.model.Product;
import com.example.seedbuy.model.RegistrationRequest;
import com.example.seedbuy.model.RegistrationResponse;
import com.example.seedbuy.model.SellerRegistrationRequest;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

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


}
