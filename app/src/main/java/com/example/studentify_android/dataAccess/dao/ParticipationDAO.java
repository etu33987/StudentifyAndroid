package com.example.studentify_android.dataAccess.dao;

import com.example.studentify_android.dataAccess.ApiClient;
import com.example.studentify_android.dataAccess.dao.Interface.IParticipationDAO;
import com.example.studentify_android.dataAccess.service.ParticipationService;
import com.example.studentify_android.model.Participation;

import java.io.IOException;

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
