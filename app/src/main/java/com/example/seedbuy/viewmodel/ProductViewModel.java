package com.example.seedbuy.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.seedbuy.model.Product;
import com.example.seedbuy.network.ApiService;
import com.example.seedbuy.network.RetrofitClient;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductViewModel extends ViewModel {
    private MutableLiveData<Product> productLiveData = new MutableLiveData<>();
    private ApiService apiService;

    public ProductViewModel() {
        apiService = RetrofitClient.getInstance().create(ApiService.class);
    }

    public LiveData<Product> getProductLiveData() {
        return productLiveData;
    }

    public void addProduct(String name, String price, String quantity, String category, File imageFile) {
        RequestBody requestBodyName = RequestBody.create(MediaType.parse("text/plain"), name);
        RequestBody requestBodyPrice = RequestBody.create(MediaType.parse("text/plain"), price);
        RequestBody requestBodyQuantity = RequestBody.create(MediaType.parse("text/plain"), quantity);
        RequestBody requestBodyCategory = RequestBody.create(MediaType.parse("text/plain"), category);

        MultipartBody.Part imagePart = null;
        if (imageFile != null) {
            RequestBody requestBodyImage = RequestBody.create(MediaType.parse("image/*"), imageFile);
            imagePart = MultipartBody.Part.createFormData("image", imageFile.getName(), requestBodyImage);
        }

        apiService.addProduct(requestBodyName, requestBodyPrice, requestBodyQuantity, requestBodyCategory, imagePart)
                .enqueue(new Callback<Product>() {
                    @Override
                    public void onResponse(Call<Product> call, Response<Product> response) {
                        if (response.isSuccessful()) {
                            productLiveData.setValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<Product> call, Throwable t) {
                        // Handle failure
                    }
                });
    }
}

