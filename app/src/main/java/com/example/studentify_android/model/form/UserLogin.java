package com.example.studentify_android.model.form;

public class UserLogin {

    private String eMail;
    private String password;

    public UserLogin(String email, String password) {
        eMail = email;
        this.password = password;
    }

    public UserLogin() {
    }

    public String getEmail() {
        return eMail;
    }

    public void setEmail(String email) {
        eMail = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
