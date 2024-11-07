package com.example.seedbuy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    private EditText email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
    }

    public void onLoginClick(View view) {
        String emailText = email.getText().toString();
        String passwordText = password.getText().toString();

        if (emailText.isEmpty() || passwordText.isEmpty()) {
            Toast.makeText(this, "Please enter both email and password", Toast.LENGTH_SHORT).show();
            return;
        }

        LoginRequest request = new LoginRequest(emailText, passwordText);

        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);
        Call<LoginResponse> call = apiService.login(request);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                Log.d("Login", "Response: " + response.body());

                if (response.isSuccessful() && response.body() != null) {
                    LoginResponse loginResponse = response.body();
                    Toast.makeText(Login.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();

                    String jwtToken = loginResponse.getToken();

                    getSharedPreferences("MyPrefs", MODE_PRIVATE)
                            .edit()
                            .putString("JWT_TOKEN", jwtToken)
                            .apply();

                    Intent intent = new Intent(Login.this, HomePage.class);
                    startActivity(intent);

                    finish();
                } else {
                    Toast.makeText(Login.this, "Login failed: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.e("Login", "Error: " + t.getMessage());
                Toast.makeText(Login.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
