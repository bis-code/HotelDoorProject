package com.project.HotelDoor.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.google.firebase.auth.FirebaseUser;
import com.project.HotelDoor.data.ReviewRepository;
import com.project.HotelDoor.data.Hotel;
import com.project.HotelDoor.data.Review;

public class PostReviewViewModel extends AndroidViewModel {

    private ReviewRepository reviewRepository;

    public PostReviewViewModel(Application application)
    {
        super(application);
        reviewRepository = ReviewRepository.getInstance(application);
    }

    public void postReview(Review review, Hotel hotel)
    {
        reviewRepository.postReview(review, hotel);
    }
    public void updateHotel(Hotel hotel) {
        reviewRepository.updateHotel(hotel);
    }
    public Hotel getHotel(String name)
    {
        return reviewRepository.getHotel(name);
    }
    public void postHotel(Hotel hotel)
    {
        reviewRepository.postHotel(hotel);
    }

    public LiveData<FirebaseUser> getCurrentUser()
    {
        return  reviewRepository.getCurrentUser();
    }
}