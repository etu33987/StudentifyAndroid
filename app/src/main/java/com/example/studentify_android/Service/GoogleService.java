package com.example.studentify_android.Service;

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
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(MyApp.getInstance());
        return account;
    }

    public static GoogleSignInClient getClient() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        GoogleSignInClient googleClient = GoogleSignIn.getClient(MyApp.getInstance(), gso);
        return googleClient;
    }
}
