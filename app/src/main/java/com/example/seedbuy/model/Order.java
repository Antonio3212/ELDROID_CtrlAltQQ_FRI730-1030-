package com.example.seedbuy.model;

public class Order {
    private int id;
    private String customer_name;
    private String shipping_address;
    private String payment_method;
    private double total_price;
    private Product product;

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getCustomerName() { return customer_name; }
    public void setCustomerName(String customer_name) { this.customer_name = customer_name; }

    public String getShippingAddress() { return shipping_address; }
    public void setShippingAddress(String shipping_address) { this.shipping_address = shipping_address; }

    public String getPaymentMethod() { return payment_method; }
    public void setPaymentMethod(String payment_method) { this.payment_method = payment_method; }

    public double getTotalPrice() { return total_price; }
    public void setTotalPrice(double total_price) { this.total_price = total_price; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }

    public static class Product {
        private int id;
        private String name;
        private double price;

        // Getters and setters
        public int getId() { return id; }
        public void setId(int id) { this.id = id; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public double getPrice() { return price; }
        public void setPrice(double price) { this.price = price; }
    }
}
