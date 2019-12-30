package com.example.studentify_android.Model;

public class JWTAuthToken {

    private String access_token;

    private Integer expires_in;

    private String userId;

    public JWTAuthToken() {}

    public String getAccessToken() {
        return access_token;
    }

    public void setAccessToken(String access_token) {
        this.access_token = access_token;
    }

    public Integer getExpiresIn() {
        return expires_in;
    }

    public void setExpiresIn(Integer expires_in) {
        this.expires_in = expires_in;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
