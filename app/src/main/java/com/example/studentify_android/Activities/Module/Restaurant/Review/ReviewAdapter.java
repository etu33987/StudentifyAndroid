package com.example.studentify_android.Activities.Module.Restaurant.Review;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.studentify_android.Activities.Module.Event.EventInfoActi;
import com.example.studentify_android.DataAccess.dao.Interface.IParticipationDAO;
import com.example.studentify_android.DataAccess.dao.Interface.IReviewDAO;
import com.example.studentify_android.DataAccess.dao.ParticipationDAO;
import com.example.studentify_android.DataAccess.dao.ReviewDAO;
import com.example.studentify_android.Model.Event;
import com.example.studentify_android.Model.Restaurant;
import com.example.studentify_android.Model.Review;
import com.example.studentify_android.MyApp;
import com.example.studentify_android.R;
import com.example.studentify_android.Service.AuthSessionService;
import com.example.studentify_android.Service.CheckIntenetConnection;
import com.example.studentify_android.Utils.ObjectWrapperForBinder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Response;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private ArrayList<Review> mReviewItems;
    private ArrayList<Review> arraylist;
    private int itemPosition;
    private Activity activity;

    public static class ReviewViewHolder extends RecyclerView.ViewHolder {
    
        public ImageView mImageView;
        public RatingBar mRating;
        public TextView mDesc;
        public TextView mAuthor;
        public ImageButton mDeleteButton;
    
        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.reviewIcon);
            mRating = itemView.findViewById(R.id.reviewRating);
            mDesc = itemView.findViewById(R.id.reviewDesc);
            mAuthor = itemView.findViewById(R.id.reviewAuthor);
            mDeleteButton = itemView.findViewById(R.id.deleteReviewButton);
        }
    }
    
    public ReviewAdapter(ArrayList<Review> reviews, Activity activity) {
        mReviewItems = reviews;
        arraylist = new ArrayList<>(mReviewItems);
        this.activity = activity;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item, parent, false);
        ReviewViewHolder restauViewHolder = new ReviewViewHolder(v);
        return restauViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        Review currentItem = mReviewItems.get(position);

        //La variable "position" correspond à la position de l'item dans l'arrayList
        //On récupère donc l'item et on met à jour les infos

        Picasso.get().load(currentItem.getUserNavigation().getPicture()).into(holder.mImageView);
        holder.mRating.setRating(currentItem.getNote());
        holder.mDesc.setText(currentItem.getDescription());
        holder.mAuthor.setText(currentItem.getUserNavigation().getName() + " " + currentItem.getUserNavigation().getFirstname());

        // Rend le button uniquement visible par son auteur et ajout son action
        if(currentItem.getUserNavigation().getId().equals(AuthSessionService.getUserId(MyApp.getInstance()))) {
            holder.mDeleteButton.setVisibility(View.VISIBLE);
            holder.mDeleteButton.setOnClickListener(view -> {

                if (CheckIntenetConnection.checkConnection(MyApp.getInstance())) {
                    itemPosition = position;
                    new DeleteReviewAsync().execute(currentItem);
                }else {
                    Toast.makeText(MyApp.getInstance(), "Vous n'êtes pas connecté à internet", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mReviewItems.size();
    }

    private class DeleteReviewAsync extends AsyncTask<Review, Void, Boolean> {
        private IReviewDAO reviewDAO = new ReviewDAO();

        @Override
        protected Boolean doInBackground(Review... reviews) {

            try {
                Response<Boolean> response = reviewDAO.delete(reviews[0].getId());
                if (response.isSuccessful() && response.code() == 204) {
                    return true;
                }else {
                    activity.runOnUiThread(() -> Toast.makeText(MyApp.getInstance(), "Erreur : " + response.code(), Toast.LENGTH_LONG).show());
                    activity.runOnUiThread(() -> Toast.makeText(MyApp.getInstance(), "Échec : " + response.message(), Toast.LENGTH_LONG).show());
                }
            }catch (Exception e)
            {
                activity.runOnUiThread(() -> e.printStackTrace());
            }
            return false;
        }
        protected void onPostExecute(Boolean deleteSuccess) {
            if(deleteSuccess) {

                mReviewItems.remove(itemPosition);
                notifyItemRemoved(itemPosition);
                activity.runOnUiThread(() ->  Toast.makeText(MyApp.getInstance(), "Avis supprimé", Toast.LENGTH_LONG));

            } else {
                activity.runOnUiThread(() -> Toast.makeText(MyApp.getInstance(), "Echec de la suppression", Toast.LENGTH_LONG).show());
            }
        }
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        mReviewItems.clear();
        if (charText.length() == 0) {
            mReviewItems.addAll(arraylist);
        }
        else
        {
            for (Review wp : arraylist)
            {
                if (wp.getDescription().toLowerCase(Locale.getDefault()).contains(charText))
                {
                    mReviewItems.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }


}
