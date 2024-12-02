package com.example.seedbuy.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2)); // 2 columns

        progressBar = view.findViewById(R.id.progressBar);
        errorMessageTextView = view.findViewById(R.id.errorMessageTextView);

        productDisplayViewModel = new ViewModelProvider(this).get(ProductDisplayViewModel.class);

        productDisplayViewModel.getProductList().observe(getViewLifecycleOwner(), new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                if (products != null) {
                    if (!products.isEmpty()) {
                        if (productAdapter == null) {
                            productAdapter = new ProductAdapter(products, getContext());
                            recyclerView.setAdapter(productAdapter);
                        } else {
                            productAdapter.notifyDataSetChanged();
                        }
                    } else {
                        errorMessageTextView.setVisibility(View.VISIBLE);
                        errorMessageTextView.setText(R.string.toast_no_products_available);
                        Toast.makeText(getContext(), R.string.toast_no_products_available, Toast.LENGTH_SHORT).show();
                    }
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

        productDisplayViewModel.getIsLoading().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                if (isLoading) {
                    progressBar.setVisibility(View.VISIBLE);
                    Toast.makeText(getContext(), R.string.toast_loading_products, Toast.LENGTH_SHORT).show();
                } else {
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

        productDisplayViewModel.getErrorMessage().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String errorMessage) {
                if (errorMessage != null) {
                    errorMessageTextView.setVisibility(View.VISIBLE);
                    errorMessageTextView.setText(errorMessage);
                    Toast.makeText(getContext(), R.string.toast_error_fetching_products, Toast.LENGTH_SHORT).show();
                }
            }
        });

        productDisplayViewModel.fetchProducts();

        return view;
    }
}
