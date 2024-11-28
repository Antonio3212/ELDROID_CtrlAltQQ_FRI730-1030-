package com.example.seedbuy.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.seedbuy.model.RegistrationResponse;
import com.example.seedbuy.model.SellerRegistrationRequest;
import com.example.seedbuy.network.ApiService;
import com.example.seedbuy.network.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SellerRegisterViewModel extends AndroidViewModel {

    private MutableLiveData<RegistrationResponse> registrationResponse;
    private MutableLiveData<String> errorMessage;

    public SellerRegisterViewModel(Application application) {
        super(application);
        registrationResponse = new MutableLiveData<>();
        errorMessage = new MutableLiveData<>();
    }

    public LiveData<RegistrationResponse> getRegistrationResponse() {
        return registrationResponse;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    // Method to handle seller registration
    public void registerSeller(SellerRegistrationRequest request) {
        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);
        Call<RegistrationResponse> call = apiService.registerSeller(request);

        call.enqueue(new Callback<RegistrationResponse>() {
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
