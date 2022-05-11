package com.project.HotelDoor.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.maps.MapView;
import com.project.HotelDoor.data.Hotel;
import com.project.HotelDoor.data.ReviewRepository;
import com.project.HotelDoor.data.UserRepository;

import java.util.ArrayList;

public class MapViewModel extends AndroidViewModel {

    private ReviewRepository reviewRepository;
    private UserRepository userRepository;

    public MapViewModel(Application app)
    {
        super(app);
        reviewRepository = ReviewRepository.getInstance(app);
        userRepository = UserRepository.getInstance(app);
    }

    public void getHotels()
    {
        reviewRepository.getHotels();
    }

    public MutableLiveData<ArrayList<Hotel>> getHotelsLiveData()
    {
        return reviewRepository.getHotelsLiveData();
    }

    public MutableLiveData<Hotel> getHotelLiveData()
    {
        return reviewRepository.getHotelLiveData();
    }
    public void getHotel(String name)
    {
        reviewRepository.getHotel(name);
    }
    public void setHotelNameLiveData(String hotelName) {
        reviewRepository.setHotelNameLiveData(hotelName);
    }

    public void setAuthenticationMessage(boolean thread, String message)
    {
        reviewRepository.setAuthenticationMessage(thread,message);
    }
}