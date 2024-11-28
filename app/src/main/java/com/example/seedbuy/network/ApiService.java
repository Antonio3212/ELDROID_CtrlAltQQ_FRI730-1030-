package com.example.seedbuy.network;

import com.example.seedbuy.model.LoginRequest;
import com.example.seedbuy.model.LoginResponse;
import com.example.seedbuy.model.RegistrationRequest;
import com.example.seedbuy.model.RegistrationResponse;
import com.example.seedbuy.model.SellerRegistrationRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    // POST request for buyer registration
    @POST("registerBuyer")
    Call<RegistrationResponse> registerBuyer(@Body RegistrationRequest request);

    @POST("registerSeller") // Update to match the new endpoint
    Call<RegistrationResponse> registerSeller(@Body SellerRegistrationRequest request);

    @POST("login") // Update to match the new endpoint
    Call<LoginResponse> login(@Body LoginRequest request);

}
