package com.project.HotelDoor.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseUser;
import com.project.HotelDoor.data.UserRepository;

public class ProfileViewModel extends AndroidViewModel {
    private final UserRepository userRepository;

    public ProfileViewModel(Application app) {
        super(app);
        userRepository = UserRepository.getInstance(app);
    }

    public MutableLiveData<Boolean> isEmailVerified()
    {
       return userRepository.isEmailVerified();
    }


    public void verifyEmail()
    {
        userRepository.verifyEmail();
    }

    public void logout()
    {
        userRepository.signOut();
    }

}