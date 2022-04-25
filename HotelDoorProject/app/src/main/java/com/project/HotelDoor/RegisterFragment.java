package com.project.HotelDoor;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
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

public class RegisterFragment extends Fragment {

    private FragmentAListener listener;


    //functionality
    EditText inputEmail;
    EditText inputPassword;
    Button registerButton;
    Button toLoginButton;
    TextView forgotPassword;



    public static RegisterFragment newInstance() {
        return new RegisterFragment();
    }

    public interface FragmentAListener {
        void onRegisterAccount(String email, String password);
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

        toLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                listener.onRegisterAccount(inputEmail.getText().toString(),
                                            inputPassword.getText().toString());
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // TODO: Use the ViewModel
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof FragmentAListener)
        {
            listener = (FragmentAListener) context;
        }
        else{
            throw new RuntimeException(context.toString() +
                    " must implement FragmentAListener");

        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}