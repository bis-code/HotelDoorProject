package com.project.HotelDoor.data;

import java.util.ArrayList;

public class Hotel {
    private String address;
    private String  name;
    private ArrayList<Review> reviews;

    public Hotel(String address, String name, ArrayList<Review> reviews) {
        this.address = address;
        this.name = name;
        this.reviews = reviews;
    }

    public Hotel()
    {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Review> getReviews() {
        return reviews;
    }

    public void setReviews(ArrayList<Review> reviews) {
        this.reviews = reviews;
    }
}
