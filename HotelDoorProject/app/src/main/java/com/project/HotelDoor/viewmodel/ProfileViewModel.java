package com.project.HotelDoor.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseUser;
import com.project.HotelDoor.data.User;
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

    public LiveData<User> getUser()
    {
        return userRepository.getUser();
    }

    public void setUser(String uid)
    {
        userRepository.setUser(uid);
    }

    public LiveData<FirebaseUser> getCurrentUser() {
        return userRepository.getCurrentUser();
    }

    public void verifyEmail()
    {
        userRepository.verifyEmail();
    }

}