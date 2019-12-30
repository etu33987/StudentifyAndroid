package com.example.studentify_android.dataAccess.dao;

import com.example.studentify_android.dataAccess.ApiClient;
import com.example.studentify_android.dataAccess.dao.Interface.IReviewDAO;
import com.example.studentify_android.dataAccess.service.ReviewService;
import com.example.studentify_android.model.form.ReviewAdd;
import com.example.studentify_android.model.Review;

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
