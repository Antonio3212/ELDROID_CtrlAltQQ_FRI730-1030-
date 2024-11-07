package com.example.seedbuy;

// User.java
public class User {
    private String id;
    private String name;

    private int profileImageResId;
    public User(int id, String name, int profileImageResId) {
        this.id = String.valueOf(id);
        this.name = name;
        this.profileImageResId = profileImageResId;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    public int getProfileImageResId() {
        return profileImageResId;
    }

    @Override
    public String toString() {
        return name; // This will display the user's name in the ListView
    }
}
