package com.example.seedbuy.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.example.seedbuy.R;
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

        firstNameEditText = findViewById(R.id.etfn);
        lastNameEditText = findViewById(R.id.etln);
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        confirmPasswordEditText = findViewById(R.id.confirmpassword);
        phoneEditText = findViewById(R.id.etphone);
        shopNameEditText = findViewById(R.id.etshop);
        addressEditText = findViewById(R.id.etaddress);

        registerButton = findViewById(R.id.btnregister);

        sellerRegisterViewModel = new SellerRegisterViewModel(getApplication());

        sellerRegisterViewModel.getRegistrationResponse().observe(this, new Observer<RegistrationResponse>() {
            @Override
            public void onChanged(RegistrationResponse response) {
                if ("success".equals(response.getStatus())) {
                    Toast.makeText(SellerRegister.this, R.string.toast_registration_success, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SellerRegister.this, Login.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(SellerRegister.this, getString(R.string.toast_registration_failed, response.getMessage()), Toast.LENGTH_SHORT).show();
                }
            }
        });

        sellerRegisterViewModel.getErrorMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String message) {
                Toast.makeText(SellerRegister.this, message, Toast.LENGTH_SHORT).show();
            }
        });

        registerButton.setOnClickListener(v -> onRegisterClick());
    }

    private void onRegisterClick() {
        String firstName = firstNameEditText.getText().toString().trim();
        String lastName = lastNameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();
        String phone = phoneEditText.getText().toString().trim();
        String shopName = shopNameEditText.getText().toString().trim();
        String address = addressEditText.getText().toString().trim();

        if (TextUtils.isEmpty(firstName) || TextUtils.isEmpty(lastName) || TextUtils.isEmpty(email) ||
                TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword) || TextUtils.isEmpty(phone) ||
                TextUtils.isEmpty(shopName) || TextUtils.isEmpty(address)) {
            Toast.makeText(this, R.string.toast_fill_all_fields, Toast.LENGTH_SHORT).show();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, R.string.toast_invalid_email, Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, R.string.toast_passwords_do_not_match, Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 6) {
            Toast.makeText(this, R.string.toast_password_length, Toast.LENGTH_SHORT).show();
            return;
        }

        SellerRegistrationRequest request = new SellerRegistrationRequest(firstName, lastName, email, password, confirmPassword, phone, shopName, address);

        sellerRegisterViewModel.registerSeller(request);
    }
}
