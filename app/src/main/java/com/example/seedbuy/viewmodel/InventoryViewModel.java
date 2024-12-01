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
    private MutableLiveData<Product> productLiveData = new MutableLiveData<>();
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);

    public InventoryViewModel(Application application) {
        super(application);
        apiService = RetrofitClient.getApiService(); // Initialize the ApiService
    }

    // Get products live data
    public LiveData<List<Product>> getProductsLiveData() {
        return productsLiveData;
    }

    // Get a single product live data
    public LiveData<Product> getProductLiveData() {
        return productLiveData;
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
        isLoading.setValue(true);

        Call<ProductResponse> call = apiService.getAllProducts();
        call.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                isLoading.setValue(false);

                if (response.isSuccessful() && response.body() != null) {
                    productsLiveData.setValue(response.body().getProducts());
                } else {
                    errorMessage.setValue("Failed to fetch products: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                isLoading.setValue(false);
                errorMessage.setValue("Network failure: " + t.getMessage());
            }
        });
    }

    public void addProduct(String name, String price, String quantity, String category, File imageFile) {
        RequestBody nameBody = RequestBody.create(MultipartBody.FORM, name);
        RequestBody priceBody = RequestBody.create(MultipartBody.FORM, price);
        RequestBody quantityBody = RequestBody.create(MultipartBody.FORM, quantity);
        RequestBody categoryBody = RequestBody.create(MultipartBody.FORM, category);

        MultipartBody.Part imagePart = null;
        if (imageFile != null) {
            imagePart = MultipartBody.Part.createFormData("image", imageFile.getName(),
                    RequestBody.create(MultipartBody.FORM, imageFile));
        }

        apiService.addProduct(nameBody, priceBody, quantityBody, categoryBody, imagePart).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Product addedProduct = response.body();

                    List<Product> currentProducts = productsLiveData.getValue();
                    if (currentProducts != null) {
                        currentProducts.add(addedProduct);
                        productsLiveData.setValue(currentProducts);
                    }
                } else {
                    errorMessage.setValue("Failed to add product");
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                errorMessage.setValue("Network failure: " + t.getMessage());
            }
        });
    }

    public void updateProduct(String productId, String name, String price, String quantity, String category, File imageFile) {
        // Handle nullable fields by checking for null/empty
        RequestBody nameBody = (name != null && !name.isEmpty()) ? RequestBody.create(MultipartBody.FORM, name) : null;
        RequestBody priceBody = (price != null && !price.isEmpty()) ? RequestBody.create(MultipartBody.FORM, price) : null;
        RequestBody quantityBody = (quantity != null && !quantity.isEmpty()) ? RequestBody.create(MultipartBody.FORM, quantity) : null;
        RequestBody categoryBody = (category != null && !category.isEmpty()) ? RequestBody.create(MultipartBody.FORM, category) : null;

        // Handle image file part (optional)
        MultipartBody.Part imagePart = null;
        if (imageFile != null) {
            imagePart = MultipartBody.Part.createFormData("image", imageFile.getName(),
                    RequestBody.create(MultipartBody.FORM, imageFile));
        }

        // Make the Retrofit API call to update the product
        apiService.updateProduct(productId, nameBody, priceBody, quantityBody, categoryBody, imagePart).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Handle success
                    Product updatedProduct = response.body();
                    List<Product> currentProducts = productsLiveData.getValue();
                    if (currentProducts != null) {
                        for (int i = 0; i < currentProducts.size(); i++) {
                            if (currentProducts.get(i).getId().equals(updatedProduct.getId())) {
                                currentProducts.set(i, updatedProduct);  // Replace the old product with updated one
                                break;
                            }
                        }
                        productsLiveData.setValue(currentProducts);
                    }
                } else {
                    errorMessage.setValue("Failed to update product: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                errorMessage.setValue("Network failure: " + t.getMessage());
            }
        });
    }



    // Delete a product
    public void deleteProduct(String productId) {
        apiService.deleteProduct(productId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    List<Product> currentProducts = productsLiveData.getValue();
                    if (currentProducts != null) {
                        currentProducts.removeIf(product -> product.getId().equals(productId));
                        productsLiveData.setValue(currentProducts);
                    }
                } else {
                    errorMessage.setValue("Failed to delete product");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                errorMessage.setValue("Network failure: " + t.getMessage());
            }
        });
    }
}
