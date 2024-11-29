package com.example.seedbuy.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.seedbuy.R;
import com.example.seedbuy.viewmodel.LoginViewModel;
import com.example.seedbuy.model.LoginResponse;

public class Login extends AppCompatActivity {

    private EditText email, password;
    private Button loginButton;

    // ViewModel initialization
    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize views
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginbtn);

        // Initialize ViewModel using ViewModelProvider
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        // Observe login response
        loginViewModel.getLoginResponse().observe(this, new Observer<LoginResponse>() {
            @Override
            public void onChanged(LoginResponse loginResponse) {
                if (loginResponse != null && "success".equals(loginResponse.getStatus())) {
                    if ("seller".equals(loginResponse.getUser_type())) {
                        Toast.makeText(Login.this, "Seller logged in", Toast.LENGTH_SHORT).show();
                        // Navigate to HomePageSeller activity when a seller logs in
                        navigateToHomePageSeller();
                    } else if ("buyer".equals(loginResponse.getUser_type())) {
                        Toast.makeText(Login.this, "Buyer logged in", Toast.LENGTH_SHORT).show();
                        // Navigate to HomePageBuyer activity when a buyer logs in
                        navigateToHomePageBuyer();
                    }
                }
            }
        });

        // Observe error messages
        loginViewModel.getErrorMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String message) {
                if (message != null) {
                    Toast.makeText(Login.this, message, Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Set up the login button click listener
        loginButton.setOnClickListener(v -> {
            String userEmail = email.getText().toString().trim();
            String userPassword = password.getText().toString().trim();

            // Validate input
            if (userEmail.isEmpty() || userPassword.isEmpty()) {
                Toast.makeText(Login.this, "Please fill in both fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Call the login method in ViewModel
            loginViewModel.loginUser(userEmail, userPassword);
        });
    }

    private void navigateToHomePageBuyer() {
        Intent intent = new Intent(Login.this, HomePageBuyer.class);
        startActivity(intent);
        finish();
    }

    private void navigateToHomePageSeller() {
        Intent intent = new Intent(Login.this, HomePageSeller.class);
        startActivity(intent);
        finish();
    }
}
