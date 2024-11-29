package com.example.seedbuy.model;
import java.io.Serializable;
public class Product implements Serializable {

    private String name;
    private String price;
    private String quantity;
    private String category;
    private String image_path;  // The relative path of the product image

    // Constructor
    public Product(String name, String price, String quantity, String category, String image_path) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
        this.image_path = image_path;
    }

    // Getters and Setters
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
        // Assuming the backend API provides the image path, and the base URL is hardcoded (or comes from a config)
        return "http://10.0.2.2:8000/storage/" + image_path;

    }
}
