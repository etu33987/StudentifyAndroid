package com.example.studentify_android.DataAccess.dao;

import com.example.studentify_android.DataAccess.ApiClient;
import com.example.studentify_android.DataAccess.dao.Interface.IReviewDAO;
import com.example.studentify_android.DataAccess.service.RestauService;
import com.example.studentify_android.DataAccess.service.ReviewService;
import com.example.studentify_android.Model.Form.ReviewAdd;
import com.example.studentify_android.Model.Restaurant;
import com.example.studentify_android.Model.Review;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Response;

public class ReviewDAO implements IReviewDAO {
    @Override
    public Response<ArrayList<Review>> getAllForOne(int id) throws IOException {
        return ApiClient.getInstance().getRetrofit()
                .create(ReviewService.class)
                .getAllForOne(id)
                .execute();
    }

    @Override
    public Response<ReviewAdd> add(ReviewAdd reviewAdd) throws IOException {
        return ApiClient.getInstance().getRetrofit()
                .create(ReviewService.class)
                .add(reviewAdd)
                .execute();
    }

    @Override
    public Response<Boolean> delete(int id) throws IOException {
        return ApiClient.getInstance().getRetrofit()
                .create(ReviewService.class)
                .delete(id)
                .execute();
    }
}
