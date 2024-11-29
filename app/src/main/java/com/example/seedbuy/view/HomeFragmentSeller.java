package com.example.seedbuy.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.seedbuy.R;
import com.example.seedbuy.model.Order;
import com.example.seedbuy.viewmodel.OrderViewModel;

import java.util.List;

public class HomeFragmentSeller extends Fragment {

    private OrderViewModel orderViewModel;
    private RecyclerView recyclerView;
    private OrderAdapter orderAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_seller, container, false);

        // Initialize ViewModel
        orderViewModel = new ViewModelProvider(this).get(OrderViewModel.class);

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.recycler_view_orders);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Set up RecyclerView Adapter
        orderAdapter = new OrderAdapter();
        recyclerView.setAdapter(orderAdapter);

        // Observe LiveData from ViewModel for changes to orders
        orderViewModel.getOrders().observe(getViewLifecycleOwner(), orders -> {
            if (orders != null) {
                // If orders are successfully fetched, update the adapter
                orderAdapter.setOrders(orders);
            } else {
                // If there's an issue (null or empty), show an error message
                Toast.makeText(getContext(), "Failed to load orders", Toast.LENGTH_SHORT).show();
            }
        });

        // Trigger the fetch of orders
        orderViewModel.fetchOrders();

        return view;
    }
}
