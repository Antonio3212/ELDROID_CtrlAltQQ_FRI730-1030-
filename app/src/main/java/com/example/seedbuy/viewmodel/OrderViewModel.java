package com.example.seedbuy.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.seedbuy.model.Order;
import com.example.seedbuy.network.ApiService;
import com.example.seedbuy.network.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderViewModel extends AndroidViewModel {
    private MutableLiveData<List<Order>> ordersLiveData;
    private ApiService apiService;

    public OrderViewModel(Application application) {
        super(application);
        ordersLiveData = new MutableLiveData<>();
        apiService = RetrofitClient.getApiService(); // Get the ApiService instance
    }

    // Get LiveData for observing the orders
    public LiveData<List<Order>> getOrders() {
        return ordersLiveData;
    }

    public void fetchOrders() {
        // Make the network request to get all orders
        apiService.getAllOrders().enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // If the response is successful, update LiveData
                    ordersLiveData.setValue(response.body());
                } else {
                    // If the response is not successful (e.g., no data), set LiveData to null
                    ordersLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {
                // If the request fails (e.g., network error), set LiveData to null
                ordersLiveData.setValue(null);
            }
        });
    }
}
