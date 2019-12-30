package com.example.studentify_android.DataAccess.dao.Interface;

import com.example.studentify_android.Model.Form.UserLogin;
import com.example.studentify_android.Model.JWTAuthToken;

import java.io.IOException;

import retrofit2.Response;

public interface IJWTAuthTokenDAO {
    Response<JWTAuthToken> getJwtAuthToken(UserLogin userLogin) throws IOException;
}
