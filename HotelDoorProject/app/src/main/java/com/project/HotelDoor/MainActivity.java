package com.project.HotelDoor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements RegisterFragment.FragmentAListener {
    BottomNavigationView bottomNavigationView;
    private RegisterFragment registerFragment;
    private MainPageFragment mainPageFragment;
    ProgressBar progressBar;
    private MainActivityViewModel viewmodel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewmodel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        viewmodel.init();
        checkIfSignIn();
        setContentView(R.layout.activity_main);

        //showing bottom navigation view
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setBackground(null);
        bottomNavigationView.getMenu().getItem(2).setEnabled(false);
        progressBar = findViewById(R.id.progress_bar);

        //setting fragments
        registerFragment = new RegisterFragment();
        mainPageFragment = new MainPageFragment();

        viewmodel.getProgressBar().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                int visibility = isLoading ? View.VISIBLE : View.INVISIBLE;
                progressBar.setVisibility(visibility);
            }
        });

        viewmodel.getAuthenticationMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String message) {
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRegisterAccount(String email, String password) {
        viewmodel.onRegisterAccount(MainActivity.this, email, password);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    private void checkIfSignIn()
    {
        viewmodel.getCurrentUser().observe(this, user -> {
            if(user != null)
            {
                startMainPageActivity();
            }
            else {
                startRegisterActivity();
            }
        });
    }

    private void startRegisterActivity()
    {
        getSupportFragmentManager().beginTransaction().
                replace(R.id.registerFragment, registerFragment).commit();
    }

    private void startMainPageActivity()
    {
        getSupportFragmentManager().beginTransaction().
                replace(R.id.mainPageFragment, mainPageFragment).commit();
    }
}