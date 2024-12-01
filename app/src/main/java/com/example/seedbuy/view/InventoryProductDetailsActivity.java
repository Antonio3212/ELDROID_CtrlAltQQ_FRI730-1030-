package com.example.seedbuy.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.seedbuy.R;
import com.example.seedbuy.model.Product;
import com.example.seedbuy.viewmodel.InventoryViewModel;

import java.io.File;

public class InventoryProductDetailsActivity extends AppCompatActivity {

    private ImageView productImage;
    private EditText productName, productPrice, productQuantity, productCategory;
    private Button btnUpdateProduct, btnDeleteProduct;
    private InventoryViewModel inventoryViewModel;
    private Product product;
    private File newImageFile;  // Store new image file if selected
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_product_details);

        // Initialize views
        productImage = findViewById(R.id.product_image);
        productName = findViewById(R.id.product_name);
        productPrice = findViewById(R.id.product_price);
        productQuantity = findViewById(R.id.product_quantity);
        productCategory = findViewById(R.id.product_category);
        btnUpdateProduct = findViewById(R.id.btn_update_product);
        btnDeleteProduct = findViewById(R.id.btn_delete_product);
        progressBar = findViewById(R.id.progress_bar);

        // Initialize ViewModel
        inventoryViewModel = new ViewModelProvider(this).get(InventoryViewModel.class);

        // Get the Product object from the Intent
        Intent intent = getIntent();
        product = (Product) intent.getSerializableExtra("product");

        if (product != null) {
            populateProductDetails(product);  // Populate UI with product details
        }

        // Observe error message
        inventoryViewModel.getErrorMessage().observe(this, errorMessage -> {
            if (errorMessage != null && !errorMessage.isEmpty()) {
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });

        // Observe loading status to toggle progress bar visibility
        inventoryViewModel.getIsLoading().observe(this, isLoading -> {
            if (isLoading) {
                progressBar.setVisibility(View.VISIBLE);
            } else {
                progressBar.setVisibility(View.GONE);
            }
        });

        // Update product when "Update" button is clicked
        btnUpdateProduct.setOnClickListener(v -> updateProduct());

        // Delete product when "Delete" button is clicked
        btnDeleteProduct.setOnClickListener(v -> deleteProduct());
    }

    private void populateProductDetails(Product product) {
        // Set product details into EditText fields
        productName.setText(product.getName());
        productPrice.setText(product.getPrice());
        productQuantity.setText(product.getQuantity());
        productCategory.setText(product.getCategory());

        // Load product image using Glide
        Glide.with(this)
                .load(product.getImageUrl())
                .into(productImage);
    }

    private void updateProduct() {
        String updatedName = productName.getText().toString();
        String updatedPrice = productPrice.getText().toString();
        String updatedQuantity = productQuantity.getText().toString();
        String updatedCategory = productCategory.getText().toString();

        File imageFile = newImageFile != null ? newImageFile : null;

        // Start updating the product via ViewModel
        inventoryViewModel.updateProduct(product.getId(), updatedName, updatedPrice, updatedQuantity, updatedCategory, imageFile);

        // Observe the result
        inventoryViewModel.getIsLoading().observe(this, isLoading -> {
            if (isLoading) {
                progressBar.setVisibility(View.VISIBLE);
            } else {
                progressBar.setVisibility(View.GONE);
            }
        });

        inventoryViewModel.getErrorMessage().observe(this, errorMessage -> {
            if (errorMessage != null && !errorMessage.isEmpty()) {
                Toast.makeText(this, "Error: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });

        // Observe when the update is successful
        inventoryViewModel.getIsLoading().observe(this, isLoading -> {
            if (!isLoading) {
                Toast.makeText(this, "Product updated successfully", Toast.LENGTH_SHORT).show();
                finish();  // Close the activity after success
            }
        });
    }


    private void deleteProduct() {
        // Call ViewModel to delete the product
        inventoryViewModel.deleteProduct(product.getId());

        // Show success message
        Toast.makeText(this, "Product deleted successfully", Toast.LENGTH_SHORT).show();

        finish();
    }

    public void setNewImageFile(File imageFile) {
        this.newImageFile = imageFile;
        Glide.with(this)
                .load(imageFile)
                .into(productImage);
    }
}
