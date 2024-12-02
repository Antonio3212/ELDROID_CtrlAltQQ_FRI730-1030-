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

        productNameTextView = findViewById(R.id.purchase_product_name);
        productPriceTextView = findViewById(R.id.purchase_product_price);
        customerNameEditText = findViewById(R.id.purchase_customer_name);
        shippingAddressEditText = findViewById(R.id.purchase_shipping_address);
        paymentMethodEditText = findViewById(R.id.purchase_payment_method);
        confirmPurchaseButton = findViewById(R.id.purchase_confirm_button);

        selectedProduct = (Product) getIntent().getSerializableExtra("product");

        if (selectedProduct != null) {
            productNameTextView.setText(selectedProduct.getName());
            productPriceTextView.setText("$" + selectedProduct.getPrice());
        }

        purchaseViewModel = new ViewModelProvider(this).get(PurchaseViewModel.class);

        purchaseViewModel.getPurchaseResponse().observe(this, purchaseResponse -> {
            if (purchaseResponse != null) {
                Toast.makeText(PurchaseActivity.this, getString(R.string.toast_purchase_successful, purchaseResponse.getOrder().getId()), Toast.LENGTH_SHORT).show();
                navigateToHomePageBuyer();
            }
        });

        purchaseViewModel.getError().observe(this, error -> {
            Toast.makeText(PurchaseActivity.this, getString(R.string.toast_purchase_failed, error), Toast.LENGTH_SHORT).show();
        });

        confirmPurchaseButton.setOnClickListener(view -> confirmPurchase());
    }

    private void confirmPurchase() {
        String customerName = customerNameEditText.getText().toString();
        String shippingAddress = shippingAddressEditText.getText().toString();
        String paymentMethod = paymentMethodEditText.getText().toString();

        if (selectedProduct != null && !customerName.isEmpty() && !shippingAddress.isEmpty() && !paymentMethod.isEmpty()) {
            PurchaseRequest purchaseRequest = new PurchaseRequest(
                    selectedProduct.getId(),
                    customerName,
                    shippingAddress,
                    paymentMethod
            );

            purchaseViewModel.placeOrder(purchaseRequest);
        } else {
            Toast.makeText(this, R.string.toast_all_fields_required, Toast.LENGTH_SHORT).show();
        }
    }

    private void navigateToHomePageBuyer() {
        Intent intent = new Intent(PurchaseActivity.this, HomePageBuyer.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
