package com.example.studentify_android.dataAccess.dao.Interface;

import com.example.studentify_android.model.form.UserLogin;
import com.example.studentify_android.model.JWTAuthToken;

import java.io.IOException;

import retrofit2.Response;

public interface IJWTAuthTokenDAO {
    Response<JWTAuthToken> getJwtAuthToken(UserLogin userLogin) throws IOException;
}
