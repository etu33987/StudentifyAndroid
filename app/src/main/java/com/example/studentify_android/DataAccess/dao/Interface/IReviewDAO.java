package com.example.studentify_android.DataAccess.dao.Interface;

import com.example.studentify_android.Model.Form.ReviewAdd;
import com.example.studentify_android.Model.Restaurant;
import com.example.studentify_android.Model.Review;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Response;

public interface IReviewDAO {
    Response<ArrayList<Review>> getAllForOne(int id) throws IOException;

    Response<ReviewAdd> add(ReviewAdd reviewAdd) throws IOException;

    Response<Boolean> delete(int id) throws IOException;
}
