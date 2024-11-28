// BuyerRegisterViewModel.java
package com.example.seedbuy.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.seedbuy.model.RegistrationRequest;
import com.example.seedbuy.model.RegistrationResponse;
import com.example.seedbuy.network.ApiService;
import com.example.seedbuy.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BuyerRegisterViewModel extends AndroidViewModel {

    private ApiService apiService;

    private MutableLiveData<RegistrationResponse> registrationResponse = new MutableLiveData<>();
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public BuyerRegisterViewModel(Application application) {
        super(application);
        apiService = RetrofitClient.getApiService();
    }

    public LiveData<RegistrationResponse> getRegistrationResponse() {
        return registrationResponse;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    // Method to handle the registration logic
    public void registerBuyer(RegistrationRequest request) {
        apiService.registerBuyer(request).enqueue(new Callback<RegistrationResponse>() {
            @Override
            public void onResponse(Call<RegistrationResponse> call, Response<RegistrationResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    registrationResponse.setValue(response.body());
                } else {
                    errorMessage.setValue("Registration failed: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<RegistrationResponse> call, Throwable t) {
                errorMessage.setValue("Error: " + t.getMessage());
            }
        });
    }
}
