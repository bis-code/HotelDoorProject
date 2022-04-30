package com.project.HotelDoor.ui.fragments;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Layout;
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
        isEmailVerifiedLayout.setVisibility(View.INVISIBLE);

        logoutButton = view.findViewById(R.id.logoutButton);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        if (isEmailVerifiedLayout != null) {
            if (!mViewModel.isEmailVerified()) {
                isEmailVerifiedLayout.setVisibility(View.VISIBLE);

                //verifiy button method
                Button verifyButton = isEmailVerifiedLayout.findViewById(R.id.buttonVerifyEmail);
                if (verifyButton != null)
                {
                    mViewModel.verifyEmail();
                }
            } else {
                isEmailVerifiedLayout = null;
            }

            //TODO: make such that verify cardview it's killed when email is verified (now it's still there, but invisible)
            //TODO: to make such that it kills by itself once the email is veified you need to use MutableLiveData in userDAO.class

        }

        if (logoutButton != null) {
            logoutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mViewModel.logout();
                }
            });
        }


    }

}