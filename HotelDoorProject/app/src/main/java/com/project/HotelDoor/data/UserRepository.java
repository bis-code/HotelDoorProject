package com.project.HotelDoor.data;

import android.app.Activity;
import android.app.Application;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseUser;

public class UserRepository {
    private static UserRepository instance;
    private static UserDAO userDAO;
    private UserRepository(Application app) {
        userDAO = UserDAO.getInstance(app);
    }


    public static UserRepository getInstance(Application app) {
        if (instance == null) {
            instance = new UserRepository(app);
        }
        return instance;
    }

    public LiveData<Boolean> getSignInPressed()
    {
        return userDAO.getSignInPressed();
    }

    public void setSignInPressed(Boolean isSignInPressed) {
        userDAO.setSignInPressed(isSignInPressed);
    }

    public LiveData<String> getAuthenticationMessage()
    {
        return userDAO.getAuthenticationMessage();
    }

    public LiveData<Boolean> getProgressBar()
    {
        return userDAO.getProgressBar();
    }

    public LiveData<FirebaseUser> getCurrentUser() {
       return userDAO.getCurrentUser();
    }

    public LiveData<Boolean> getSignOut() {
        return userDAO.getSignOut();
    }

    public void signOut() {
       userDAO.signOut();
    }

    public void registerAccount(Activity activity, String email, String password) {
        userDAO.registerAccount(activity,email,password);
    }

    public void loginAccount(Activity activity, String email, String password)
    {
        userDAO.loginAccount(activity,email,password);
    }

    public void forgotPassword(View view)
    {
        userDAO.forgotPassword(view);
    }

    public MutableLiveData<Boolean> isEmailVerified()
    {
       return userDAO.isEmailVerified();
    }

    public void verifyEmail(){
        userDAO.verifyEmail();
    }
}
