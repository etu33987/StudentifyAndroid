package com.example.studentify_android.DataAccess.dao;

import com.example.studentify_android.DataAccess.ApiClient;
import com.example.studentify_android.DataAccess.dao.Interface.IJWTAuthTokenDAO;
import com.example.studentify_android.DataAccess.service.JWTAuthTokenService;
import com.example.studentify_android.Model.Form.UserLogin;
import com.example.studentify_android.Model.JWTAuthToken;

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
