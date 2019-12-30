package com.example.studentify_android.dataAccess.service;

import com.example.studentify_android.model.form.NewPassword;
import com.example.studentify_android.model.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserService {

    @GET("/User/")
    Call<ArrayList<User>> getAll();

    @GET("/User/{email}/{password}")
    Call<User> getByEmailAndPassword(@Path("email") String email, @Path("password") String password);

    @GET("/User/id/{id}")
    Call<User> getById(@Path("id") String id);

    @POST("/User/")
    Call<User> create(@Body User user);

    @PUT("/User/")
    Call<User> update(@Body User user);

    @PUT("/User/pswd")
    Call<Void> updatePswd(@Body NewPassword pswd);
}
