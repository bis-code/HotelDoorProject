package com.project.HotelDoor.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseUser;
import com.project.HotelDoor.data.DAO.UserDAO;
import com.project.HotelDoor.data.Hotel;
import com.project.HotelDoor.data.Review;
import com.project.HotelDoor.data.ReviewRepository;
import com.project.HotelDoor.data.User;
import com.project.HotelDoor.data.UserLiveData;
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

    public void getHotels()
    {
        reviewRepository.getHotels();
    }

    public MutableLiveData<ArrayList<Hotel>> getHotelsLiveData()
    {
        return reviewRepository.getHotelsLiveData();
    }

    public MutableLiveData<ArrayList<Review>> getReviewsLiveData()
    {
        return reviewRepository.getReviewsLiveData();
    }

    public void updateHotel(Hotel hotel) {
        reviewRepository.updateHotel(hotel);
    }

    public MutableLiveData<Review> getReviewLiveData() {
        return reviewRepository.getReviewLiveData();
    }

    public void setReviewLiveData(Review review)
    {
        reviewRepository.setReviewLiveData(review);
    }

    public MutableLiveData<Boolean> getIsLikePressed() {
        return reviewRepository.getIsLikePressed();
    }

    public void setIsLikePressed(boolean statement) {
        reviewRepository.setIsLikePressed(statement);
    }

    public void removeHotel(Hotel hotel)
    {
        reviewRepository.removeHotel(hotel);
    }

    public void postHotel(Hotel hotel)
    {
        reviewRepository.postHotel(hotel);
    }

    public void updateLikes(int likes)
    {
        userRepository.updateUser("likes",likes);
    }

    public User getUserModal(String uid)
    {
        return reviewRepository.getUserModal(uid);
    }

    public LiveData<FirebaseUser> getCurrentUser()
    {
        return reviewRepository.getCurrentUser();
    }

}