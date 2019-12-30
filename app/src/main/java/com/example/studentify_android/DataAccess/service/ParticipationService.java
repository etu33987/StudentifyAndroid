package com.example.studentify_android.DataAccess.service;

import com.example.studentify_android.Model.Participation;
import com.example.studentify_android.Model.Restaurant;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ParticipationService {

    @GET("/Participation/userParticipation/{userId}/{eventId}")
    Call<Boolean> getUserPartiFromEvent(@Path("userId") String userId, @Path("eventId") int eventId);

    @DELETE("/Participation/{eventId}")
    Call<Boolean> deleteParticipation(@Path("eventId") int eventId);

    @POST("/Participation/")
    Call<Participation> addParticipation(@Body Participation participation);
}
