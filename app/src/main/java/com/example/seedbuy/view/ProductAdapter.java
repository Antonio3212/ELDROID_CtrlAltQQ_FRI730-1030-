package com.example.seedbuy.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.seedbuy.R;
import com.example.seedbuy.model.Product;
import com.example.seedbuy.view.ProductDetailActivity;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<Product> productList;
    private Context context;

    // Constructor for the adapter
    public ProductAdapter(List<Product> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        Product product = productList.get(position);

        // Bind product details
        holder.nameTextView.setText(product.getName());
        holder.priceTextView.setText("$" + product.getPrice());
        holder.quantityTextView.setText("" + product.getQuantity());

        // Use getImageUrl() to get the full URL for the product image
        String imageUrl = product.getImageUrl();  // Assuming getImageUrl() method returns the full URL

        // Log the image URL to check if it is correct
        Log.d("ProductAdapter", "Image URL: " + imageUrl);

        // Load the image using Glide
        Glide.with(holder.imageView.getContext())
                .load(imageUrl)
                .placeholder(R.drawable.ic_launcher_background)  // Optional placeholder while image is loading
                .error(R.drawable.ic_launcher_foreground)      // Optional error image if loading fails
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        // Log the error if image loading fails
                        Log.e("GlideError", "Error loading image: " + (e != null ? e.getMessage() : "Unknown"));
                        return false;  // Allow Glide to handle the error
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        // Log message when image is successfully loaded
                        Log.d("Glide", "Image loaded successfully!");
                        return false;  // Continue normal Glide operations
                    }
                })
                .into(holder.imageView);  // Set the image into ImageView

        // Handle item click: Open ProductDetailActivity with product data
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductDetailActivity.class);
            intent.putExtra("product", product); // Pass the Product object to the detail activity
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();  // Return the total number of products
    }

    // ViewHolder class for holding item views
    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView nameTextView;
        TextView priceTextView;
        TextView quantityTextView;

        public ProductViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.product_image);
            nameTextView = itemView.findViewById(R.id.product_name_value);
            priceTextView = itemView.findViewById(R.id.product_price_value);
            quantityTextView = itemView.findViewById(R.id.product_quantity_value);
        }
    }
}
