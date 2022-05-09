package com.project.HotelDoor.ui.fragments;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.project.HotelDoor.viewmodel.AddInformationViewModel;
import com.project.HotelDoor.R;

public class AddInformationFragment extends Fragment {

    private AddInformationViewModel mViewModel;
    EditText inputUserName;
    EditText inputFullName;
    EditText inputPhone;
    EditText inputStreetAddress;
    EditText inputStreetNumber;
    Button updateInformation;

    public static AddInformationFragment newInstance() {
        return new AddInformationFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.add_information_fragment, container, false);

        inputUserName = view.findViewById(R.id.inputUserName);
        inputFullName = view.findViewById(R.id.inputFullName);
        inputPhone = view.findViewById(R.id.inputPhone);
        inputStreetAddress = view.findViewById(R.id.inputStreetAddress);
        inputStreetNumber = view.findViewById(R.id.inputNumberStreet);
        updateInformation = view.findViewById(R.id.updateUserInfoButton);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AddInformationViewModel.class);

        updateInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               try{
                   mViewModel.updateUserInformation(
                           inputUserName.getText().toString(),
                           inputFullName.getText().toString(),
                           inputPhone.getText().toString(),
                           inputStreetAddress.getText().toString(),
                           inputStreetNumber.getText().toString()
                   );
               }
               catch (NullPointerException |  NumberFormatException e)
               {
                   Toast.makeText(getActivity(), "Fields cannot be empty...", Toast.LENGTH_SHORT).show();
               }
            }
        });
    }

}