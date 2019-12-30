package com.example.studentify_android.DataAccess.service;

import com.example.studentify_android.Model.Form.ReviewAdd;
import com.example.studentify_android.Model.Restaurant;
import com.example.studentify_android.Model.Review;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ReviewService {

    @GET("/Review/resto/{id}")
    Call<ArrayList<Review>> getAllForOne(@Path("id")int id);

    @POST("/Review/")
    Call<ReviewAdd> add(@Body ReviewAdd reviewAdd);

    @DELETE("/Review/{id}")
    Call<Boolean> delete(@Path("id") int id);
}
