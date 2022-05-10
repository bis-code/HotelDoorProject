package com.project.HotelDoor.data;

import android.app.Application;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.HotelDoor.R;
import com.project.HotelDoor.data.DAO.UserDAO;
import com.project.HotelDoor.data.Review;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    private ArrayList<Review> reviewArrayList;
    private Context context;
    private UserRepository userRepository;
    private ReviewRepository reviewRepository;


    public ReviewAdapter(ArrayList<Review> instaModalArrayList, Context context, Application app) {
        this.reviewArrayList = instaModalArrayList;
        this.context = context;
        this.userRepository = UserRepository.getInstance(app);
        this.reviewRepository = ReviewRepository.getInstance(app);
    }

    @NonNull
    @Override
    public ReviewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item, parent, false);

        return new ReviewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewAdapter.ViewHolder holder, int position) {

        Review modal = reviewArrayList.get(position);
        User user = userRepository.getUserModal(modal.getUserUID());
        Hotel hotel = reviewRepository.getHotel(modal.getHotelName() + "[" + position + "]" + user.getUid());

        holder.authorTV.setText(user.getUserName());
//        if (modal.getMedia_type().equals("IMAGE")) {
//            Picasso.get().load(modal.getMedia_url()).into(holder.postIV);
//        }
        holder.desctv.setText(modal.getDescription());
        holder.likeTV.setText("" + modal.getLikes() + " likes");
        holder.hotelName.setText("About " + hotel.getName() + " hotel");
//        Picasso.get().load(modal.getAuthor_url()).into(holder.authorIV);
    }

    @Override
    public int getItemCount() {
        return reviewArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView authorIV;
        private TextView authorTV;
        private TextView likeTV;
        private TextView desctv;
        private TextView hotelName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            authorIV = itemView.findViewById(R.id.imageAuthor);
            authorTV = itemView.findViewById(R.id.userNameAuthor);
            likeTV = itemView.findViewById(R.id.likesReview);
            desctv = itemView.findViewById(R.id.reviewDescription);
            hotelName = itemView.findViewById(R.id.hotelName);
        }
    }
}
