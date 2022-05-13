package com.project.HotelDoor.ui.fragments;

import static android.content.ContentValues.TAG;

import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
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
    ImageView googleSignIn;
    private GoogleSignInClient googleSignInClient;
    private final static int RC_SIGN_IN = 123;

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
        googleSignIn = view.findViewById(R.id.googleLogin);

        createRequestGoogle();

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
                    try{
                        loginViewModel.loginAccount((Activity) view.getContext(),inputEmailLogin.getText().toString(), inputPasswordLogin.getText().toString());
                    }
                    catch (NullPointerException e)
                    {
                        Toast.makeText(getActivity(), "Fields cannot be empty...", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

       if(toRegisterButton != null)
       {
           toRegisterButton.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view)  {
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
                firebaseAuthWithGoogle(account);
            }
            catch (ApiException e)
            {
                Toast.makeText(getContext(),e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        loginViewModel.firebaseAuthWithGoogle(false,account,getActivity());
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