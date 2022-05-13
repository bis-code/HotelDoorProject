package com.project.HotelDoor.data.DAO;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.project.HotelDoor.data.Review;
import com.project.HotelDoor.data.Role;
import com.project.HotelDoor.data.User;
import com.project.HotelDoor.data.UserLiveData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import kotlin.collections.UArraySortingKt;

public class UserDAO {
    private final UserLiveData currentUser;
    private final Application app;
    private static UserDAO instance;

    private MutableLiveData<String> authenticationMessage = new MutableLiveData<>("");
    private MutableLiveData<Boolean> progressBar = new MutableLiveData<>(false);

    //Verify email
    private MutableLiveData<Boolean> isEmailVerified = new MutableLiveData<>(false);

    //enter sign in
    private MutableLiveData<Boolean> signInPressed = new MutableLiveData<>(false);

    //sign out
    private MutableLiveData<Boolean> signOut = new MutableLiveData<>(false);

    private MutableLiveData<User> user = new MutableLiveData<>(null);

    //Authentication
    private FirebaseAuth firebaseAuth;

    //Firebase Database
    private FirebaseFirestore firebaseDatabase;

    User returnedUser;
    User userModal = new User();

    private UserDAO(Application app) {
        this.app = app;
        currentUser = new UserLiveData();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseFirestore.getInstance();
    }

    public static UserDAO getInstance(Application app) {
        if (instance == null) {
            instance = new UserDAO(app);
        }
        return instance;
    }

    public MutableLiveData<Boolean> isEmailVerified() {
        if (currentUser.getValue() != null) {
            isEmailVerified.postValue(currentUser.getValue().isEmailVerified());
        }
        return isEmailVerified;
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

    public void setProgressBar(boolean statement) {
        progressBar.postValue(statement);
    }

    public void setAuthenticationMessage(boolean thread, String authenticationMessage) {
        if (thread) {
            this.authenticationMessage.postValue(authenticationMessage);
        } else {
            this.authenticationMessage.setValue(authenticationMessage);
        }
    }

    public LiveData<FirebaseUser> getCurrentUser() {
        return currentUser;
    }

    public LiveData<User> getUser() {
        return user;
    }

    public LiveData<Boolean> getSignOut() {
        return signOut;
    }

    public void signOut() {
        AuthUI.getInstance().signOut(app.getApplicationContext());
        signOut.setValue(true);
    }

    public void registerAccount(Activity activity, String email, String password) {
        try{
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success
                                Log.d(TAG, "createUserWithEmail:success");
                                authenticationMessage.postValue("User created!");
                                createUser(getCurrentUser().getValue().getUid(), email);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                authenticationMessage.postValue("Error on creating user");
                            }
                        }
                    });
            signOut.postValue(false);
        }
        catch (Exception e)
        {
            authenticationMessage.postValue("Exception: " + e.getMessage());
        }
    }

    public void loginAccount(Activity activity, String email, String password) {
        try{
            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(activity, task -> {
                        if (task.isSuccessful()) {
                            //Sign in success
                            Log.d(TAG, "signInUserWithEmail:success");
                            authenticationMessage.postValue("You are signed in!");
                        } else {
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            authenticationMessage.postValue("Error on signing in");
                        }
                    });
            signOut.postValue(false);
        }
        catch (Exception e)
        {
            authenticationMessage.postValue("Exception: " + e.getMessage());
        }
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
                        Log.e(TAG, "Email sent!");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        authenticationMessage.postValue("Error! Reset link is not sent!");
                        Log.w(TAG, e.getMessage());
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

    public void createUser(String uid, String email) {

        Map<String, Object> user = new HashMap<>();
        user.put("email", email);
        user.put("uid", uid);
        user.put("reviews", 0);
        user.put("userName", "Not set");
        user.put("fullName", "Not set");
        user.put("streetAddress", "Not set");
        user.put("numberAddress", -1);
        user.put("role", Role.MEMBER);
        user.put("likes",0);

        firebaseDatabase.collection("users").document(uid)
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "User created successfully!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing the user", e);
                    }
                });
    }

    public void updateUserInformation(String userName, String fullName, String phone, String streetAddress, String numberStreet) {
        if(userName != null && !userName.isEmpty())
        {
            updateUser("userName",userName);
        }
        if(fullName != null && !fullName.isEmpty()){
            updateUser("fullName",fullName);
        }
        if(phone != null && !phone.isEmpty()){
            updateUser("phone",phone);
        }
        if(streetAddress != null && !streetAddress.isEmpty())
        {
            updateUser("streetAddress",streetAddress);
        }
        if(numberStreet != null && !numberStreet.isEmpty()){
            int numberOfStreet = Integer.parseInt(numberStreet);
            updateUser("numberStreet",numberOfStreet);
        }
    }

    public void updateRole(Role role) {
        switch (role) {
            case ADMIN:
                updateUser("role", Role.ADMIN);
                break;
            case MEMBER:
                updateUser("role", Role.MEMBER);
                break;
            case ACTIVE:
                updateUser("role", Role.ACTIVE);
                break;
            case TRIPADVISOR:
                updateUser("role", Role.TRIPADVISOR);
                break;
            case LEGEND:
                updateUser("role", Role.LEGEND);
                break;
        }
    }

    public void updateUser(String column, Object object)
    {
        DocumentReference userDocument = firebaseDatabase.collection("users").document(firebaseAuth.getCurrentUser().getUid());
        userDocument
                .update(
                        column,object
                )
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "Document User with " + userDocument.getId() + " has been updated");
                        authenticationMessage.postValue("Information has been updated!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error updating user document " + userDocument.getId(), e);
                        authenticationMessage.postValue("Information couldn't be updated.");
                    }
                });
    }


    //used method for old requirements
    public void setUser(String uid) {
        returnedUser = new User();
        if (firebaseAuth.getCurrentUser() != null) {
            DocumentReference docRef = firebaseDatabase.collection("users").document(uid);
            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    returnedUser = documentSnapshot.toObject(User.class);
                    user.postValue(returnedUser);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    user.postValue(null);
                    Log.e(TAG, e.getMessage());
                }
            });
        }
    }


    //new method for getting the user
    public User getUserModal(String uid) {
            DocumentReference docRef = firebaseDatabase.collection("users").document(uid);
            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    userModal = documentSnapshot.toObject(User.class);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e(TAG, e.getMessage());
                }
            });
        return userModal;
    }

    public void verifyEmail() {
        progressBar.setValue(true);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if (currentUser.getValue() != null) {
                    currentUser.getValue().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "Email successfully sent!");
                                authenticationMessage.postValue("Email successfully sent!");
                                isEmailVerified.postValue(true);
                            } else {
                                Log.e(TAG, "Error sending the mail. Error: " + task.getException());
                                authenticationMessage.postValue("Error sending the mail.");
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e(TAG, "Error sending the mail. Error: " + e.getMessage());
                            authenticationMessage.postValue("Error sending the mail.");
                        }
                    });
                }
                progressBar.postValue(false);
            }
        }, 3000);
    }


    public void firebaseAuthWithGoogle(boolean isRegister, GoogleSignInAccount account, FragmentActivity activity) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential).
                addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            authenticationMessage.postValue("Authentication successful!");
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            if(user != null && isRegister)
                            {
                                createUser(user.getUid(),user.getEmail());
                            }
                        }
                        else{
                            authenticationMessage.postValue("Authentication failed!");
                            Log.e(TAG,"Google: " + task.getException());
                        }
                    }
                });
    }
}