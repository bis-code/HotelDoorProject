package com.project.HotelDoor.viewmodel;

import android.app.Activity;
import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseUser;
import com.project.HotelDoor.data.UserRepository;

public class MainActivityViewModel extends AndroidViewModel {
    private final UserRepository userRepository;

    public MainActivityViewModel(Application app)
    {
        super(app);
        userRepository = UserRepository.getInstance(app);
    }

    public void init(FirebaseUser user)
    {
        String userId = user.getUid();
        //TODO: feature init functions for reviewRepository and so on...
    }

    public LiveData<FirebaseUser> getCurrentUser()
    {
        return userRepository.getCurrentUser();
    }

    public LiveData<String> getAuthenticationMessage()
    {
        return userRepository.getAuthenticationMessage();
    }

    public LiveData<Boolean> getProgressBar()
    {
        return userRepository.getProgressBar();
    }

    public LiveData<Boolean> getSignInPressed()
    {
        return userRepository.getSignInPressed();
    }

    //TODO: feature functionality for others repositories.
}