package com.example.studentify_android.dataAccess.service;

import com.example.studentify_android.model.Restaurant;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RestauService {

    @GET("/Restaurant/")
    Call<ArrayList<Restaurant>> getAll();

    @GET("/Restaurant/note/{id}")
    Call<Float> getRatingForOne(@Path("id") int id);

    @GET("/Restaurant/{id}")
    Call<Restaurant> getOne(@Path("id") int id);
}
