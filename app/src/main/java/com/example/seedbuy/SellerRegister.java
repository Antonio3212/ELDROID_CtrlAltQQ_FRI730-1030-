package com.example.seedbuy;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SellerRegister extends AppCompatActivity {

    private EditText firstNameEditText, lastNameEditText, emailEditText, passwordEditText, confirmPasswordEditText,
            phoneEditText, shopNameEditText, addressEditText;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_register);

        // Initialize the EditText fields
        firstNameEditText = findViewById(R.id.etfn);
        lastNameEditText = findViewById(R.id.etln);
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        confirmPasswordEditText = findViewById(R.id.confirmpassword);
        phoneEditText = findViewById(R.id.etphone);
        shopNameEditText = findViewById(R.id.etshop);
        addressEditText = findViewById(R.id.etaddress);

        // Initialize the Register button
        registerButton = findViewById(R.id.btnregister);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRegisterClick(v);
            }
        });
    }

    public void onRegisterClick(View view) {
        // Capture input data from the EditTexts
        String firstName = firstNameEditText.getText().toString().trim();
        String lastName = lastNameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();
        String phone = phoneEditText.getText().toString().trim();
        String shopName = shopNameEditText.getText().toString().trim();
        String address = addressEditText.getText().toString().trim();

        // Validate inputs
        if (TextUtils.isEmpty(firstName) || TextUtils.isEmpty(lastName) || TextUtils.isEmpty(email) ||
                TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword) || TextUtils.isEmpty(phone) ||
                TextUtils.isEmpty(shopName) || TextUtils.isEmpty(address)) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 6) {
            Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create the SellerRegistrationRequest object with password_confirmation
        SellerRegistrationRequest request = new SellerRegistrationRequest(
                firstName, lastName, email, password, confirmPassword, phone, shopName, address);

        // Log the request data for debugging purposes
        Log.d("SellerRegister", "Request data: " + request.toString());

        // Create API service instance
        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);

        // Call the API for registration
        Call<RegistrationResponse> call = apiService.registerSeller(request);

        // Execute the request asynchronously
        call.enqueue(new Callback<RegistrationResponse>() {
            @Override
            public void onResponse(Call<RegistrationResponse> call, Response<RegistrationResponse> response) {
                if (response.isSuccessful()) {
                    // Log the response body for debugging
                    RegistrationResponse registrationResponse = response.body();
                    if (registrationResponse != null) {
                        Log.d("SellerRegister", "Success response: " + registrationResponse.getMessage());
                        if ("success".equals(registrationResponse.getStatus())) {
                            Toast.makeText(SellerRegister.this, "Registration successful", Toast.LENGTH_SHORT).show();

                            // Redirect to Login screen after successful registration
                            Intent intent = new Intent(SellerRegister.this, Login.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // Server responded with an error message
                            Toast.makeText(SellerRegister.this, registrationResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Null response body
                        Log.e("SellerRegister", "Null response body");
                        Toast.makeText(SellerRegister.this, "Registration failed, null response body", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Log response code and message for debugging
                    Log.e("SellerRegister", "Response failed: " + response.code() + ", " + response.message());
                    Toast.makeText(SellerRegister.this, "Registration failed. Please try again.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RegistrationResponse> call, Throwable t) {
                // Log failure details
                Log.e("SellerRegister", "Network failure: " + t.getMessage());
                Toast.makeText(SellerRegister.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });
    }
}
