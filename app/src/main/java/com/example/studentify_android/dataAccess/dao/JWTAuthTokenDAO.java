package com.example.studentify_android.dataAccess.dao;

import com.example.studentify_android.dataAccess.ApiClient;
import com.example.studentify_android.dataAccess.dao.Interface.IJWTAuthTokenDAO;
import com.example.studentify_android.dataAccess.service.JWTAuthTokenService;
import com.example.studentify_android.model.form.UserLogin;
import com.example.studentify_android.model.JWTAuthToken;

import java.io.IOException;

import retrofit2.Response;

public class JWTAuthTokenDAO implements IJWTAuthTokenDAO {

    @Override
    public Response<JWTAuthToken> getJwtAuthToken(UserLogin userLogin) throws IOException {
        return ApiClient.getInstance().getRetrofit()
                .create(JWTAuthTokenService.class)
                .getJwtAuthToken(userLogin)
                .execute();
    }
}
