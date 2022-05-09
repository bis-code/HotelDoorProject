package com.project.HotelDoor.data.DAO;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.google.firebase.auth.FirebaseUser;
import com.project.HotelDoor.data.Hotel;
import com.project.HotelDoor.data.Review;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;


public class ReviewRepository {

    private ReviewDAO reviewDAO;
    private UserDAO userDAO;
    private static ReviewRepository instance;

    public static ReviewRepository getInstance(Application app)
    {
        if(instance == null)
        {
              instance = new ReviewRepository(app);
        }
        return instance;
    }

    public ReviewRepository(Application application)
    {
        reviewDAO = ReviewDAO.getInstance(application);
        userDAO = UserDAO.getInstance(application);
    }

    public void postReview(Review review, Hotel hotel)
    {
         userDAO.setProgressBar(true);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                reviewDAO.postReview(review, hotel);
                userDAO.setProgressBar(false);
            }
        }, 3000);
    }
    public void updateHotel(Hotel hotel) {
        reviewDAO.updateHotel(hotel);
    }
    public Hotel getHotel(String name)
    {
        return reviewDAO.getHotel(name);
    }
    public void postHotel(Hotel hotel)
    {
        reviewDAO.postHotel(hotel);
    }

    public LiveData<FirebaseUser> getCurrentUser()
    {
        return userDAO.getCurrentUser();
    }
    // TODO: Implement the ViewModel
}
