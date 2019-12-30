package com.example.studentify_android.DataAccess.dao;

import com.example.studentify_android.DataAccess.ApiClient;
import com.example.studentify_android.DataAccess.dao.Interface.IEventDAO;
import com.example.studentify_android.DataAccess.service.EventService;
import com.example.studentify_android.Model.Event;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Response;

public class EventDAO implements IEventDAO {

    @Override
    public Response<ArrayList<Event>> getAll() throws IOException {
        return ApiClient.getInstance().getRetrofit()
                .create(EventService.class)
                .getAll()
                .execute();
    }

    @Override
    public Response<Event> add(Event event) throws IOException {
        return ApiClient.getInstance().getRetrofit()
                .create(EventService.class)
                .add(event)
                .execute();
    }

    @Override
    public Response<Void> update(Event event) throws IOException {
        return ApiClient.getInstance().getRetrofit()
                .create(EventService.class)
                .update(event)
                .execute();
    }

    @Override
    public Response<Boolean> isOwner(int id) throws IOException {
        return ApiClient.getInstance().getRetrofit()
                .create(EventService.class)
                .isOwner(id)
                .execute();
    }

    @Override
    public Response<Void> delete(int id) throws IOException {
        return ApiClient.getInstance().getRetrofit()
                .create(EventService.class)
                .delete(id)
                .execute();
    }
}
