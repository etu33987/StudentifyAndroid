package com.example.studentify_android.DataAccess.service;

import com.example.studentify_android.Model.Form.UserLogin;
import com.example.studentify_android.Model.JWTAuthToken;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface JWTAuthTokenService {
    @POST("/Jwt/")
    Call<JWTAuthToken> getJwtAuthToken(@Body UserLogin userLogin);
}
