package com.example.seedbuy;

import android.util.Log;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

public class RetrofitClient {

    private static Retrofit retrofit;
    private static final String BASE_URL = "http://10.0.2.2:8000/api/"; // Base URL (use the correct API URL)
    private static final int TIMEOUT = 30;

    public static Retrofit getInstance() {
        if (retrofit == null) {
            // Create the Gson instance with lenient parsing
            Gson gson = new GsonBuilder()
                    .setLenient()  // Allow lenient JSON parsing
                    .create();

            // Setup OkHttpClient for logging
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                    .addInterceptor(interceptor)  // Add logging interceptor
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)  // Use the correct base URL
                    .addConverterFactory(GsonConverterFactory.create(gson))  // Use lenient Gson
                    .client(client)  // Add the OkHttp client with logging
                    .build();
        }
        return retrofit;
    }

    // Get ApiService instance
    public static ApiService getApiService() {
        return getInstance().create(ApiService.class);
    }
}
