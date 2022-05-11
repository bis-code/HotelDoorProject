package com.project.HotelDoor.ui.fragments;

import static android.content.ContentValues.TAG;

import androidx.lifecycle.ViewModelProvider;

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

import com.project.HotelDoor.R;
import com.project.HotelDoor.data.ReviewAdapter;
import com.project.HotelDoor.viewmodel.HotelReviewsViewModel;

public class HotelReviewsFragment extends Fragment {

    private HotelReviewsViewModel mViewModel;
    RecyclerView revFeed;

    public static HotelReviewsFragment newInstance() {
        return new HotelReviewsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hotel_reviews, container, false);
        revFeed = view.findViewById(R.id.idReviewFeeds);
        revFeed.hasFixedSize();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(HotelReviewsViewModel.class);

        mViewModel.getHotelNameLiveData().observe(getViewLifecycleOwner(), hotelName -> {
            if(hotelName != null)
            {
                mViewModel.getHotel(hotelName);
            }
        });
        mViewModel.getHotelLiveData().observe(getViewLifecycleOwner(), hotel -> {
            if(!hotel.getReviews().isEmpty()){
                try{
                    ReviewAdapter adapter = new ReviewAdapter(hotel.getReviews(), getContext(), getActivity().getApplication());

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
        });
    }

}