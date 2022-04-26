package com.project.HotelDoor;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.project.HotelDoor.viewmodel.MainPageViewModel;

///**
// * A simple {@link Fragment} subclass.
// * Use the {@link MainPageFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class MainPageFragment extends Fragment {

    Button logoutButton;
    private MainPageViewModel mainPageViewModel;

    public static MainPageFragment newInstance() {
        return new MainPageFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mainPageViewModel = new ViewModelProvider(this).get(MainPageViewModel.class);

        if (logoutButton != null) {
            logoutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mainPageViewModel.logout();
                }
            });
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_page, container, false);
        logoutButton = view.findViewById(R.id.logoutButton);
        // Inflate the layout for this fragment

        return view;
    }
}