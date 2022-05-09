package com.project.HotelDoor.ui.fragments;

import static android.content.ContentValues.TAG;

import androidx.lifecycle.ViewModelProvider;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.project.HotelDoor.R;
import com.project.HotelDoor.data.Review;
import com.project.HotelDoor.data.ReviewAdapter;
import com.project.HotelDoor.viewmodel.HomeViewModel;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private HomeViewModel mViewModel;
    private ProgressBar progressBar;
    private ArrayList<Review> reviewsArrayList;
    RecyclerView revFeed;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        revFeed = view.findViewById(R.id.idReviewFeeds);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        loadReviewData();
        //todo progressbar

    }

    public void loadReviewData() {
        ArrayList<Review> reviews = mViewModel.getReviews();
        try{
            ReviewAdapter adapter = new ReviewAdapter(reviews, getContext(), getActivity().getApplication());

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);

            revFeed.setLayoutManager(linearLayoutManager);

            revFeed.setAdapter(adapter);
        }

        catch(Exception e)
        {
            e.printStackTrace();
            Log.e(TAG, "Error getting reviews data: " + e.getMessage());
        }
    }

}