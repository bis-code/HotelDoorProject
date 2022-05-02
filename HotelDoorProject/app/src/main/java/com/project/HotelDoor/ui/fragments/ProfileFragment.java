package com.project.HotelDoor.ui.fragments;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.project.HotelDoor.viewmodel.ProfileViewModel;
import com.project.HotelDoor.R;

public class ProfileFragment extends Fragment {
    private ProfileViewModel mViewModel;
    View imagineLayout;
    View addInfoLayout;
    View emailLayout;
    View isEmailVerifiedLayout;
    Button verifyEmailButton;
    Button logoutButton;

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
        logoutButton = view.findViewById(R.id.logoutButton);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        mViewModel.isEmailVerified().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                int visibility = aBoolean ? View.GONE : View.VISIBLE;
                isEmailVerifiedLayout.setVisibility(visibility);
            }
        });

        if (logoutButton != null) {
            logoutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mViewModel.logout();
                }
            });
        }

        if(verifyEmailButton != null)
        {
            verifyEmailButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mViewModel.verifyEmail();
                }
            });
        }

    }
}