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
    private final UserDAO userDAO;
    private ArrayList<Review> reviewsArrayList = new ArrayList<>();
    private ArrayList<Hotel> hotelsArrayList = new ArrayList<>();

    //Firebase Database
    private FirebaseFirestore firebaseDatabase;

    private MutableLiveData<Boolean> getProgressBar = new MutableLiveData<>(false);
    private MutableLiveData<ArrayList<Review>> reviewsLiveData = new MutableLiveData<>(new ArrayList<>());
    private MutableLiveData<ArrayList<Hotel>> hotelsLiveData = new MutableLiveData<>(new ArrayList<>());
    private MutableLiveData<Hotel> hotel = new MutableLiveData<>(null);
    private MutableLiveData<Review> review = new MutableLiveData<>();
    private MutableLiveData<String> hotelNameLiveData = new MutableLiveData<>(null);
    private MutableLiveData<Boolean> isLikePressed = new MutableLiveData<>(false);

    public ReviewDAO(Application app) {
        firebaseDatabase = FirebaseFirestore.getInstance();
        userDAO = UserDAO.getInstance(app);

        getHotels();
        getReviews();
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

    public MutableLiveData<ArrayList<Review>> getReviewsLiveData() {
        return reviewsLiveData;
    }

    public MutableLiveData<ArrayList<Hotel>> getHotelsLiveData() {
        return hotelsLiveData;
    }

    public MutableLiveData<Hotel> getHotelLiveData()
    {
        return hotel;
    }

    public MutableLiveData<String> getHotelNameLiveData()
    {
        return hotelNameLiveData;
    }

    public void setHotelNameLiveData(String hotelName) {
        this.hotelNameLiveData.postValue(hotelName);
    }

    public MutableLiveData<Review> getReviewLiveData() {
        return review;
    }

    public MutableLiveData<Boolean> getIsLikePressed() {
        return isLikePressed;
    }

    public void setIsLikePressed(boolean statement) {
        this.isLikePressed.postValue(statement);
    }

    public void setReviewLiveData(Review review) {
        this.review.setValue(review);
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
                        isLikePressed.postValue(false);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing the user", e);
                    }
                });


    }

    public void getHotel(String name) {
        DocumentReference docRef = firebaseDatabase.collection("hotels").document(name);
        Source source = Source.CACHE;
        docRef.get(source).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    Hotel hotelToBeSet = task.getResult().toObject(Hotel.class);
                    hotel.postValue(hotelToBeSet);
                } else {
                    hotel.postValue(null);
                    Log.e(TAG, task.getException().getMessage());
                }
            }
        });

    }

//    //prob not using it
//    public Review getReview(String hotelName, String userUID) {
//        ArrayList<Review> reviews = getReviews();
//        for(Review reviewItem : reviews)
//        {
//            if(reviewItem.getHotelName().equals(hotelName) && reviewItem.getUserUID().equals(userUID))
//            {
//                return reviewItem;
//            }
//        }
//        return null;
//    }

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

    public void removeHotel(Hotel hotel)
    {
        firebaseDatabase.collection("hotels").document(hotel.getName())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "Hotel successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error deleting document", e);
                    }
                });
    }

    public void getHotels()
    {
        firebaseDatabase.collection("hotels")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Hotel hotel = document.toObject(Hotel.class);
                            hotelsArrayList.add(hotel);

//                            firebaseDatabase.collection("hotels").document(hotel.getName()).
//                                    get().addOnCompleteListener(taskListener -> {
//                                        if(taskListener.isSuccessful())
//                                        {
//                                            reviewsArrayList.addAll(hotel.getReviews());
//                                            reviewsLiveData.postValue();
//                                        }
//                                    });
                        }
                        hotelsLiveData.postValue(hotelsArrayList);
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                });
    }

    public void getReviews() {
        getHotels();
        firebaseDatabase.collection("hotels")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Hotel hotel = document.toObject(Hotel.class);
                            hotelsArrayList.add(hotel);
                            hotelsLiveData.postValue(hotelsArrayList);
                            firebaseDatabase.collection("hotels").document(hotel.getName()).
                                    get().addOnCompleteListener(taskListener -> {
                                        if(taskListener.isSuccessful())
                                        {
                                            reviewsArrayList.addAll(hotel.getReviews());
                                            reviewsLiveData.postValue(reviewsArrayList);
                                        }
                                    });
                        }
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                });
    }
}
