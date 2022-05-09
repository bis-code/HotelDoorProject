package com.project.HotelDoor.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.project.HotelDoor.data.Hotel;
import com.project.HotelDoor.data.Review;
import com.project.HotelDoor.ui.fragments.AddInformationFragment;
import com.project.HotelDoor.ui.fragments.HomeFragment;
import com.project.HotelDoor.ui.fragments.MapFragment;
import com.project.HotelDoor.R;
import com.project.HotelDoor.ui.fragments.PostReviewFragment;
import com.project.HotelDoor.ui.fragments.SettingsFragment;
import com.project.HotelDoor.data.Stage;
import com.project.HotelDoor.ui.fragments.LoginFragment;
import com.project.HotelDoor.ui.fragments.ProfileFragment;
import com.project.HotelDoor.ui.fragments.RegisterFragment;
import com.project.HotelDoor.viewmodel.MainActivityViewModel;
import com.project.HotelDoor.viewmodel.PostReviewViewModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Stage stage;
    //TODO: repair bug it does not go back when pressing on create account button on login fragment.
    BottomNavigationView bottomNavigationView;
    BottomAppBar bottomAppBar;
    FloatingActionButton floatingPlus;
    //TODO: need to add functionality for the bottomNAvigationView so it changes between fragments

    private final RegisterFragment registerFragment = new RegisterFragment();
    private final LoginFragment loginFragment = new LoginFragment();
    private final ProfileFragment profileFragment = new ProfileFragment();
    private final MapFragment mapFragment = new MapFragment();
    private final SettingsFragment settingsFragment = new SettingsFragment();
    private final HomeFragment homeFragment = new HomeFragment();
    private final AddInformationFragment addInformationFragment = new AddInformationFragment();
    private final PostReviewFragment postReviewFragment = new PostReviewFragment();

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
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
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
            Fragment fragment = null;
            if (user != null) {
                viewmodel.init(user);
                stage.setStage("profile");
                getSupportFragmentManager().beginTransaction().replace(R.id.navigationFragment, homeFragment).commit();
                makeBottomNavVisible(true);
            } else {
                makeBottomNavVisible(false);
                viewmodel.getSignInPressed().observe(this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean isSignInPressed) {
                        if (isSignInPressed) {
                            stage.setStage("login");
                            getSupportFragmentManager().beginTransaction().replace(R.id.navigationFragment, loginFragment).commit();
                        } else {
                            stage.setStage("register");
                            getSupportFragmentManager().beginTransaction().replace(R.id.navigationFragment, registerFragment).commit();
                        }
                    }
                });
            }
        });

        viewmodel.getAuthenticationMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String message) {
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });

        floatingPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.navigationFragment, postReviewFragment).commit();
            }
        });
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;
            switch (item.getItemId()) {
                case R.id.home:
                    fragment = new HomeFragment();
                    break;
                case R.id.mySettings:
                    fragment = new SettingsFragment();
                    break;
                case R.id.myAccount:
                    fragment = new ProfileFragment();
                    break;
                case R.id.placeholder:
                    fragment = new PostReviewFragment();
                    break;
                case R.id.browsingMap:
                    fragment = new MapFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.navigationFragment, fragment).commit();
            return true;
        }
    };


    public void makeBottomNavVisible(boolean visible) {
        if (visible) {
            bottomNavigationView.setVisibility(View.VISIBLE);
            bottomAppBar.setVisibility(View.VISIBLE);
            floatingPlus.setVisibility(View.VISIBLE);
        } else {
            bottomNavigationView.setVisibility(View.INVISIBLE);
            bottomAppBar.setVisibility(View.INVISIBLE);
            floatingPlus.setVisibility(View.INVISIBLE);
        }
    }

    public void switchAddFragment(View view) {
        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.navigationFragment, addInformationFragment).commit();
    }

    public void onArrowBack(View view) {
        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.navigationFragment, homeFragment).commit();
        //TODO: to make such that when it goes back to home the navigation drawer is set to home item.
    }

    public void postReview(View view) {

        //TODO need to solve those = they are null
        TextView reviewDescription = view.findViewById(R.id.inputReviewDescription);
        RatingBar ratingReviewBar = view.findViewById(R.id.ratingReviewBar);
        TextView inputHotelName =  view.findViewById(R.id.inputHotelName);
        TextView inputHotelAddress = view.findViewById(R.id.inputHotelAddress);

        try {
            PostReviewViewModel postReviewViewModel = new ViewModelProvider(this).get(PostReviewViewModel.class);
            if (postReviewViewModel.getCurrentUser().getValue() != null) {
                Review review = new Review(postReviewViewModel.getCurrentUser().getValue().getUid(), reviewDescription.getText().toString(), ratingReviewBar.getRating());
                Hotel hotel = postReviewViewModel.getHotel(inputHotelName.getText().toString());
                if (hotel != null) {
                    hotel.getReviews().add(review);
                    postReviewViewModel.updateHotel(hotel);
                } else {
                    hotel = new Hotel(inputHotelAddress.getText().toString(), inputHotelName.getText().toString(), new ArrayList<Review>());
                    hotel.getReviews().add(review);
                    postReviewViewModel.postHotel(hotel);
                }
                postReviewViewModel.postReview(review, hotel);
            }

            getSupportFragmentManager().
                    beginTransaction().
                    replace(R.id.navigationFragment, homeFragment).
                    commit();

        } catch (NullPointerException e) {
            Toast.makeText(this, "Fields cannot be empty..", Toast.LENGTH_SHORT).show();
        }
    }

    public void cancelPostingReview(View view) {
        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.navigationFragment, homeFragment).
                commit();
    }

//    public void clickProfile(MenuItem item)
//    {
//        stage.setStage("profile");
//        startActivity();
//    }
}