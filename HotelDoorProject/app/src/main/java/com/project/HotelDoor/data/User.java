package com.project.HotelDoor.data;

import java.util.ArrayList;

public class User {
    private String uid;
    private String userName;
    private String email;
    private int reviews;
    private String fullName;
    private String streetAddress;
    private int numberAddress;
    private String phone;
    private Role role;
    private int likes;

    public User(String uid, String userName) {
        this.uid = uid;
        this.userName = userName;
        email = null;
        reviews = 0;
        fullName = null;
        streetAddress = null;
        numberAddress = -1;
        phone = null;
        role = Role.MEMBER;
        likes = 0;
    }

    public User(Role role, String uid, String userName, String email, String phone, int reviews, String fullName, String streetAddress, int numberAddress, int likes) {
        this.uid = uid;
        this.userName = userName;
        this.phone = phone;
        this.email = email;
        this.reviews = reviews;
        this.fullName = fullName;
        this.streetAddress = streetAddress;
        this.numberAddress = numberAddress;
        this.role = role;
        this.likes = likes;
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

    public void setRole(Role role) {
        this.role = role;
    }

    public Role getRole() {
        return role;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getReviews() {
        return reviews;
    }

    public void setReviews(int reviews) {
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

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
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
