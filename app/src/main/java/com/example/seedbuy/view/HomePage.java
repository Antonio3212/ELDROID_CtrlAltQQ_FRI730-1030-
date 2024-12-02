package com.example.seedbuy.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.seedbuy.R;

public class HomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        Button loginButton = findViewById(R.id.btn1);
        Button signUpButton = findViewById(R.id.btn2);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("HomePage", "Login button clicked!");

                // Start the Login Activity
                Intent loginIntent = new Intent(HomePage.this, Login.class);
                startActivity(loginIntent);
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Log message for debugging
                Log.d("HomePage", "Sign Up button clicked!");

                // Start the ChooseUser Activity
                Intent chooseUserIntent = new Intent(HomePage.this, ChooseUser.class);
                startActivity(chooseUserIntent);
            }
        });
    }
}
