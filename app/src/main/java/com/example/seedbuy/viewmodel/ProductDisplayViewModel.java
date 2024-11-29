// ProductDisplayViewModel.java

package com.example.seedbuy.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.seedbuy.model.Product;
import com.example.seedbuy.model.ProductResponse;
import com.example.seedbuy.network.ApiService;
import com.example.seedbuy.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class ProductDisplayViewModel extends AndroidViewModel {

    private MutableLiveData<List<Product>> productList = new MutableLiveData<>();
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false); // Initialize as false

    public ProductDisplayViewModel(Application application) {
        super(application);
    }

    // Getter for product list
    public LiveData<List<Product>> getProductList() {
        return productList;
    }

    // Getter for error LiveData
    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    // Getter for loading state
    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    // Fetch the products from the API using RetrofitClient
    public void fetchProducts() {
        isLoading.setValue(true); // Indicate loading

        ApiService apiService = RetrofitClient.getApiService();
        Call<ProductResponse> call = apiService.getAllProducts();

        call.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                isLoading.setValue(false); // Loading finished

                if (response.isSuccessful() && response.body() != null) {
                    Log.d("API Response", "Fetched products: " + response.body().getProducts());
                    productList.setValue(response.body().getProducts());
                } else {
                    Log.e("API Error", "Failed to fetch products: " + response.message());
                    errorMessage.setValue("Failed to fetch products: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                isLoading.setValue(false);
                Log.e("API Failure", "Error: " + t.getMessage());
                errorMessage.setValue("Network failure: " + t.getMessage());
            }
        });
    }


}
