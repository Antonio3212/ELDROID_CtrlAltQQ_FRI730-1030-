package com.example.seedbuy.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.seedbuy.model.LoginRequest;
import com.example.seedbuy.model.LoginResponse;
import com.example.seedbuy.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends AndroidViewModel {

    private MutableLiveData<LoginResponse> loginResponseLiveData = new MutableLiveData<>();
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public LoginViewModel(Application application) {
        super(application);
    }

    public LiveData<LoginResponse> getLoginResponse() {
        return loginResponseLiveData;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void loginUser(String email, String password) {
        LoginRequest loginRequest = new LoginRequest(email, password);

        RetrofitClient.getApiService().login(loginRequest).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    LoginResponse loginResponse = response.body();
                    if (loginResponse != null && "success".equals(loginResponse.getStatus())) {
                        loginResponseLiveData.setValue(loginResponse);
                    } else {
                        errorMessage.setValue("Invalid credentials");
                    }
                } else {
                    errorMessage.setValue("Login failed. Try again.");
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                errorMessage.setValue("Error: " + t.getMessage());
                Log.e("LoginViewModel", "Error: " + t.getMessage());
            }
        });
    }
}
