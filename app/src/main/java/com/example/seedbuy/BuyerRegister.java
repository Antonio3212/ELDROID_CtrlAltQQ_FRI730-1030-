package com.example.seedbuy;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BuyerRegister extends AppCompatActivity {

    private EditText firstName, lastName, email, password, confirmPassword, mobileNo;
    private Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_register);

        firstName = findViewById(R.id.etfn);
        lastName = findViewById(R.id.etln);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.cpassword);
        mobileNo = findViewById(R.id.etemobileno);


        signUpButton = findViewById(R.id.btn_signup);


        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRegisterClick(view);
            }
        });
    }


    public void onRegisterClick(View view) {

        String firstNameText = firstName.getText().toString().trim();
        String lastNameText = lastName.getText().toString().trim();
        String emailText = email.getText().toString().trim();
        String passwordText = password.getText().toString().trim();
        String confirmPasswordText = confirmPassword.getText().toString().trim();
        String mobileNoText = mobileNo.getText().toString().trim();


        Log.d("BuyerRegister", "First Name: " + firstNameText);
        Log.d("BuyerRegister", "Last Name: " + lastNameText);
        Log.d("BuyerRegister", "Email: " + emailText);
        Log.d("BuyerRegister", "Password: " + passwordText);
        Log.d("BuyerRegister", "Confirm Password: " + confirmPasswordText);
        Log.d("BuyerRegister", "Mobile No: " + mobileNoText);

        if (firstNameText.isEmpty() || lastNameText.isEmpty() || emailText.isEmpty() ||
                passwordText.isEmpty() || confirmPasswordText.isEmpty() || mobileNoText.isEmpty()) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!passwordText.equals(confirmPasswordText)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        BuyerRegistrationRequest request = new BuyerRegistrationRequest(
                firstNameText, lastNameText, emailText, passwordText, mobileNoText
        );

        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);
        Call<RegistrationResponse> call = apiService.registerBuyer(request);

        call.enqueue(new Callback<RegistrationResponse>() {
            @Override
            public void onResponse(Call<RegistrationResponse> call, Response<RegistrationResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    RegistrationResponse registrationResponse = response.body();
                    Toast.makeText(BuyerRegister.this, registrationResponse.getMessage(), Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(BuyerRegister.this, Login.class);
                    startActivity(intent);


                    finish();
                } else {
                    Toast.makeText(BuyerRegister.this, "Registration failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RegistrationResponse> call, Throwable t) {
                Toast.makeText(BuyerRegister.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
