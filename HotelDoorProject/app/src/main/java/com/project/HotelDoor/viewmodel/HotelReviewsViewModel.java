package com.project.HotelDoor.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.project.HotelDoor.data.Hotel;
import com.project.HotelDoor.data.ReviewRepository;

public class HotelReviewsViewModel extends AndroidViewModel {
    private ReviewRepository reviewRepository;

    public HotelReviewsViewModel(Application app)
    {
        super(app);
        reviewRepository = ReviewRepository.getInstance(app);
    }

    public MutableLiveData<String> getHotelNameLiveData()
    {
        return reviewRepository.getHotelNameLiveData();
    }

    public void setHotelNameLiveData(String hotelName) {
        reviewRepository.setHotelNameLiveData(hotelName);
    }

    public MutableLiveData<Hotel> getHotelLiveData()
    {
        return reviewRepository.getHotelLiveData();
    }
    public void getHotel(String hotelName)
    {
        reviewRepository.setHotelNameLiveData(hotelName);
    }
}