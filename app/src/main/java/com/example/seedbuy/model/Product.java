package com.example.seedbuy.model;

import java.io.Serializable;

public class Product implements Serializable {

    private String id;        // Change this to String, if product ID can be alphanumeric
    private String name;
    private String price;
    private String quantity;
    private String category;
    private String image_path;

    // Constructor
    public Product(String id, String name, String price, String quantity, String category, String image_path) {
        this.id = id;         // Initialize the id as String
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
        this.image_path = image_path;
    }

    // Getters and Setters
    public String getId() {
        return id;            // Return the product ID as a String
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImagePath() {
        return image_path;
    }

    public void setImagePath(String image_path) {
        this.image_path = image_path;
    }

    // Method to get the full URL for the image
    public String getImageUrl() {
        return "http://10.0.2.2:8000/storage/" + image_path;
    }
}
