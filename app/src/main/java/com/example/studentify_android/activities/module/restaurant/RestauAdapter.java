package com.example.studentify_android.activities.module.restaurant;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentify_android.model.Restaurant;
import com.example.studentify_android.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;

public class RestauAdapter extends RecyclerView.Adapter<RestauAdapter.RestauViewHolder> {

    private ArrayList<Restaurant> mRestauItems;
    private ArrayList<Restaurant> arraylist;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }


    public static class RestauViewHolder extends RecyclerView.ViewHolder {

        ImageView mImageView;
        TextView mTextView1;
        TextView mTextView2;

        RestauViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.imageView);
            mTextView1 = itemView.findViewById(R.id.textView1);
            mTextView2 = itemView.findViewById(R.id.textView2);

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

    public RestauAdapter(ArrayList<Restaurant> RestauItems) {
        mRestauItems = RestauItems;
        arraylist = new ArrayList<>(mRestauItems);

    }

    @NonNull
    @Override
    public RestauViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.restau_item, parent, false);
        return new RestauViewHolder(v, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RestauViewHolder holder, int position) {
        Restaurant currentItem = mRestauItems.get(position);

        //La variable "position" correspond à la position de l'item dans l'arrayList
        //On récupère donc l'item et on met à jour les infos

        Picasso.get().load(currentItem.getPicture()).into(holder.mImageView);
        holder.mTextView1.setText(currentItem.getName());
        holder.mTextView2.setText(currentItem.getType());
    }

    @Override
    public int getItemCount() {
        return mRestauItems.size();
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        mRestauItems.clear();
        if (charText.length() == 0) {
            mRestauItems.addAll(arraylist);
        }
        else
        {
            for (Restaurant wp : arraylist)
            {
                if (wp.getName().toLowerCase(Locale.getDefault()).contains(charText))
                {
                    mRestauItems.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}
