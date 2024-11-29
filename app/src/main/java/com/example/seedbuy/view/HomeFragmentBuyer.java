package com.example.seedbuy.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.seedbuy.R;
import com.example.seedbuy.viewmodel.ProductDisplayViewModel;
import com.example.seedbuy.model.Product;

import java.util.List;

public class HomeFragmentBuyer extends Fragment {

    private ProductDisplayViewModel productDisplayViewModel;
    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private ProgressBar progressBar;
    private TextView errorMessageTextView;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_buyer, container, false);

        // Initialize RecyclerView and set its LayoutManager to GridLayoutManager (2 columns)
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2)); // 2 columns

        // Initialize ProgressBar and Error TextView for UI feedback
        progressBar = view.findViewById(R.id.progressBar);
        errorMessageTextView = view.findViewById(R.id.errorMessageTextView);

        // Initialize ViewModel
        productDisplayViewModel = new ViewModelProvider(this).get(ProductDisplayViewModel.class);

        // Observe the product list from ViewModel
        productDisplayViewModel.getProductList().observe(getViewLifecycleOwner(), new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                if (products != null) {
                    // Check if the product list is not empty
                    if (!products.isEmpty()) {
                        // When products are fetched successfully, set them in the adapter
                        if (productAdapter == null) {
                            productAdapter = new ProductAdapter(products);
                            recyclerView.setAdapter(productAdapter);
                        } else {
                            productAdapter.notifyDataSetChanged();
                        }
                    } else {
                        // Handle the case where there are no products
                        errorMessageTextView.setVisibility(View.VISIBLE);
                        errorMessageTextView.setText("No products available.");
                    }
                    progressBar.setVisibility(View.GONE); // Hide the progress bar once data is loaded
                }
            }
        });

        // Observe the loading state to show/hide the ProgressBar
        productDisplayViewModel.getIsLoading().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                if (isLoading) {
                    progressBar.setVisibility(View.VISIBLE); // Show progress bar while loading
                } else {
                    progressBar.setVisibility(View.GONE); // Hide progress bar when loading is done
                }
            }
        });

        // Observe error message if fetching fails
        productDisplayViewModel.getErrorMessage().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String errorMessage) {
                if (errorMessage != null) {
                    errorMessageTextView.setVisibility(View.VISIBLE); // Show error message
                    errorMessageTextView.setText(errorMessage);
                }
            }
        });

        // Fetch products from the API when the fragment is created
        productDisplayViewModel.fetchProducts();

        return view;
    }
}
