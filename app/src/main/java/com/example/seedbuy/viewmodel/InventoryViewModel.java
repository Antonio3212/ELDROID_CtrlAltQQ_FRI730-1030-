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

import java.io.File;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InventoryViewModel extends AndroidViewModel {

    private ApiService apiService;
    private MutableLiveData<List<Product>> productsLiveData = new MutableLiveData<>();
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false); // Default to false

    public InventoryViewModel(Application application) {
        super(application);
        apiService = RetrofitClient.getApiService(); // Initialize the ApiService
    }

    // Get products live data
    public LiveData<List<Product>> getProductsLiveData() {
        return productsLiveData;
    }

    // Get error message live data
    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    // Get loading status live data
    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    // Fetch products from the API
    public void fetchProducts() {
        isLoading.setValue(true); // Show loading indicator

        Call<ProductResponse> call = apiService.getAllProducts();
        call.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                isLoading.setValue(false); // Hide loading indicator

                if (response.isSuccessful() && response.body() != null) {
                    Log.d("InventoryViewModel", "Fetched products: " + response.body().getProducts());
                    productsLiveData.setValue(response.body().getProducts());
                } else {
                    Log.e("InventoryViewModel", "Failed to fetch products: " + response.message());
                    errorMessage.setValue("Failed to fetch products: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                isLoading.setValue(false); // Hide loading indicator
                Log.e("InventoryViewModel", "Error: " + t.getMessage());
                errorMessage.setValue("Network failure: " + t.getMessage());
            }
        });
    }

    // Add a new product
    public void addProduct(String name, String price, String quantity, String category, File imageFile) {
        // Prepare the request bodies
        RequestBody nameBody = RequestBody.create(MultipartBody.FORM, name);
        RequestBody priceBody = RequestBody.create(MultipartBody.FORM, price);
        RequestBody quantityBody = RequestBody.create(MultipartBody.FORM, quantity);
        RequestBody categoryBody = RequestBody.create(MultipartBody.FORM, category);

        MultipartBody.Part imagePart = null;
        if (imageFile != null) {
            imagePart = MultipartBody.Part.createFormData("image", imageFile.getName(),
                    RequestBody.create(MultipartBody.FORM, imageFile));
        }

        // Make the API call to add the product
        apiService.addProduct(nameBody, priceBody, quantityBody, categoryBody, imagePart).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Product added successfully
                    Product addedProduct = response.body();

                    // Get the current list of products
                    List<Product> currentProducts = productsLiveData.getValue();
                    if (currentProducts != null) {
                        // Add the newly added product to the list
                        currentProducts.add(addedProduct);
                        productsLiveData.setValue(currentProducts); // Update LiveData with new list
                    }

                    Log.d("InventoryViewModel", "Product added successfully: " + addedProduct.getName());
                } else {
                    Log.e("InventoryViewModel", "Failed to add product: " + response.message());
                    errorMessage.setValue("Failed to add product");
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Log.e("InventoryViewModel", "Error adding product: " + t.getMessage());
                errorMessage.setValue("Network failure: " + t.getMessage());
            }
        });
    }
}
