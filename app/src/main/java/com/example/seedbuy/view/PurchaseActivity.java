package com.example.seedbuy.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.seedbuy.R;
import com.example.seedbuy.model.Product;
import com.example.seedbuy.model.PurchaseRequest;
import com.example.seedbuy.viewmodel.PurchaseViewModel;

public class PurchaseActivity extends AppCompatActivity {

    private TextView productNameTextView;
    private TextView productPriceTextView;
    private EditText customerNameEditText;
    private EditText shippingAddressEditText;
    private EditText paymentMethodEditText;
    private Button confirmPurchaseButton;

    private PurchaseViewModel purchaseViewModel;
    private Product selectedProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);

        // Initialize views
        productNameTextView = findViewById(R.id.purchase_product_name);
        productPriceTextView = findViewById(R.id.purchase_product_price);
        customerNameEditText = findViewById(R.id.purchase_customer_name);
        shippingAddressEditText = findViewById(R.id.purchase_shipping_address);
        paymentMethodEditText = findViewById(R.id.purchase_payment_method);
        confirmPurchaseButton = findViewById(R.id.purchase_confirm_button);

        // Get the product object from the Intent
        selectedProduct = (Product) getIntent().getSerializableExtra("product");

        if (selectedProduct != null) {
            productNameTextView.setText(selectedProduct.getName());
            productPriceTextView.setText("$" + selectedProduct.getPrice());
        }

        // Initialize the ViewModel
        purchaseViewModel = new ViewModelProvider(this).get(PurchaseViewModel.class);

        // Observe LiveData for purchase response
        purchaseViewModel.getPurchaseResponse().observe(this, purchaseResponse -> {
            // Handle success response (order placed)
            if (purchaseResponse != null) {
                Toast.makeText(PurchaseActivity.this, "Purchase Successful! Order ID: " + purchaseResponse.getOrder().getId(), Toast.LENGTH_SHORT).show();

                // After successful purchase, navigate to HomePageBuyer
                navigateToHomePageBuyer();
            }
        });

        // Observe LiveData for errors
        purchaseViewModel.getError().observe(this, error -> {
            // Handle error response
            Toast.makeText(PurchaseActivity.this, "Purchase Failed: " + error, Toast.LENGTH_SHORT).show();
        });

        // Confirm purchase button listener
        confirmPurchaseButton.setOnClickListener(view -> confirmPurchase());
    }

    private void confirmPurchase() {
        String customerName = customerNameEditText.getText().toString();
        String shippingAddress = shippingAddressEditText.getText().toString();
        String paymentMethod = paymentMethodEditText.getText().toString();

        if (selectedProduct != null && !customerName.isEmpty() && !shippingAddress.isEmpty() && !paymentMethod.isEmpty()) {
            // Prepare the data to send
            PurchaseRequest purchaseRequest = new PurchaseRequest(
                    selectedProduct.getId(),
                    customerName,
                    shippingAddress,
                    paymentMethod
            );

            // Call ViewModel to place the order
            purchaseViewModel.placeOrder(purchaseRequest);
        } else {
            // Show error message if fields are empty
            Toast.makeText(this, "All fields are required!", Toast.LENGTH_SHORT).show();
        }
    }

    private void navigateToHomePageBuyer() {
        // Create an Intent to navigate to the HomePageBuyer activity
        Intent intent = new Intent(PurchaseActivity.this, HomePageBuyer.class);
        // Optionally, you can add flags or data to the Intent
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);  // Clears the stack
        startActivity(intent);
        finish(); // Optional: finish current activity so the user can't go back to purchase page
    }
}
