package com.project.HotelDoor.data;

import static android.content.ContentValues.TAG;
import static android.provider.Settings.System.getString;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.project.HotelDoor.R;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

public class UserRepository {
    private final UserLiveData currentUser;
    private final Application app;
    private static UserRepository instance;

    private MutableLiveData<String> authenticationMessage = new MutableLiveData<>("");
    private MutableLiveData<Boolean> progressBar = new MutableLiveData<>(false);

    //enter sign in
    private MutableLiveData<Boolean> signInPressed = new MutableLiveData<>(false);

    //sign out
    private MutableLiveData<Boolean> signOut = new MutableLiveData<>(false);

    //Authentication
    private FirebaseAuth firebaseAuth;

    private UserRepository(Application app) {
        this.app = app;
        currentUser = new UserLiveData();

        firebaseAuth = FirebaseAuth.getInstance();
    }

    public static UserRepository getInstance(Application app) {
        if (instance == null) {
            instance = new UserRepository(app);
        }
        return instance;
    }

    public LiveData<Boolean> getSignInPressed() {
        return signInPressed;
    }

    public void setSignInPressed(Boolean isSignInPressed) {
        this.signInPressed.setValue(isSignInPressed);
    }

    public LiveData<String> getAuthenticationMessage() {
        return authenticationMessage;
    }

    public LiveData<Boolean> getProgressBar() {
        return progressBar;
    }

    public LiveData<FirebaseUser> getCurrentUser() {
        return currentUser;
    }

    public LiveData<Boolean> getSignOut() {
        return signOut;
    }

    public void signOut() {
        AuthUI.getInstance().signOut(app.getApplicationContext());
        signOut.setValue(true);
    }

    public void registerAccount(Activity activity, String email, String password) {
        progressBar.setValue(true);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success
                                    Log.d(TAG, "createUserWithEmail:success");
                                    authenticationMessage.postValue("User created!");
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    authenticationMessage.postValue("Error on creating user");
                                }
                            }
                        });
                signOut.postValue(false);
                progressBar.postValue(false);
            }
        }, 3000);
    }

    public void loginAccount(Activity activity, String email, String password) {
        progressBar.setValue(true);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                firebaseAuth.signInWithEmailAndPassword(email, password).
                        addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    //Sign in success
                                    Log.d(TAG, "signInUserWithEmail:success");
                                    authenticationMessage.postValue("You are signed in!");
                                } else {
                                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                                    authenticationMessage.postValue("Error on signing in");
                                }
                            }
                        });
                signOut.postValue(false);
                progressBar.postValue(false);
            }
        }, 3000);
    }

    public void forgotPassword(View view) {
        EditText resetEmail = new EditText(view.getContext());
        AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(view.getContext());
        passwordResetDialog.setTitle("Reset password?");
        passwordResetDialog.setMessage("Enter your email to reset the password");
        passwordResetDialog.setView(resetEmail);

        passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                firebaseAuth.sendPasswordResetEmail(resetEmail.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        authenticationMessage.postValue("Email sent!");
                        Log.e(TAG,"Email sent!");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        authenticationMessage.postValue("Error! Reset link is not sent!");
                        Log.w(TAG,e.getMessage());
                    }
                });

            }
        });

        passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                authenticationMessage.postValue("Reset password cancelled.");
            }
        });

        passwordResetDialog.create().show();
    }
}

//    public void registerGoogleAccount(Activity activity)
//    {
//        BeginSignInRequest.builder()
//                .setGoogleIdTokenRequestOptions(BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
//                        .setSupported(true)
//                        // Your server's client ID, not your Android client ID.
//                        .setServerClientId(getString(R.string.default_web_client_id))
//                        // Only show accounts previously used to sign in.
//                        .setFilterByAuthorizedAccounts(true)
//                        .build())
//                .build();
//
//        SignInCredential googleCredential = oneTapClient.getSignInCredentialFromIntent(data);
//        String idToken = googleCredential.getGoogleIdToken();
//        if (idToken !=  null) {
//        AuthCredential firebaseCredential = GoogleAuthProvider.getCredential(idToken, null);
//        firebaseAuth.signInWithCredential(firebaseCredential)
//                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "signInWithCredential:success");
//                            signOut.postValue(false);
//
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            Log.w(TAG, "signInWithCredential:failure", task.getException());
//
//                        }
//                    }
//                });
//    }
//    }
