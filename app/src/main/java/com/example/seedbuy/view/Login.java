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

    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginbtn);

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        loginViewModel.getLoginResponse().observe(this, new Observer<LoginResponse>() {
            @Override
            public void onChanged(LoginResponse loginResponse) {
                if (loginResponse != null && "success".equals(loginResponse.getStatus())) {
                    if ("seller".equals(loginResponse.getUser_type())) {
                        Toast.makeText(Login.this, R.string.toast_seller_logged_in, Toast.LENGTH_SHORT).show();
                        navigateToHomePageSeller();
                    } else if ("buyer".equals(loginResponse.getUser_type())) {
                        Toast.makeText(Login.this, R.string.toast_buyer_logged_in, Toast.LENGTH_SHORT).show();
                        navigateToHomePageBuyer();
                    }
                }
            }
        });

        loginViewModel.getErrorMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String message) {
                if (message != null) {
                    Toast.makeText(Login.this, message, Toast.LENGTH_SHORT).show();
                }
            }
        });

        loginButton.setOnClickListener(v -> {
            String userEmail = email.getText().toString().trim();
            String userPassword = password.getText().toString().trim();

            if (userEmail.isEmpty() || userPassword.isEmpty()) {
                Toast.makeText(Login.this, R.string.toast_fill_in_fields, Toast.LENGTH_SHORT).show();
                return;
            }

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
