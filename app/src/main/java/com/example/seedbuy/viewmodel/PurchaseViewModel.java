// viewmodel/PurchaseViewModel.java
package com.example.seedbuy.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.seedbuy.model.PurchaseRequest;
import com.example.seedbuy.model.PurchaseResponse;
import com.example.seedbuy.network.ApiService;
import com.example.seedbuy.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PurchaseViewModel extends ViewModel {

    private MutableLiveData<PurchaseResponse> purchaseResponseLiveData = new MutableLiveData<>();
    private MutableLiveData<String> errorLiveData = new MutableLiveData<>();

    private ApiService apiService;

    public PurchaseViewModel() {
        apiService = RetrofitClient.getApiService();
    }

    // Exposed LiveData for the activity to observe
    public LiveData<PurchaseResponse> getPurchaseResponse() {
        return purchaseResponseLiveData;
    }

    public LiveData<String> getError() {
        return errorLiveData;
    }

    // Method to place an order
    public void placeOrder(PurchaseRequest purchaseRequest) {
        apiService.purchase(purchaseRequest).enqueue(new Callback<PurchaseResponse>() {
            @Override
            public void onResponse(Call<PurchaseResponse> call, Response<PurchaseResponse> response) {
                if (response.isSuccessful()) {
                    purchaseResponseLiveData.setValue(response.body());
                } else {
                    errorLiveData.setValue("Error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<PurchaseResponse> call, Throwable t) {
                errorLiveData.setValue("Failure: " + t.getMessage());
            }
        });
    }
}
