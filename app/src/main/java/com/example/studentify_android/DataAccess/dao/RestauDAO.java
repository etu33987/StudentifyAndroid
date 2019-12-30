package com.example.studentify_android.DataAccess.dao;

import com.example.studentify_android.DataAccess.ApiClient;
import com.example.studentify_android.DataAccess.dao.Interface.IRestauDAO;
import com.example.studentify_android.DataAccess.service.RestauService;

import java.io.IOException;
import java.util.ArrayList;

import com.example.studentify_android.Model.Restaurant;

import retrofit2.Response;

public class RestauDAO implements IRestauDAO {

    @Override
    public Response<ArrayList<Restaurant>> getAll() throws IOException {
        return ApiClient.getInstance().getRetrofit()
                .create(RestauService.class)
                .getAll()
                .execute();
    }

    @Override
    public Response<Float> getRatingForOne(int id) throws IOException {
        return ApiClient.getInstance().getRetrofit()
                .create(RestauService.class)
                .getRatingForOne(id)
                .execute();
    }

    @Override
    public Response<Restaurant> getOne(int id) throws IOException {
        return ApiClient.getInstance().getRetrofit()
                .create(RestauService.class)
                .getOne(id)
                .execute();
    }
}
