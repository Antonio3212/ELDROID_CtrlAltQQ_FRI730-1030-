package com.example.seedbuy.view;

import android.content.Context;
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

import java.util.List;

public class InventoryProductAdapter extends RecyclerView.Adapter<InventoryProductAdapter.ProductViewHolder> {

    private List<Product> productList;
    private Context context;
    private ProductClickListener productClickListener; // Declare ProductClickListener

    // Constructor for the adapter with the click listener
    public InventoryProductAdapter(List<Product> productList, Context context, ProductClickListener productClickListener) {
        this.productList = productList;
        this.context = context;
        this.productClickListener = productClickListener; // Initialize click listener
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        Product product = productList.get(position);

        // Bind product details to the views
        holder.nameTextView.setText(product.getName());
        holder.priceTextView.setText("$" + product.getPrice());
        holder.quantityTextView.setText("Quantity: " + product.getQuantity());

        // Load the product image using Glide
        String imageUrl = product.getImageUrl();  // Assuming getImageUrl() method returns the full URL

        Glide.with(holder.imageView.getContext())
                .load(imageUrl)
                .placeholder(R.drawable.ic_launcher_background)  // Optional placeholder while image is loading
                .error(R.drawable.ic_launcher_foreground)      // Optional error image if loading fails
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        Log.e("GlideError", "Error loading image: " + (e != null ? e.getMessage() : "Unknown"));
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        Log.d("Glide", "Image loaded successfully!");
                        return false;
                    }
                })
                .into(holder.imageView);

        // Handle item click: Trigger the product click listener
        holder.itemView.setOnClickListener(v -> productClickListener.onProductClick(product));
    }

    @Override
    public int getItemCount() {
        return productList != null ? productList.size() : 0;
    }

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

    // Method to update the product list
    public void updateProductList(List<Product> newProductList) {
        this.productList = newProductList;
        notifyDataSetChanged();
    }

    // Define the ProductClickListener interface
    public interface ProductClickListener {
        void onProductClick(Product product);
    }
}
