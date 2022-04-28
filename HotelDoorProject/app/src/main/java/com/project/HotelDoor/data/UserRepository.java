package com.project.HotelDoor.data;

import static android.content.ContentValues.TAG;
import static android.provider.Settings.System.getString;

import android.app.Activity;
import android.app.Application;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.project.HotelDoor.R;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

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
}
