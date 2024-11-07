package com.example.seedbuy;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("seller_register.php")
    Call<RegistrationResponse> registerSeller(@Body RegistrationRequest request);

    @POST("login.php")
    Call<LoginResponse> login(@Body LoginRequest request);

    @POST("buyer_register.php")  // This is the API for registering a buyer
    Call<RegistrationResponse> registerBuyer(@Body BuyerRegistrationRequest request);
}
