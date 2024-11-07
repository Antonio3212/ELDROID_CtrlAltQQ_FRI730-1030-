package com.example.seedbuy;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class RateAppActivity extends AppCompatActivity {

    // Declare star ImageViews and the submit button
    private ImageView star1, star2, star3, star4, star5;
    private Button submitButton;
    private int selectedRating = 0; // Track the selected star rating

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_our_app);

        // Initialize the views
        star1 = findViewById(R.id.star1);
        star2 = findViewById(R.id.star2);
        star3 = findViewById(R.id.star3);
        star4 = findViewById(R.id.star4);
        star5 = findViewById(R.id.star5);
        submitButton = findViewById(R.id.submitButton);

        // Set click listeners for each star
        setStarClickListener(star1, 1);
        setStarClickListener(star2, 2);
        setStarClickListener(star3, 3);
        setStarClickListener(star4, 4);
        setStarClickListener(star5, 5);

        // Set submit button action
        submitButton.setOnClickListener(v -> submitRating());
    }

    // Set click listener to each star
    private void setStarClickListener(ImageView star, int rating) {
        star.setOnClickListener(v -> {
            selectedRating = rating;  // Update selected rating
            updateStars();  // Update star visuals
        });
    }

    // Update the star images based on selected rating
    private void updateStars() {
        // Update each star to be either filled or empty based on rating
        star1.setImageResource(selectedRating >= 1 ? R.drawable.ic_star_filled : R.drawable.ic_star_empty);
        star2.setImageResource(selectedRating >= 2 ? R.drawable.ic_star_filled : R.drawable.ic_star_empty);
        star3.setImageResource(selectedRating >= 3 ? R.drawable.ic_star_filled : R.drawable.ic_star_empty);
        star4.setImageResource(selectedRating >= 4 ? R.drawable.ic_star_filled : R.drawable.ic_star_empty);
        star5.setImageResource(selectedRating >= 5 ? R.drawable.ic_star_filled : R.drawable.ic_star_empty);
    }

    // Submit the rating (this can be sending data to a server or saving locally)
    private void submitRating() {
        if (selectedRating == 0) {
            // Inform the user to select a rating
            // You can show a Toast or a Snackbar to indicate the issue
            return;
        }
        // Submit the rating (you can handle this as you see fit, e.g., save it or send it to a server)
    }
}

