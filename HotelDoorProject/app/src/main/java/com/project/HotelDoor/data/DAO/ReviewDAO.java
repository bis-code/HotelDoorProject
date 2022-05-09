package com.project.HotelDoor.data.DAO;

import static android.content.ContentValues.TAG;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;
import com.project.HotelDoor.R;
import com.project.HotelDoor.data.Hotel;
import com.project.HotelDoor.data.Review;
import com.project.HotelDoor.data.ReviewAdapter;
import com.project.HotelDoor.data.User;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;


public class ReviewDAO {
    private static ReviewDAO instance;
    private final Application app;
    private boolean statement;
    private final UserDAO userDAO;
    private Hotel hotel = null;
    private ArrayList<Review> reviewsArrayList;

    //Firebase Database
    private FirebaseFirestore firebaseDatabase;

    private MutableLiveData<Boolean> getProgressBar = new MutableLiveData<>(false);


    public ReviewDAO(Application app) {
        this.app = app;
        firebaseDatabase = FirebaseFirestore.getInstance();
        userDAO = UserDAO.getInstance(app);
        reviewsArrayList = new ArrayList<>();
    }

    public static ReviewDAO getInstance(Application application) {
        if (instance == null) {
            instance = new ReviewDAO(application);
        }
        return instance;
    }

    public MutableLiveData<Boolean> getProgressBar() {
        return getProgressBar;
    }

    public void postHotel(Hotel hotel) {
        Map<String, Object> hotelMap = new HashMap<>();
        hotelMap.put("name", hotel.getName());
        hotelMap.put("address", hotel.getAddress());
        hotelMap.put("reviews", FieldValue.arrayUnion(hotel.getReviews().toArray()));

        firebaseDatabase.collection("hotels").document(hotel.getName())
                .set(hotelMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "Hotel inserted successfully!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing the user", e);
                    }
                });
    }

    public Hotel getHotel(String name) {
        DocumentReference docRef = firebaseDatabase.collection("hotels").document(name);
        Source source = Source.CACHE;
        docRef.get(source).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    Hotel returnedHotel = task.getResult().toObject(Hotel.class);
                    hotel = returnedHotel;
                } else {
                    hotel = null;
                    Log.e(TAG, task.getException().getMessage());
                }
            }
        });
        return hotel;
    }

    public void updateHotel(Hotel hotel) {
        DocumentReference hotelDocument = firebaseDatabase.collection("hotels").document(hotel.getName());
        hotelDocument
                .update(
                        "reviews", FieldValue.arrayUnion(hotel.getReviews().toArray())
                )
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "Document Hotel with " + hotelDocument.getId() + " has been updated");
                        userDAO.setAuthenticationMessage(true, "Information for Hotel has been updated!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error updating user document " + hotelDocument.getId(), e);
                        userDAO.setAuthenticationMessage(true, "Information couldn't be updated.");
                    }
                });
    }

//    public boolean hotelAlreadyExisting(String name) {
//        DocumentReference docRef = firebaseDatabase.collection("hotels").document(name);
//        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//            @Override
//            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                if(documentSnapshot != null)
//                {
//                    statement = true;
//                }
//                else {
//                    statement = false;
//                }
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                statement = false;
//                Log.e(TAG, e.getMessage());
//            }
//        });
//        return statement;
////    }

    public void postReview(Review review, Hotel hotel) {

        Map<String, Object> reviewMap = new HashMap<>();
        if (review.getUserUID() != null || review.getRate() >= 0.0 || review.getDescription() != null) {
            reviewMap.put("userUID", review.getUserUID());
            reviewMap.put("description", review.getDescription());
            reviewMap.put("rate", review.getRate());
            reviewMap.put("likes", review.getLikes());
            int position = hotel.getReviews().size() - 1;
            firebaseDatabase.collection("reviews").document(hotel.getName() + "[" + position + "] " + review.getUserUID())
                    .set(reviewMap)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "Review created successfully!");
                            userDAO.setAuthenticationMessage(true, "Review created successfully!");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error writing the review", e);
                            userDAO.setAuthenticationMessage(true, "Error writing the review");
                        }
                    });
        }
    }

    public ArrayList<Review> getReviews() {
        firebaseDatabase.collection("reviews")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Review review = document.toObject(Review.class);
                                reviewsArrayList.add(review);
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
        return reviewsArrayList;
    }
}
