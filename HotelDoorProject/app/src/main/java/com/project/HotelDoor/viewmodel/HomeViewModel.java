package com.project.HotelDoor.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.project.HotelDoor.data.DAO.UserDAO;
import com.project.HotelDoor.data.Hotel;
import com.project.HotelDoor.data.Review;
import com.project.HotelDoor.data.ReviewRepository;
import com.project.HotelDoor.data.User;
import com.project.HotelDoor.data.UserRepository;

import java.util.ArrayList;

public class HomeViewModel extends AndroidViewModel {
    private UserRepository userRepository;
    private ReviewRepository reviewRepository;

    public HomeViewModel(Application app) {
        super(app);
        userRepository = UserRepository.getInstance(app);
        reviewRepository = ReviewRepository.getInstance(app);
    }

    public User getUser(String uid) {
        return userRepository.getUserModal(uid);
    }

//    public ArrayList<Review> getReviews(ArrayList<Hotel> hotel) {
//        return reviewRepository.getReviews(hotel);
//    }

    public void getHotels()
    {
        reviewRepository.getHotels();
    }

    public void getReviews()
    {
        reviewRepository.getReviews();
    }

    public MutableLiveData<ArrayList<Hotel>> getHotelsLiveData()
    {
        return reviewRepository.getHotelsLiveData();
    }

    public MutableLiveData<ArrayList<Review>> getReviewsLiveData()
    {
        return reviewRepository.getReviewsLiveData();
    }

//    public void loadReviewData(Context context, Application app, RecyclerView revFeed)
//    {
//        reviewRepository.loadReviewData(context, app,revFeed);
//    }
}