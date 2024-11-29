package com.example.seedbuy.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.seedbuy.R;
import com.example.seedbuy.model.Order;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private List<Order> orders;

    // Setter to update the orders list and notify the adapter of changes
    public void setOrders(List<Order> orders) {
        this.orders = orders;
        notifyDataSetChanged();
    }

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OrderViewHolder holder, int position) {
        Order order = orders.get(position);
        holder.customerName.setText(order.getCustomerName());
        holder.shippingAddress.setText(order.getShippingAddress());
        holder.paymentMethod.setText(order.getPaymentMethod());
        holder.totalPrice.setText(String.format("$%.2f", order.getTotalPrice()));
    }

    @Override
    public int getItemCount() {
        return orders != null ? orders.size() : 0;
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView customerName, shippingAddress, paymentMethod, totalPrice;

        public OrderViewHolder(View itemView) {
            super(itemView);
            customerName = itemView.findViewById(R.id.text_customer_name);
            shippingAddress = itemView.findViewById(R.id.text_shipping_address);
            paymentMethod = itemView.findViewById(R.id.text_payment_method);
            totalPrice = itemView.findViewById(R.id.text_total_price);
        }
    }
}
