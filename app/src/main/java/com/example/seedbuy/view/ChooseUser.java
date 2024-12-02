package com.example.seedbuy.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.seedbuy.R;

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

                // Show Toast message when the Seller button is clicked
                Toast.makeText(ChooseUser.this, R.string.toast_seller_login_clicked, Toast.LENGTH_SHORT).show();

                Intent loginIntent = new Intent(ChooseUser.this, SellerRegister.class);
                startActivity(loginIntent);
            }
        });

        buyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Log message for debugging
                Log.d("HomePage", "Sign Up button clicked!");

                // Show Toast message when the Buyer button is clicked
                Toast.makeText(ChooseUser.this, R.string.toast_buyer_signup_clicked, Toast.LENGTH_SHORT).show();

                // Start the ChooseUser Activity
                Intent chooseUserIntent = new Intent(ChooseUser.this, BuyerRegister.class);
                startActivity(chooseUserIntent);
            }
        });
    }
}
