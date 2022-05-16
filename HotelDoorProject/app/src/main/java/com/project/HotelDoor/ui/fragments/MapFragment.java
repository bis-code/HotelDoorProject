package com.project.HotelDoor.ui.fragments;

import static android.content.ContentValues.TAG;

import androidx.lifecycle.ViewModelProvider;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.project.HotelDoor.R;
import com.project.HotelDoor.data.Hotel;
import com.project.HotelDoor.viewmodel.MapViewModel;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapFragment extends Fragment implements OnMapReadyCallback {
    private MapViewModel mViewModel;
    private GoogleMap googleMap;
    TextView numberOfReviews;
    Button viewReviews;
    TextView addressOfHotel;
    View detailsHotel;

    public static MapFragment newInstance() {
        return new MapFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.map_fragment, container, false);

        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.google_map);

        supportMapFragment.getMapAsync(this);

        numberOfReviews = view.findViewById(R.id.numberOfReviews);
        addressOfHotel = view.findViewById(R.id.addressOfHotel);
        viewReviews = view.findViewById(R.id.seeReviews);
        detailsHotel = view.findViewById(R.id.mapDetails);

        detailsHotel.setVisibility(View.GONE);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MapViewModel.class);

        viewReviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.navigationFragment, new HotelReviewsFragment()).commit();
            }
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        try {
            this.googleMap = googleMap;
            googleMap.moveCamera(CameraUpdateFactory.zoomTo(10.0f));
            mViewModel.getHotels();
            mViewModel.getHotelsLiveData().observe(getViewLifecycleOwner(), hotels ->
            {
                if (hotels.size() > 0) {
                    Geocoder geocoder = new Geocoder(getActivity().getApplicationContext(), Locale.getDefault());
                    for (Hotel hotel : hotels) {
                        try {
                            List<Address> addressList = geocoder.getFromLocationName(hotel.getAddress(), 1);
                            if (addressList != null && addressList.size() > 0) {
                                Address address = addressList.get(0);
                                LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                                googleMap.addMarker(new MarkerOptions().position(latLng).title(hotel.getName()));

                                googleMap.animateCamera(CameraUpdateFactory.zoomTo(18.0f));

                                googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                            }
                        } catch (IOException e) {
                            Toast.makeText(getContext(), "Location not found for hotel: " + hotel.getName(), Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                }
            });
        } catch (Exception e) {
            Toast.makeText(getActivity(), "An error appeared: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }


        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {
                CameraUpdate zoom = CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 20);
                googleMap.moveCamera(zoom);

                mViewModel.getHotel(marker.getTitle());

                mViewModel.getHotelLiveData().observe(getViewLifecycleOwner(), hotel -> {
                    if (hotel != null) {
                        numberOfReviews.setText("Reviews: " + hotel.getReviews().size());
                        addressOfHotel.setText("Address: " + hotel.getAddress());
                        detailsHotel.setVisibility(View.VISIBLE);
                        mViewModel.setHotelNameLiveData(hotel.getName());
                    }
                });

                return false;
            }
        });

        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull LatLng latLng) {
                CameraUpdate zoom = CameraUpdateFactory.zoomOut();
                googleMap.moveCamera(zoom);
                detailsHotel.setVisibility(View.GONE);
            }
        });
    }
}