package com.example.seedbuy.model;



public class PurchaseRequest {

    private String product_id;  // Change to String if the product ID is alphanumeric
    private String customer_name;
    private String shipping_address;
    private String payment_method;

    public PurchaseRequest(String product_id, String customer_name, String shipping_address, String payment_method) {
        this.product_id = product_id;
        this.customer_name = customer_name;
        this.shipping_address = shipping_address;
        this.payment_method = payment_method;
    }

    // Getters and setters
    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getShipping_address() {
        return shipping_address;
    }

    public void setShipping_address(String shipping_address) {
        this.shipping_address = shipping_address;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }
}
