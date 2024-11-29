// BuyerRegister.java
package com.example.seedbuy.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.seedbuy.R;
import com.example.seedbuy.model.RegistrationRequest;
import com.example.seedbuy.viewmodel.BuyerRegisterViewModel;

public class BuyerRegister extends AppCompatActivity {

    private EditText firstName, lastName, email, password, confirmPassword, mobileNo;
    private Button signUpButton;
    private BuyerRegisterViewModel buyerRegisterViewModel;

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

        // Initialize ViewModel
        buyerRegisterViewModel = new ViewModelProvider(this).get(BuyerRegisterViewModel.class);

        // Observe LiveData for registration success
        buyerRegisterViewModel.getRegistrationResponse().observe(this, registrationResponse -> {
            if (registrationResponse != null) {
                Toast.makeText(BuyerRegister.this, registrationResponse.getMessage(), Toast.LENGTH_SHORT).show();
                // Redirect to login page
                Intent intent = new Intent(BuyerRegister.this, Login.class);
                startActivity(intent);
                finish();
            }
        });

        // Observe LiveData for error messages
        buyerRegisterViewModel.getErrorMessage().observe(this, errorMessage -> {
            if (errorMessage != null) {
                Toast.makeText(BuyerRegister.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });

        signUpButton.setOnClickListener(view -> onRegisterClick());
    }

    public void onRegisterClick() {
        String firstNameText = firstName.getText().toString().trim();
        String lastNameText = lastName.getText().toString().trim();
        String emailText = email.getText().toString().trim();
        String passwordText = password.getText().toString().trim();
        String confirmPasswordText = confirmPassword.getText().toString().trim();
        String mobileNoText = mobileNo.getText().toString().trim();

        if (firstNameText.isEmpty() || lastNameText.isEmpty() || emailText.isEmpty() ||
                passwordText.isEmpty() || confirmPasswordText.isEmpty() || mobileNoText.isEmpty()) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!passwordText.equals(confirmPasswordText)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create the registration request
        RegistrationRequest request = new RegistrationRequest(
                firstNameText, lastNameText, emailText, passwordText, confirmPasswordText, mobileNoText
        );

        // Call the registerBuyer method in ViewModel
        buyerRegisterViewModel.registerBuyer(request);
    }
}
