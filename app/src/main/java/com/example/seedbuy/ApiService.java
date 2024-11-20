package com.example.seedbuy;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    // POST request for buyer registration
    @POST("registerBuyer")
    Call<RegistrationResponse> registerBuyer(@Body RegistrationRequest request);

    @POST("registerSeller") // Update to match the new endpoint
    Call<RegistrationResponse> registerSeller(@Body SellerRegistrationRequest request);
}
