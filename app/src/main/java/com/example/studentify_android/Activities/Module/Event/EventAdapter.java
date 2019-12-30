package com.example.studentify_android.Activities.Module.Event;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentify_android.Activities.Module.Restaurant.Review.ReviewAdapter;
import com.example.studentify_android.DataAccess.dao.EventDAO;
import com.example.studentify_android.DataAccess.dao.Interface.IEventDAO;
import com.example.studentify_android.DataAccess.dao.Interface.IReviewDAO;
import com.example.studentify_android.DataAccess.dao.ReviewDAO;
import com.example.studentify_android.Model.Event;
import com.example.studentify_android.Model.Restaurant;
import com.example.studentify_android.Model.Review;
import com.example.studentify_android.MyApp;
import com.example.studentify_android.R;
import com.example.studentify_android.Service.AuthSessionService;
import com.example.studentify_android.Service.CheckIntenetConnection;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Response;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    private ArrayList<Event> mEventItems;
    private ArrayList<Event> arraylist;
    private OnItemClickListener mListener;
    private Activity activity;
    private int itemPosition;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder {

        public ImageView mImageView;
        public TextView mTextView1;
        public TextView mTextView2;
        public ImageButton mDeleteButton;

        public EventViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

            mImageView = itemView.findViewById(R.id.imageView);
            mTextView1 = itemView.findViewById(R.id.textView1);
            mTextView2 = itemView.findViewById(R.id.textView2);
            mDeleteButton = itemView.findViewById(R.id.deleteEventButton);

            itemView.setOnClickListener(view -> {
                if(listener != null) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(position);
                    }
                }
            });
        }
    }

    public EventAdapter(ArrayList<Event> eventItems, Activity activity) {
        mEventItems = eventItems;
        arraylist = new ArrayList<>(mEventItems);
        this.activity = activity;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_item, parent, false);
        EventViewHolder eventViewHolder = new EventViewHolder(v, mListener);
        return eventViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        Event currentItem = mEventItems.get(position);


        //La variable "position" correspond à la position de l'item dans l'arrayList
        //On récupère donc l'item et on met à jour les infos
        Picasso.get().load(currentItem.getAuthorNavigation().getPicture()).into(holder.mImageView);
        holder.mTextView1.setText(currentItem.getTitle());
        holder.mTextView2.setText(currentItem.getAuthorNavigation().getName() + " " + currentItem.getAuthorNavigation().getFirstname());

        // Rend le button uniquement visible par son auteur et ajout son action
        if(currentItem.getAuthorNavigation().getId().equals(AuthSessionService.getUserId(MyApp.getInstance()))) {
            holder.mDeleteButton.setVisibility(View.VISIBLE);
            holder.mDeleteButton.setOnClickListener(view -> {

                if (CheckIntenetConnection.checkConnection(MyApp.getInstance())) {
                    new DeleteEventAsync().execute(currentItem);
                    itemPosition = position;
                }else {
                    Toast.makeText(MyApp.getInstance(), R.string.error_no_connection, Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mEventItems.size();
    }

    private class DeleteEventAsync extends AsyncTask<Event, Void, Boolean> {
        private IEventDAO eventDAO = new EventDAO();

        @Override
        protected Boolean doInBackground(Event... events) {

            try {
                Response<Void> response = eventDAO.delete(events[0].getId());
                if (response.isSuccessful() && response.code() == 204) {
                    return true;
                }else {
                    activity.runOnUiThread(() -> {
                    Toast.makeText(MyApp.getInstance(), "Erreur " + response.code(), Toast.LENGTH_LONG).show();
                    Toast.makeText(MyApp.getInstance(), "Erreur " + response.message(), Toast.LENGTH_LONG).show();
                    });
                }
            }catch (Exception e)
            {

                e.printStackTrace();

            }
            return false;
        }
        protected void onPostExecute(Boolean deleteSuccess) {
            if(deleteSuccess) {

                mEventItems.remove(itemPosition);
                notifyItemRemoved(itemPosition);
                activity.runOnUiThread(() -> Toast.makeText(MyApp.getInstance(), "Supression réussie", Toast.LENGTH_LONG));

            } else {
                activity.runOnUiThread(() -> Toast.makeText(MyApp.getInstance(), "Echec de la suppression", Toast.LENGTH_LONG).show());

            }
        }
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        mEventItems.clear();
        if (charText.length() == 0) {
            mEventItems.addAll(arraylist);
        }
        else
        {
            for (Event wp : arraylist)
            {
                if (wp.getTitle().toLowerCase(Locale.getDefault()).contains(charText))
                {
                    mEventItems.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

}
