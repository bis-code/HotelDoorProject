package com.project.HotelDoor.viewmodel;

import android.app.Activity;
import android.app.Application;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.lifecycle.AndroidViewModel;

import com.project.HotelDoor.data.UserRepository;

public class LoginViewModel extends AndroidViewModel {
    private final UserRepository userRepository;



    public LoginViewModel(Application app) {
        super(app);
        userRepository = UserRepository.getInstance(app);
    }

    public void loginAccount(Activity activity, String email, String password) {
        userRepository.loginAccount(activity, email, password);
    }

    public void setSignInPressed(Boolean isSignInPressed) {
        userRepository.setSignInPressed(isSignInPressed);
    }
}