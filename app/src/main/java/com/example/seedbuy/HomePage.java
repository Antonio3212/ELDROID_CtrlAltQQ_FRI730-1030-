package com.example.seedbuy;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class HomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page); // Ensure this matches your layout filename

        // Find the buttons by their IDs
        Button loginButton = findViewById(R.id.btn1);
        Button signUpButton = findViewById(R.id.btn2);

        // Set OnClickListener for the Login button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Log message for debugging
                Log.d("HomePage", "Login button clicked!");

                // Start the Login Activity
                Intent loginIntent = new Intent(HomePage.this, Login.class);
                startActivity(loginIntent);
            }
        });

        // Set OnClickListener for the Sign Up button
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
