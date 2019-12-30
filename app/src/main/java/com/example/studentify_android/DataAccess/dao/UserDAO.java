package com.example.studentify_android.DataAccess.dao;

import com.example.studentify_android.DataAccess.ApiClient;
import com.example.studentify_android.DataAccess.dao.Interface.IUserDAO;
import com.example.studentify_android.DataAccess.service.UserService;
import com.example.studentify_android.Model.Form.NewPassword;
import com.example.studentify_android.Model.User;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Response;

public class UserDAO implements IUserDAO {

    @Override
    public Response<ArrayList<User>> getAll() throws IOException {
        return ApiClient.getInstance().getRetrofit()
                .create(UserService.class)
                .getAll()
                .execute();
    }

    @Override
    public Response<User> getByEmailAndPassword(String email, String password) throws IOException {
        return ApiClient.getInstance().getRetrofit()
                .create(UserService.class)
                .getByEmailAndPassword(email, password)
                .execute();
    }

    @Override
    public Response<User> getById(String id) throws IOException {
        return ApiClient.getInstance().getRetrofit()
                .create(UserService.class)
                .getById(id)
                .execute();
    }

    @Override
    public Response<User> create(User user) throws IOException {
        return ApiClient.getInstance().getRetrofit()
                .create(UserService.class)
                .create(user)
                .execute();
    }

    @Override
    public Response<User> update(User user) throws IOException {
        return ApiClient.getInstance().getRetrofit()
                .create(UserService.class)
                .update(user)
                .execute();
    }

    @Override
    public Response<Void> updatePswd(NewPassword pswd) throws IOException {
        return ApiClient.getInstance().getRetrofit()
                .create(UserService.class)
                .updatePswd(pswd)
                .execute();
    }
}
