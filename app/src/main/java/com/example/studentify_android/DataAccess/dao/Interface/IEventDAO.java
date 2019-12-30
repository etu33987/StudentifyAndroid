package com.example.studentify_android.DataAccess.dao.Interface;

import com.example.studentify_android.Model.Event;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Response;

public interface IEventDAO {
    Response<ArrayList<Event>> getAll() throws IOException;
    Response<Event> add(Event event) throws IOException;
    Response<Boolean> isOwner(int id) throws IOException;
    Response<Void> update(Event event) throws IOException;
    Response<Void> delete(int id) throws IOException;
}
