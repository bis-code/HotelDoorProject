package com.project.HotelDoor.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.project.HotelDoor.data.UserRepository;

public class SettingsViewModel extends AndroidViewModel {
    private final UserRepository userRepository;

    public SettingsViewModel(Application app) {
        super(app);
        userRepository = UserRepository.getInstance(app);
    }

    public void logout()
    {
        userRepository.signOut();
    }
}