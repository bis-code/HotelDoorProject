package com.project.HotelDoor.data;

import java.util.ArrayList;

public class User {
    private String userId;
    private String username;
    private String password;
    private String email;
    private ArrayList<String> comments;
    private ArrayList<String> reviews;
    private String fullName;
    private String streetAddress;
    private int numberAddress;

    public User(String userId, String username, String password) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        email = null;
        comments = new ArrayList<>();
        reviews = new ArrayList<>();
        fullName = null;
        streetAddress = null;
        numberAddress = -1;
    }

    public User(String userId, String username, String password, String email, ArrayList<String> comments, ArrayList<String> reviews, String fullName, String streetAddress, int numberAddress) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.comments = comments;
        this.reviews = reviews;
        this.fullName = fullName;
        this.streetAddress = streetAddress;
        this.numberAddress = numberAddress;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<String> getComments() {
        return comments;
    }

    public void setComments(ArrayList<String> comments) {
        this.comments = comments;
    }

    public ArrayList<String> getReviews() {
        return reviews;
    }

    public void setReviews(ArrayList<String> reviews) {
        this.reviews = reviews;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public int getNumberAddress() {
        return numberAddress;
    }

    public void setNumberAddress(int numberAddress) {
        this.numberAddress = numberAddress;
    }
}
