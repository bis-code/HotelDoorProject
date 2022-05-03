package com.project.HotelDoor.data;

import java.util.ArrayList;

public class User {
    private String uid;
    private String userName;
    private String email;

    //TODO: change to review object
    private ArrayList<String> reviews;
    private String fullName;
    private String streetAddress;
    private int numberAddress;
    private String phone;

    public User(String uid, String userName) {
        this.uid = uid;
        this.userName = userName;
        email = null;
        reviews = new ArrayList<>();
        fullName = null;
        streetAddress = null;
        numberAddress = -1;
        phone = null;
    }

    public User(String uid, String userName, String email, String phone,ArrayList<String> comments, ArrayList<String> reviews, String fullName, String streetAddress, int numberAddress) {
        this.uid = uid;
        this.userName = userName;
        this.phone = phone;
        this.email = email;
        this.reviews = reviews;
        this.fullName = fullName;
        this.streetAddress = streetAddress;
        this.numberAddress = numberAddress;
    }

    public User()
    {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    @Override
    public String toString() {
        return "User{" +
                "uid='" + uid + '\'' +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", reviews=" + reviews +
                ", fullName='" + fullName + '\'' +
                ", streetAddress='" + streetAddress + '\'' +
                ", numberAddress=" + numberAddress +
                '}';
    }
}
