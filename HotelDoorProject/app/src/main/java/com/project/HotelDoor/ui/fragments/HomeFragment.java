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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.project.HotelDoor.R;
import com.project.HotelDoor.data.Hotel;
import com.project.HotelDoor.data.Review;
import com.project.HotelDoor.data.ReviewAdapter;
import com.project.HotelDoor.data.User;
import com.project.HotelDoor.viewmodel.HomeViewModel;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private HomeViewModel mViewModel;
    private ProgressBar progressBar;
    private ArrayList<Review> reviewsArrayList;
    RecyclerView revFeed;
    TextView likeReview;
    ReviewAdapter reviewAdapter;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        likeReview = view.findViewById(R.id.likeReview);
        revFeed = view.findViewById(R.id.idReviewFeeds);
        revFeed.hasFixedSize();
        reviewsArrayList = new ArrayList<>();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        loadReviewData();
        mViewModel.getHotelsLiveData().observe(getViewLifecycleOwner(), hotels -> {
            for (Hotel hotel : hotels) {
                reviewsArrayList.addAll(hotel.getReviews());
            }
            if (!reviewsArrayList.isEmpty()) {
                try {
                    reviewAdapter = new ReviewAdapter(reviewsArrayList, getContext(), getActivity().getApplication());

                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);

                    revFeed.setLayoutManager(linearLayoutManager);

                    revFeed.setAdapter(reviewAdapter);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(TAG, "Error getting reviews data: " + e.getMessage());
                }
            }
        });

        mViewModel.getReviewLiveData().observe(getViewLifecycleOwner(), review -> {
            if (review != null) {
                mViewModel.getIsLikePressed().observe(getViewLifecycleOwner(), statement ->{
                    if(statement)
                    {
                        mViewModel.getHotels();
                        mViewModel.getHotelsLiveData().observe(getViewLifecycleOwner(), hotels ->
                        {
                            Hotel hotelToBeAdded = null;
                            Review reviewToBeRemoved = null;
                            for(Hotel hotel : hotels)
                            {
                                if(hotel.getName().equals(review.getHotelName()))
                                {
                                    hotelToBeAdded = hotel;

                                    for(Review findReview : hotel.getReviews())
                                    {
                                        if(findReview.getUniqID() == review.getUniqID())
                                        {
                                            reviewToBeRemoved = findReview;
                                        }
                                    }
                                    //remove hotel and update it again by posting with new review updated
                                    mViewModel.removeHotel(hotel);

                                    //posting the hotel with new review updated
                                    hotelToBeAdded.getReviews().remove(reviewToBeRemoved);
                                    hotelToBeAdded.getReviews().add(review);
                                    mViewModel.postHotel(hotelToBeAdded);
                                }
                            }
                        });
                    }
                });
            }
//            User user = mViewModel.getUserModal(mViewModel.getCurrentUser().getValue().getUid());
//            if(user != null)
//            {
//                int likes = user.getLikes() + 1;
//                mViewModel.updateLikes(likes);
//            }
        });
    }

    public void loadReviewData() {
        mViewModel.getHotels();
    }

}