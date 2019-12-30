package com.example.studentify_android.activities.startView;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.example.studentify_android.dataAccess.dao.JWTAuthTokenDAO;
import com.example.studentify_android.dataAccess.dao.UserDAO;
import com.example.studentify_android.activities.mainMenuView.MainMenuActi;
import com.example.studentify_android.model.form.UserLogin;
import com.example.studentify_android.model.JWTAuthToken;
import com.example.studentify_android.model.User;
import com.example.studentify_android.R;
import com.example.studentify_android.service.AuthSessionService;
import com.example.studentify_android.service.CheckIntenetConnection;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Response;

public class SignInActi extends AppCompatActivity {

    @BindView(R.id.user_editText)
    EditText email;

    @BindView(R.id.passwordEditText)
    EditText password;

    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);
        activity = this;

    }

    @OnClick(R.id.connect_email_register_button) void submit() {


        if(!email.getText().toString().isEmpty() && !password.getText().toString().isEmpty()){


            if(email.getText().toString().matches(".+@.+\\..+")){

                UserLogin userLogin = new UserLogin();
                userLogin.setEmail(email.getText().toString());
                userLogin.setPassword(password.getText().toString());
                if (CheckIntenetConnection.checkConnection(this)) {
                    new LoginUserAsyncTask().execute(userLogin);
                }else {
                    Toast.makeText(this, "Vous n'êtes pas connecté à internet", Toast.LENGTH_LONG).show();
                }



            }else {
                email.setError(getResources().getString(R.string.error_matche_email));
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_matche_email), Toast.LENGTH_LONG).show();
            }

        }else {
            password.setError(getResources().getString(R.string.form_empty_field));
            email.setError(getResources().getString(R.string.form_empty_field));
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.form_empty_field), Toast.LENGTH_LONG).show();
        }
    }

    private class LoginUserAsyncTask extends AsyncTask<UserLogin, Void, JWTAuthToken> {

        @Override
        protected JWTAuthToken doInBackground(UserLogin... userLogins) {
            try {

                Response<JWTAuthToken> response = new JWTAuthTokenDAO().getJwtAuthToken(userLogins[0]);

                if (response.isSuccessful() && response.code() == 200) {
                    return response.body();
                }
                runOnUiThread(() -> {Toast.makeText(activity, "Erreur : " + response.code(), Toast.LENGTH_LONG).show();
                    try {
                        Toast.makeText(activity, "Échec : " + response.errorBody().string(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                return null;

            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        protected void onPostExecute(JWTAuthToken token) {
            if(token != null) {
                Context context = activity.getBaseContext();

                AuthSessionService.setToken(context, token.getAccessToken());
                AuthSessionService.setUserId(context, token.getUserId());
                Toast.makeText(context, "Connexion réussie!", Toast.LENGTH_LONG).show();
                new GetUser().execute();
                startActivity(new Intent(activity, MainMenuActi.class));
                finish();

            } else {
                password.setError("Ce mot de passe est incorrect");
                password.requestFocus();
            }
        }
    }

    private class GetUser extends AsyncTask<Void, Void, User> {

        @Override
        protected User doInBackground(Void... voids) {
            try {
                Response<User> response = new UserDAO().getById(AuthSessionService.getUserId(activity.getApplicationContext()));

                if (response.isSuccessful() && response.code() == 200) {
                    return response.body();
                }

                runOnUiThread(() -> {Toast.makeText(activity, "Erreur : " + response.code(), Toast.LENGTH_LONG).show();
                    try {
                        Toast.makeText(activity, "Échec : " + response.errorBody().string(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(User user) {
            if(user != null) {

                AuthSessionService.setUserPicture(activity, user.getPicture());

            } else {
                Toast.makeText(getApplicationContext(), R.string.error_not_login, Toast.LENGTH_LONG).show();
            }
        }
    }
}
