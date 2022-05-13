package com.project.HotelDoor.ui.fragments;

import android.app.Activity;
import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.project.HotelDoor.R;
import com.project.HotelDoor.viewmodel.RegisterViewModel;

public class RegisterFragment extends Fragment {

    private RegisterViewModel registerViewModel;

    //functionality
    EditText inputEmail;
    EditText inputPassword;
    Button registerButton;
    Button toLoginButton;
    TextView forgotPassword;
    ImageView googleSignIn;
    private GoogleSignInClient googleSignInClient;
    private final static int RC_SIGN_IN = 123;


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
        googleSignIn = view.findViewById(R.id.googleLogin);

        createRequestGoogle();

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
                        try{
                            registerViewModel.registerAccount((Activity) getView().getContext(), inputEmail.getText().toString(),
                                    inputPassword.getText().toString());
                        }
                        catch (NullPointerException e)
                        {
                            Toast.makeText(getActivity(), "Fields cannot be empty..", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
            });
        }
        if (toLoginButton != null) {
            toLoginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    registerViewModel.setSignInPressed(true);
                }
            });
        }

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerViewModel.forgotPassword(view);
            }
        });

        googleSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_SIGN_IN)
        {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(true,account);
            }
            catch (ApiException e)
            {
                Toast.makeText(getContext(),e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(boolean isRegister,GoogleSignInAccount account) {
        registerViewModel.firebaseAuthWithGoogle(isRegister,account,getActivity());
    }

    public void createRequestGoogle()
    {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                requestIdToken(getString(com.firebase.ui.auth.R.string.default_web_client_id)).
                requestEmail().
                build();

        googleSignInClient = GoogleSignIn.getClient(getContext(),gso);
    }

    private void signIn()
    {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
}
