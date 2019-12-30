package com.example.studentify_android.service;

import com.example.studentify_android.MyApp;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

public class GoogleService {


    public static GoogleSignInAccount getAccount() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        GoogleSignInClient googleClient = GoogleSignIn.getClient(MyApp.getInstance(), gso);
        return GoogleSignIn.getLastSignedInAccount(MyApp.getInstance());
    }

    public static GoogleSignInClient getClient() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        return GoogleSignIn.getClient(MyApp.getInstance(), gso);
    }
}
