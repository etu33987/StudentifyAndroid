package com.example.studentify_android.DataAccess.dao.Interface;

import com.example.studentify_android.Model.Restaurant;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Response;

public interface IRestauDAO {
    Response<ArrayList<Restaurant>> getAll() throws IOException;

    Response<Float> getRatingForOne(int id) throws IOException;

    Response<Restaurant> getOne(int id) throws IOException;
}
