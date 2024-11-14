package com.example.seedbuy;

public class SellerRegistrationRequest {

    private String first_name;
    private String last_name;
    private String email;
    private String password;
    private String password_confirmation;  // Backend expects this for password confirmation
    private String phone;
    private String shop_name;
    private String address;

    // Constructor with password_confirmation
    public SellerRegistrationRequest(String first_name, String last_name, String email, String password,
                                     String password_confirmation, String phone, String shop_name, String address) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
        this.password_confirmation = password_confirmation;  // Include password confirmation
        this.phone = phone;
        this.shop_name = shop_name;
        this.address = address;
    }

    // Getters and setters
    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword_confirmation() {
        return password_confirmation;
    }

    public void setPassword_confirmation(String password_confirmation) {
        this.password_confirmation = password_confirmation;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "SellerRegistrationRequest{" +
                "first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", password_confirmation='" + password_confirmation + '\'' +
                ", phone='" + phone + '\'' +
                ", shop_name='" + shop_name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
