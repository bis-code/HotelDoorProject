package com.project.HotelDoor.data;

import java.util.ArrayList;
import java.util.List;

public class Review {
    public ArrayList<Comment> comments;
    public String userUID;
    public String descriptions;
    public int rate;

    public Review(String userUID, String descriptions, int rate) {
        this.comments = new ArrayList<>();
        this.userUID = userUID;
        this.descriptions = descriptions;
        this.rate = rate;
    }

    public Review(ArrayList<Comment> comments, String userUID, String descriptions, int rate) {
        this.comments = comments;
        this.userUID = userUID;
        this.descriptions = descriptions;
        this.rate = rate;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    public String getUserUID() {
        return userUID;
    }

    public void setUserUID(String userUID) {
        this.userUID = userUID;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }
}
