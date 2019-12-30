package com.example.studentify_android.Activities.MainMenuView;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studentify_android.Activities.Module.Event.EventActi;
import com.example.studentify_android.Activities.Module.Restaurant.Review.AddReviewActi;
import com.example.studentify_android.DataAccess.dao.Interface.IUserDAO;
import com.example.studentify_android.DataAccess.dao.UserDAO;
import com.example.studentify_android.Model.User;
import com.example.studentify_android.R;
import com.example.studentify_android.Service.AuthSessionService;
import com.example.studentify_android.Service.CheckIntenetConnection;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Response;

public class SettingsActivity extends AppCompatActivity {

    GoogleSignInClient googleClient;

    @BindView(R.id.settings_email)
    TextView email;
    @BindView(R.id.settings_show_name)
    TextView name;
    @BindView(R.id.settings_show_firstname)
    TextView firstname;
    @BindView(R.id.settings_show_birthdate)
    TextView birthdate;
    @BindView(R.id.settings_show_gender)
    TextView gender;
    @BindView(R.id.settings_phoneNumber)
    TextView phone;
    @BindView(R.id.settings_street)
    TextView street;
    @BindView(R.id.settings_streetnumber)
    TextView streetNumber;
    @BindView(R.id.settings_show_city)
    TextView city;
    @BindView(R.id.settings_postalCode)
    TextView postalCode;
    @BindView(R.id.settings_box)
    TextView box;
    @BindView(R.id.settings_floor)
    TextView floor;

    Activity activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        activity = this;

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Settings");

        // Récupération du compte google associé (s'il existe)
        // ?? RECUP LES INFOS DEPUIS LE MAINMENU ??

        //TODO : Comment vérif correctement ?

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleClient = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        if(account != null) {

            name.setText(account.getFamilyName() + getResources().getString(R.string.from_google));
            email.setText(account.getEmail() + getResources().getString(R.string.from_google));
            firstname.setText(account.getGivenName() + getResources().getString(R.string.from_google));
        } else {

            if (CheckIntenetConnection.checkConnection(this)) {
                new GetUser().execute();
            }else {
                Toast.makeText(this, R.string.error_no_connection, Toast.LENGTH_LONG).show();
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

                try {
                    name.setText(user.getName());
                    firstname.setText(user.getFirstname());
                    email.setText(user.geteMail());
                    gender.setText(user.getSexe());
                    birthdate.setText(user.getBithdateFormated());
                    street.setText(user.getAddressNavigation().getStreetName());
                    city.setText(user.getAddressNavigation().getCity());
                    streetNumber.setText(user.getAddressNavigation().getStreetNumber());
                    postalCode.setText(String.valueOf(user.getAddressNavigation().getZipCode()));

                    if(user.getAddressNavigation() != null) {
                        if(user.getAddressNavigation().getBox() != null) {
                            box.setText(user.getAddressNavigation().getBox());
                        }
                        if(user.getAddressNavigation().getFloor() != null) {
                            floor.setText(user.getAddressNavigation().getFloor());
                        }
                    }
                    if(!user.getPhoneNumber().isEmpty()) {
                        phone.setText(user.getPhoneNumber());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            } else {
                Toast.makeText(getApplicationContext(), R.string.error_not_login, Toast.LENGTH_LONG).show();
            }
        }
    }

    @OnClick(R.id.upDateProfilButton) void onClick() {
        startActivityForResult(new Intent(this, UpdateProfilActi.class), 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent modifProfil){
        super.onActivityResult(requestCode, resultCode, modifProfil);

        if(resultCode == RESULT_CANCELED) {
            Toast toast = Toast.makeText(getApplicationContext(), "Modification annulé", Toast.LENGTH_LONG);
            toast.show();
        }else if (resultCode == RESULT_OK) {
            Toast.makeText(this, "Modification réussie", Toast.LENGTH_LONG);
        }
    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), MainMenuActi.class);
        startActivityForResult(myIntent, 0);
        return true;
    }
}
