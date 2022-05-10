package com.project.HotelDoor.data;

import java.util.ArrayList;
import java.util.List;

public class Review {
//    public ArrayList<Comment> comments;
    public String userUID;
    public String description;
    public float rate;
    public int likes;
    private String hotelName;

    public Review(String hotelName, String userUID, String description, float rate, int likes) {
//        this.comments = new ArrayList<>();
        this.userUID = userUID;
        this.description = description;
        this.rate = rate;
        this.likes = likes;
        this.hotelName = hotelName;
    }

    public Review() {
        this.userUID = null;
        this.description = null;
        this.rate = -1;
        this.likes = 0;
        this.hotelName = null;
    }



//    public ArrayList<Comment> getComments() {
//        return comments;
//    }
//
//    public void setComments(ArrayList<Comment> comments) {
//        this.comments = comments;
//    }

    public String getUserUID() {
        return userUID;
    }

    public void setUserUID(String userUID) {
        this.userUID = userUID;
    }

    public int getLikes() {
        return likes;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getDescription() {
        return description;
    }

    public void setDescriptions(String description) {
        this.description = description;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }
}
