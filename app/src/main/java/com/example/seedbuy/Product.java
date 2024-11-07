package com.example.seedbuy;

// Product.java
public class Product {
    private String name;
    private String price;
    private int imageResource;
    private int sellerProfileImageResource;

    public Product(String name, String price, int imageResource, int sellerProfileImageResource) {
        this.name = name;
        this.price = price;
        this.imageResource = imageResource;
        this.sellerProfileImageResource = sellerProfileImageResource;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public int getImageResource() {
        return imageResource;
    }

    public int getSellerProfileImageResource() {
        return sellerProfileImageResource;
    }
}
