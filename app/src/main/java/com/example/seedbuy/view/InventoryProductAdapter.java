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
    private ProductClickListener productClickListener;

    public InventoryProductAdapter(List<Product> productList, Context context, ProductClickListener productClickListener) {
        this.productList = productList;
        this.context = context;
        this.productClickListener = productClickListener;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        Product product = productList.get(position);

        holder.nameTextView.setText(product.getName());
        holder.priceTextView.setText("$" + product.getPrice());
        holder.quantityTextView.setText("Quantity: " + product.getQuantity());

        String imageUrl = product.getImageUrl();

        Glide.with(holder.imageView.getContext())
                .load(imageUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
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

    public void updateProductList(List<Product> newProductList) {
        this.productList = newProductList;
        notifyDataSetChanged();
    }

    public interface ProductClickListener {
        void onProductClick(Product product);
    }
}
