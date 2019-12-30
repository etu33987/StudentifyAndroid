package com.example.studentify_android.DataAccess.dao;

import com.example.studentify_android.DataAccess.ApiClient;
import com.example.studentify_android.DataAccess.dao.Interface.IParticipationDAO;
import com.example.studentify_android.DataAccess.service.ParticipationService;
import com.example.studentify_android.DataAccess.service.RestauService;
import com.example.studentify_android.Model.Participation;
import com.example.studentify_android.Model.Restaurant;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Response;

public class ParticipationDAO implements IParticipationDAO {

    @Override
    public Response<Boolean> getUserPartiFromEvent(String userId, int eventId) throws IOException {
        return ApiClient.getInstance().getRetrofit()
                .create(ParticipationService.class)
                .getUserPartiFromEvent(userId, eventId)
                .execute();
    }

    @Override
    public Response<Boolean> deleteParticipation(int eventId) throws IOException {
        return ApiClient.getInstance().getRetrofit()
                .create(ParticipationService.class)
                .deleteParticipation(eventId)
                .execute();
    }

    @Override
    public Response<Participation> addParticipation(Participation participation) throws IOException {
        return ApiClient.getInstance().getRetrofit()
                .create(ParticipationService.class)
                .addParticipation(participation)
                .execute();
    }
}
