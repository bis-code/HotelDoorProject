package com.project.HotelDoor.ui.fragments;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.HotelDoor.R;
import com.project.HotelDoor.viewmodel.HotelReviewsViewModel;

public class HotelReviewsFragment extends Fragment {

    private HotelReviewsViewModel mViewModel;

    public static HotelReviewsFragment newInstance() {
        return new HotelReviewsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hotel_reviews, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(HotelReviewsViewModel.class);
        // TODO: Use the ViewModel
    }

}