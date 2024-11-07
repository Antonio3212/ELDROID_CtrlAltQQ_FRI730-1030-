package com.example.seedbuy;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ChooseUser extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_choose_user);
        Button seller = findViewById(R.id.seller);
        Button buyer = findViewById(R.id.buyer);

        seller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("HomePage", "Login button clicked!");

                Intent loginIntent = new Intent(ChooseUser.this, SellerRegister.class);
                startActivity(loginIntent);
            }
        });

        buyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Log message for debugging
                Log.d("HomePage", "Sign Up button clicked!");

                // Start the ChooseUser Activity
                Intent chooseUserIntent = new Intent(ChooseUser.this, BuyerRegister.class);
                startActivity(chooseUserIntent);
            }
        });
    }
}