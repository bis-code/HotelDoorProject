package com.project.HotelDoor.ui.fragments;

import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.project.HotelDoor.R;
import com.project.HotelDoor.viewmodel.LoginViewModel;

public class LoginFragment extends Fragment {

    private LoginViewModel loginViewModel;

    //functionality
    EditText inputEmailLogin;
    EditText inputPasswordLogin;
    Button loginButton;
    Button toRegisterButton;
    TextView forgotPasswordLogin;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.login_fragment, container, false);

        inputEmailLogin =  view.findViewById(R.id.inputEmailLogin);
        inputPasswordLogin = view.findViewById(R.id.inputPasswordLogin);
        loginButton = view.findViewById(R.id.loginButton);
        toRegisterButton = view.findViewById(R.id.toRegisterButton);
        forgotPasswordLogin = view.findViewById(R.id.forgotPasswordLogin);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        if(loginButton != null)
        {
            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    loginViewModel.loginAccount((Activity) view.getContext(),inputEmailLogin.getText().toString(), inputPasswordLogin.getText().toString());
                }
            });
        }

       if(toRegisterButton != null)
       {
           toRegisterButton.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   loginViewModel.setSignInPressed(false);
               }
           });
       }

        forgotPasswordLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginViewModel.forgotPassword(view);
            }
        });
    }
}