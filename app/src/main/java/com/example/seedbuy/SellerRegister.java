package com.example.seedbuy;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.example.seedbuy.model.SellerRegistrationRequest;
import com.example.seedbuy.model.RegistrationResponse;
import com.example.seedbuy.viewmodel.SellerRegisterViewModel;

public class SellerRegister extends AppCompatActivity {

    private EditText firstNameEditText, lastNameEditText, emailEditText, passwordEditText, confirmPasswordEditText,
            phoneEditText, shopNameEditText, addressEditText;
    private Button registerButton;

    private SellerRegisterViewModel sellerRegisterViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_register);

        // Initialize EditTexts and Button
        firstNameEditText = findViewById(R.id.etfn);
        lastNameEditText = findViewById(R.id.etln);
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        confirmPasswordEditText = findViewById(R.id.confirmpassword);
        phoneEditText = findViewById(R.id.etphone);
        shopNameEditText = findViewById(R.id.etshop);
        addressEditText = findViewById(R.id.etaddress);

        registerButton = findViewById(R.id.btnregister);

        // Initialize ViewModel
        sellerRegisterViewModel = new SellerRegisterViewModel(getApplication());

        // Observe registration response from ViewModel
        sellerRegisterViewModel.getRegistrationResponse().observe(this, new Observer<RegistrationResponse>() {
            @Override
            public void onChanged(RegistrationResponse response) {
                if ("success".equals(response.getStatus())) {
                    Toast.makeText(SellerRegister.this, "Registration successful", Toast.LENGTH_SHORT).show();
                    // Redirect to Login screen after successful registration
                    Intent intent = new Intent(SellerRegister.this, Login.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(SellerRegister.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Observe error messages
        sellerRegisterViewModel.getErrorMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String message) {
                Toast.makeText(SellerRegister.this, message, Toast.LENGTH_SHORT).show();
            }
        });

        // Set the Register button click listener
        registerButton.setOnClickListener(v -> onRegisterClick());
    }

    private void onRegisterClick() {
        // Capture input data from EditText fields
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

        // Create the SellerRegistrationRequest
        SellerRegistrationRequest request = new SellerRegistrationRequest(firstName, lastName, email, password, confirmPassword, phone, shopName, address);

        // Call ViewModel to initiate the registration API call
        sellerRegisterViewModel.registerSeller(request);
    }
}
