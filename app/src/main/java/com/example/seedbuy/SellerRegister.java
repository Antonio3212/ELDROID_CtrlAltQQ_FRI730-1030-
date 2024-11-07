package com.example.seedbuy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SellerRegister extends AppCompatActivity {

    private EditText firstName, lastName, email, password, phone, shopName, address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_register);

        firstName = findViewById(R.id.etfn);
        lastName = findViewById(R.id.etln);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        phone = findViewById(R.id.etphone);
        shopName = findViewById(R.id.etshop);
        address = findViewById(R.id.etaddress);
    }

    public void onRegisterClick(View view) {
        String firstNameText = firstName.getText().toString();
        String lastNameText = lastName.getText().toString();
        String emailText = email.getText().toString();
        String passwordText = password.getText().toString();
        String phoneText = phone.getText().toString();
        String shopNameText = shopName.getText().toString();
        String addressText = address.getText().toString();


        RegistrationRequest request = new RegistrationRequest(firstNameText, lastNameText, emailText, passwordText, phoneText, shopNameText, addressText);

        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);
        Call<RegistrationResponse> call = apiService.registerSeller(request);

        call.enqueue(new Callback<RegistrationResponse>() {
            @Override
            public void onResponse(Call<RegistrationResponse> call, Response<RegistrationResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    RegistrationResponse registrationResponse = response.body();
                    Toast.makeText(SellerRegister.this, registrationResponse.getMessage(), Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(SellerRegister.this, Login.class);
                    startActivity(intent);

                    finish();
                } else {
                    Toast.makeText(SellerRegister.this, "Registration failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RegistrationResponse> call, Throwable t) {
                Toast.makeText(SellerRegister.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
