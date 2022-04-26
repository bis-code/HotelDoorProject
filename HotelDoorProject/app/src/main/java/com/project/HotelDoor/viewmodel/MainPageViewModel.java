package com.project.HotelDoor.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.project.HotelDoor.data.UserRepository;

public class MainPageViewModel extends AndroidViewModel {
    private final UserRepository userRepository;

    public MainPageViewModel(Application app)
    {
        super(app);
        userRepository = UserRepository.getInstance(app);
    }

    public void logout()
    {
        userRepository.signOut();
    }

}
