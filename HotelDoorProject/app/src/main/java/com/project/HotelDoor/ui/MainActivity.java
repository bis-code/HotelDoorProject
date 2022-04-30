package com.project.HotelDoor.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.project.HotelDoor.R;
import com.project.HotelDoor.data.Stage;
import com.project.HotelDoor.ui.fragments.LoginFragment;
import com.project.HotelDoor.ui.fragments.MainPageFragment;
import com.project.HotelDoor.ui.fragments.ProfileFragment;
import com.project.HotelDoor.ui.fragments.RegisterFragment;
import com.project.HotelDoor.viewmodel.MainActivityViewModel;

public class MainActivity extends AppCompatActivity {
    private Stage stage;
    //TODO: repair bug it does not go back when pressing on create account button on login fragment.
    BottomNavigationView bottomNavigationView;
    BottomAppBar bottomAppBar;
    FloatingActionButton floatingPlus;
    //TODO: need to add functionality for the bottomNAvigationView so it changes between fragments

    private final RegisterFragment registerFragment = new RegisterFragment();
    private final MainPageFragment mainPageFragment = new MainPageFragment();
    private final LoginFragment loginFragment = new LoginFragment();
    private final ProfileFragment profileFragment = new ProfileFragment();

    ProgressBar progressBar;
    private MainActivityViewModel viewmodel;

    //Bottom Nav Items


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewmodel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        setContentView(R.layout.activity_main);

        stage = new Stage();
        //showing bottom navigation view
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setBackground(null);
        bottomNavigationView.getMenu().getItem(2).setEnabled(true);
        bottomAppBar = findViewById(R.id.bottomAppBar);
        floatingPlus = findViewById(R.id.floatingPlus);

        progressBar = findViewById(R.id.progress_bar);

        viewmodel.getProgressBar().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                int visibility = isLoading ? View.VISIBLE : View.INVISIBLE;
                progressBar.setVisibility(visibility);
            }
        });

        viewmodel.getCurrentUser().observe(this, user -> {
            if(user != null) {
                viewmodel.init(user);
                stage.setStage("profile");
                startActivity();
            }
            else{
                stage.setStage("register");
                startActivity();
            }
        });

        viewmodel.getAuthenticationMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String message) {
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });

        viewmodel.getSignInPressed().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isSignInPressed) {
                if(isSignInPressed)
                {
                    stage.setStage("login");
                    startActivity();
                }
            }
        });

//        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
//            @Override
//            public void onNavigationItemReselected(@NonNull MenuItem item) {
//                switch (item.getItemId())
//                {
//                    case R.id.myAccount: {
//                        stage.setStage("profile");
//                        startActivity();
//                        break;
//                    }
//                    default:
//                        System.out.println("Nothing");
//                }
//            }
//        });
    }

    private void startActivity()
    {
        if(stage.getPreviousStage() != null)
        {
            removeActivity(stage.getPreviousStage());
        }
        switch (stage.getStage())
        {
            case "main" : {
                getSupportFragmentManager().beginTransaction().replace(R.id.mainPageFragment, mainPageFragment).commit();
                stage.setPreviousStage("main");
                makeBottomNavVisible(true);
                break;
            }
            case "register": {
                getSupportFragmentManager().beginTransaction().replace(R.id.registerFragment, registerFragment).commit();
                stage.setPreviousStage("register");
                makeBottomNavVisible(false);
                break;
            }
            case "login" : {
                getSupportFragmentManager().beginTransaction().replace(R.id.loginFragment, loginFragment).commit();
                stage.setPreviousStage("login");
                makeBottomNavVisible(false);
                break;
            }
            case "profile" : {
                getSupportFragmentManager().beginTransaction().replace(R.id.profileFragment, profileFragment).commit();
                stage.setPreviousStage("profile");
                makeBottomNavVisible(false);
                break;
            }
            default:
        }
    }

    private void removeActivity(String previousStage)
    {
        Fragment fragment;
        FragmentTransaction fragmentTransaction;
        switch (previousStage)
        {
            case "main" : {
                fragment = getSupportFragmentManager().findFragmentById(R.id.mainPageFragment);
                if(fragment != null) {
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.remove(fragment);
                    fragmentTransaction.commit();
                }
                break;
            }
            case "register" : {
                fragment = getSupportFragmentManager().findFragmentById(R.id.registerFragment);
                if(fragment != null) {
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.remove(fragment);
                    fragmentTransaction.commit();
                }
                break;
            }
            case "login" : {
                fragment = getSupportFragmentManager().findFragmentById(R.id.loginFragment);
                if(fragment != null) {
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.remove(fragment);
                    fragmentTransaction.commit();
                }
                break;
            }
            case "profile" : {
                fragment = getSupportFragmentManager().findFragmentById(R.id.profileFragment);
                if(fragment != null) {
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.remove(fragment);
                    fragmentTransaction.commit();
                }
                break;
            }
            default:
        }
    }

    public void makeBottomNavVisible(boolean visible)
    {
        if(visible)
        {
            bottomNavigationView.setVisibility(View.VISIBLE);
            bottomAppBar.setVisibility(View.VISIBLE);
            floatingPlus.setVisibility(View.VISIBLE);
        }
        else{
            bottomNavigationView.setVisibility(View.INVISIBLE);
            bottomAppBar.setVisibility(View.INVISIBLE);
            floatingPlus.setVisibility(View.INVISIBLE);
        }
    }

//    public void clickProfile(MenuItem item)
//    {
//        stage.setStage("profile");
//        startActivity();
//    }
}