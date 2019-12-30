package com.example.studentify_android.Service;

import android.content.Context;

public class AuthSessionService {
    private static final AuthSessionService instance = new AuthSessionService();

    private static final String AUTH_SESSION = "AUTH_SESSION";
    private static final String JWT_AUTH_TOKEN = "JWT_AUTH_TOKEN";
    private static final String USER_ID = "USER_ID";
    private static final String USER_PICTURE = "USER_PICTURE";

    private AuthSessionService() {
    }

    public static AuthSessionService getInstance() {
        return instance;
    }

    public static void setToken(Context context, String authToken) {
        context
                .getSharedPreferences(AUTH_SESSION, Context.MODE_PRIVATE)
                .edit()
                .putString(JWT_AUTH_TOKEN, authToken)
                .apply();
    }

    public static String getToken(Context context) {
        return context.getSharedPreferences(AUTH_SESSION, Context.MODE_PRIVATE)
                .getString(JWT_AUTH_TOKEN, "");
    }

    public static String getUserId(Context context) {
        return context.getSharedPreferences(AUTH_SESSION, Context.MODE_PRIVATE)
                .getString(USER_ID, "");
    }

    public static String getUserPicture(Context context) {
        return context.getSharedPreferences(AUTH_SESSION, Context.MODE_PRIVATE)
                .getString(USER_PICTURE, "");
    }

    public static void setUserId(Context context, String userId) {
        context
                .getSharedPreferences(AUTH_SESSION, Context.MODE_PRIVATE)
                .edit()
                .putString(USER_ID, userId)
                .apply();
    }

    public static void setUserPicture(Context context, String picture) {
        context
                .getSharedPreferences(AUTH_SESSION, Context.MODE_PRIVATE)
                .edit()
                .putString(USER_PICTURE, picture)
                .apply();
    }

    public static void disconnectUser(Context context) {
        context.getSharedPreferences(AUTH_SESSION, Context.MODE_PRIVATE)
                .edit()
                .remove(JWT_AUTH_TOKEN)
                .remove(USER_ID)
                .apply();
    }
}
