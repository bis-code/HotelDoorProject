package com.project.HotelDoor.viewmodel;

import android.app.Application;
import android.view.View;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.project.HotelDoor.data.UserRepository;

public class AddInformationViewModel extends AndroidViewModel {
    private final UserRepository userRepository;

    public AddInformationViewModel(Application app)
    {
        super(app);
        userRepository = UserRepository.getInstance(app);
    }

    public void updateUserInformation(String userName, String fullName, String phone, String streetAddress, String numberStreet) {
        userRepository.updateUserInformation(userName,fullName,phone,streetAddress,numberStreet);
    }
}