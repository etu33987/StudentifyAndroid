package com.example.studentify_android.dataAccess.dao.Interface;

import com.example.studentify_android.model.form.NewPassword;
import com.example.studentify_android.model.User;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Response;

public interface IUserDAO {
    Response<ArrayList<User>> getAll() throws IOException;

    Response<User> getByEmailAndPassword(String email, String password) throws IOException;

    Response<User> getById(String id) throws IOException;

    Response<User> create(User user) throws IOException;

    Response<User> update(User user) throws IOException;

    Response<Void> updatePswd(NewPassword pswd) throws IOException;
}
