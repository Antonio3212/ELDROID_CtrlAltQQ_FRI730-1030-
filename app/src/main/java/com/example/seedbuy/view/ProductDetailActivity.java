package com.example.seedbuy.view;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.seedbuy.R;
import com.example.seedbuy.model.Product;

public class ProductDetailActivity extends AppCompatActivity {

    private ImageView productImageView;
    private TextView productNameTextView;
    private TextView productPriceTextView;
    private TextView productQuantityTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        productImageView = findViewById(R.id.product_detail_image);
        productNameTextView = findViewById(R.id.product_detail_name);
        productPriceTextView = findViewById(R.id.product_detail_price);
        productQuantityTextView = findViewById(R.id.product_detail_quantity);

        // Get the product object from the Intent
        Product selectedProduct = (Product) getIntent().getSerializableExtra("product");

        if (selectedProduct != null) {
            productNameTextView.setText(selectedProduct.getName());
            productPriceTextView.setText("$" + selectedProduct.getPrice());
            productQuantityTextView.setText("Qty: " + selectedProduct.getQuantity());
            // Load the product image using Glide
            Glide.with(this)
                    .load(selectedProduct.getImageUrl())
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_foreground)
                    .into(productImageView);
        }
    }
}
