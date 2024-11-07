package com.example.seedbuy;

// MainActivity.java
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {

    private RecyclerView productRecyclerView;
    private ProductAdapter productAdapter;
    private List<Product> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard); // Assuming this is the correct layout file

        // Initialize RecyclerView
        productRecyclerView = findViewById(R.id.productRecyclerView);
        productRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize Product List
        productList = new ArrayList<>();
        productList.add(new Product("Grapes", "250.00", R.drawable.grapes, R.drawable.ic_user_profile));
        productList.add(new Product("Rose", "500.00", R.drawable.rose, R.drawable.ic_user_profile));
        productList.add(new Product("Corns", "150.00", R.drawable.corns, R.drawable.ic_user_profile));
        productList.add(new Product("Lavender", "600.00", R.drawable.lavender, R.drawable.ic_user_profile));
        // Add more products as needed

        // Initialize Adapter and set it to RecyclerView
        productAdapter = new ProductAdapter(productList);
        productRecyclerView.setAdapter(productAdapter);
    }
}

