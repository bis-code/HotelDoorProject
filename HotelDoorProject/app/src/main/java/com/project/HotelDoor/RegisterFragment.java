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
import android.widget.EditText;
import android.widget.TextView;

import com.project.HotelDoor.viewmodel.RegisterViewModel;

public class RegisterFragment extends Fragment {

    private RegisterViewModel registerViewModel;

    //functionality
    EditText inputEmail;
    EditText inputPassword;
    Button registerButton;
    Button toLoginButton;
    TextView forgotPassword;


    public static RegisterFragment newInstance() {
        return new RegisterFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.register_fragment, container, false);

        inputEmail = view.findViewById(R.id.inputEmail);
        inputPassword = view.findViewById(R.id.inputPassword);
        registerButton = view.findViewById(R.id.registerButton);
        toLoginButton = view.findViewById(R.id.toLoginButton);
        forgotPassword = view.findViewById(R.id.forgotPassword);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);

        if (registerButton != null) {
            registerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getView() != null) {
                        registerViewModel.registerAccount((Activity) getView().getContext(), inputEmail.getText().toString(),
                                inputPassword.getText().toString());
                    }
                }
            });
        }
        if(toLoginButton != null)
        {
            toLoginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    registerViewModel.setSignInPressed(true);
                }
            });
        }
    }
}