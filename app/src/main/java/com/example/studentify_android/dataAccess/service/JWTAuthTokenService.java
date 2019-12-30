package com.example.studentify_android.dataAccess.service;

import com.example.studentify_android.model.form.UserLogin;
import com.example.studentify_android.model.JWTAuthToken;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface JWTAuthTokenService {
    @POST("/Jwt/")
    Call<JWTAuthToken> getJwtAuthToken(@Body UserLogin userLogin);
}
