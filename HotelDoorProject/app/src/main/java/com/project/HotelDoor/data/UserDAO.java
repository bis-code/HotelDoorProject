package com.project.HotelDoor.data;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.DialogInterface;
import android.hardware.ConsumerIrManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class UserDAO {
    private final UserLiveData currentUser;
    private final Application app;
    private static UserDAO instance;

    private MutableLiveData<String> authenticationMessage = new MutableLiveData<>("");
    private MutableLiveData<Boolean> progressBar = new MutableLiveData<>(false);

    //enter sign in
    private MutableLiveData<Boolean> signInPressed = new MutableLiveData<>(false);

    //sign out
    private MutableLiveData<Boolean> signOut = new MutableLiveData<>(false);

    //Authentication
    private FirebaseAuth firebaseAuth;

    //Firebase Database
    private FirebaseFirestore firebaseDatabase;

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
//        createUser("dadsad", "Asdadasd");
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
                signInPressed.postValue(false);
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


    //TODO: make functionality for updating password, email etc etc.
    //TODO: make UI design for profile information and update information
    //TODO: use this method when user's email is verified!!!
    //TODO: save user ID in a variable that can be used in viewmodels!!
    public void createUser(String email, String password) {
        Map<String, Object> user = new HashMap<>();
        if (currentUser.getValue() != null) {
            User createUser = new User(firebaseAuth.getCurrentUser().getUid(), email, password);
            user.put("uid", createUser.getUserId());
            user.put("username", createUser.getUsername());
            user.put("password", createUser.getPassword());
            user.put("email", createUser.getEmail());
            user.put("comments", createUser.getComments());
            user.put("reviews", createUser.getReviews());
            user.put("fullName", createUser.getUsername());
            user.put("streetAddress", createUser.getStreetAddress());
            user.put("numberAddress", createUser.getNumberAddress());
            firebaseDatabase.collection("users")
                    .add(user).
                    addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference userDocument) {
                            Log.d(TAG, "DocumentSnapshot added with ID: " + userDocument.getId());
                            HashMap<String, Object> reviewss = new HashMap<>();
                            //TODO: will need to move add reviews and add comments functionality in other methods.
                            userDocument.collection("reviews").add(reviewss)
                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference reviewDocument) {
                                            Log.d(TAG, "DocumentSnapshot added with ID: " + reviewDocument.getId());

                                            HashMap<String, Object> commentss = new HashMap<>();
                                            commentss.put("reviewId", reviewDocument.getId());
                                            commentss.put("Likes", 3);
                                            commentss.put("Comment", "it's good");
                                            reviewDocument.collection("comments").add(commentss).
                                                    addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                        @Override
                                                        public void onSuccess(DocumentReference commentDocument) {
                                                            Log.d(TAG, "DocumentSnapshot added with ID: " + commentDocument.getId());
                                                        }
                                                    }).
                                                    addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Log.w(TAG, "Error adding document", e);
                                                            System.out.println("mata");
                                                        }
                                                    });
                                        }
                                    }).
                                    addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.w(TAG, "Error adding document", e);
                                            System.out.println("mata");
                                        }
                                    });

                            System.out.println("Success");
                        }
                    }).
                    addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error adding document", e);
                            System.out.println("mata");
                        }
                    });
        }
    }

    public boolean isUserEmailVerified()
    {
        if(currentUser.getValue() != null)
        {
            return currentUser.getValue().isEmailVerified();
        }
        return false;
    }

    public void verifyEmail() {
        progressBar.setValue(true);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if(currentUser.getValue() != null)
                {
                    currentUser.getValue().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                Log.d(TAG, "Email successfully sent!");
                                authenticationMessage.postValue("Email successfully sent!");
                            }
                            else {
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
}