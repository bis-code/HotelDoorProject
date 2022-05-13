package com.project.HotelDoor.ui.fragments;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;
import com.project.HotelDoor.data.User;
import com.project.HotelDoor.viewmodel.ProfileViewModel;
import com.project.HotelDoor.R;

import org.w3c.dom.Text;

public class ProfileFragment extends Fragment {
    private ProfileViewModel mViewModel;
    View imagineLayout;
    View addInfoLayout;
    View emailLayout;
    View isEmailVerifiedLayout;
    Button verifyEmailButton;

    //User information
    TextView email;
    TextView myReviews;
    TextView addInformationText;
    ImageView addInformation;
    TextView phone;
    TextView address;
    TextView name;
    TextView username;
    TextView likes;
    TextView myRole;

    User user;

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.profile_fragment, container, false);

        imagineLayout = view.findViewById(R.id.imagineCardView);
        addInfoLayout = view.findViewById(R.id.addInformationCardView);
        emailLayout = view.findViewById(R.id.emailCardView);
        isEmailVerifiedLayout = view.findViewById(R.id.verifyEmail);
        isEmailVerifiedLayout.setVisibility(View.GONE);

        verifyEmailButton = view.findViewById(R.id.buttonVerifyEmail);

        email = view.findViewById(R.id.updateEmail);
        phone = view.findViewById(R.id.updateMobile);
        address = view.findViewById(R.id.updateAddress);
        username = view.findViewById(R.id.updateUsername);
        name = view.findViewById(R.id.updateName);
        myReviews = view.findViewById(R.id.myReviews);
        addInformationText = view.findViewById(R.id.addInformationText);
        addInformation = view.findViewById(R.id.addInformation);
        myRole = view.findViewById(R.id.myRole);

        //TODO: to be implemented
        likes = view.findViewById(R.id.likes);


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        user = null;

        mViewModel.isEmailVerified().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                int visibility = aBoolean ? View.GONE : View.VISIBLE;
                isEmailVerifiedLayout.setVisibility(visibility);
            }
        });

        if (verifyEmailButton != null) {
            verifyEmailButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mViewModel.verifyEmail();
                }
            });
        }

        myReviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: another fragment where it shows reviews created so far.
            }
        });

        mViewModel.getCurrentUser().observe(getViewLifecycleOwner(), new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if(firebaseUser != null)
                {
                    mViewModel.setUser(firebaseUser.getUid());
                }
            }
        });

        mViewModel.getUser().observe(getViewLifecycleOwner(), new Observer<User>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onChanged(User user) {
                if(user != null)
                {
                    email.setText(user.getEmail());
                    phone.setText(user.getPhone());
                    address.setText(user.getStreetAddress() + " " + user.getNumberAddress());
                    name.setText(user.getFullName());
                    username.setText(user.getUserName());
                    myRole.setText(user.getRole().name());
                    //TODO: to be implemented
                    likes.setText(user.getLikes() + " likes");
                }
            }
        });
    }
}