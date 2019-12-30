package com.example.studentify_android.DataAccess.service;

import com.example.studentify_android.Model.Event;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface EventService {

    @GET("/Event/")
    Call<ArrayList<Event>> getAll();

    @POST("/Event/")
    Call<Event> add(@Body Event event);

    @PUT("/Event/")
    Call<Void> update(@Body Event event);

    @GET("/Event/isMyEvent/{id}")
    Call<Boolean> isOwner(@Path("id")int id);

    @DELETE("/Event/{id}")
    Call<Void> delete(@Path("id")int id);
}
