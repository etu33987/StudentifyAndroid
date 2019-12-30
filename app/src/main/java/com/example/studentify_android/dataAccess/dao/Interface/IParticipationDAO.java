package com.example.studentify_android.dataAccess.dao.Interface;

import com.example.studentify_android.model.Participation;

import java.io.IOException;

import retrofit2.Response;

public interface IParticipationDAO {

    Response<Boolean> getUserPartiFromEvent(String userId, int eventId) throws IOException;

    Response<Boolean> deleteParticipation(int eventId) throws IOException;

    Response<Participation> addParticipation(Participation participation) throws IOException;
}
