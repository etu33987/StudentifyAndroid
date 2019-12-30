package com.example.studentify_android;

import android.app.Application;
import android.content.Intent;
import android.widget.Toast;

import com.example.studentify_android.Activities.StartView.StartApp;
import com.example.studentify_android.Service.AuthSessionService;
import com.example.studentify_android.Service.GoogleService;

public class MyApp extends Application {
    private static MyApp instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static MyApp getInstance() {
        return instance;
    }
}
