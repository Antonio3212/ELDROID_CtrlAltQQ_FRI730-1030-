package com.example.seedbuy;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class NotificationsActivity extends AppCompatActivity {

    private RecyclerView notificationsRecyclerView;
    private NotificationAdapter notificationAdapter;
    private List<Notification> notificationList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        // Initialize the RecyclerView
        notificationsRecyclerView = findViewById(R.id.notificationsRecyclerView);
        notificationsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize sample notifications
        notificationList = new ArrayList<>();
        populateSampleNotifications();

        // Set up the adapter
        notificationAdapter = new NotificationAdapter(notificationList);
        notificationsRecyclerView.setAdapter(notificationAdapter);
    }

    private void populateSampleNotifications() {
        // Sample data for testing
        notificationList.add(new Notification(
                "SeedBuy: Your Plant Marketplace Simplified",
                "Easily buy and sell seedlings and plants with SeedBuy. Connect with fellow enthusiasts and nurseries, stay on top of trends, and grow your collection—all in one convenient app.",
                System.currentTimeMillis() - 60000)); // 1 minute ago

        notificationList.add(new Notification(
                "Order Update",
                "Your plant order has been shipped.",
                System.currentTimeMillis() - 3600000)); // 1 hour ago
    }
}
