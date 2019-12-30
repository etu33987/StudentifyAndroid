package com.example.studentify_android.activities.startView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.example.studentify_android.activities.mainMenuView.MainMenuActi;
import com.example.studentify_android.dataAccess.dao.JWTAuthTokenDAO;
import com.example.studentify_android.dataAccess.dao.UserDAO;
import com.example.studentify_android.model.Address;
import com.example.studentify_android.model.form.UserLogin;
import com.example.studentify_android.model.JWTAuthToken;
import com.example.studentify_android.model.User;
import com.example.studentify_android.R;
import com.example.studentify_android.service.AuthSessionService;
import com.example.studentify_android.service.CheckIntenetConnection;
import com.example.studentify_android.service.GoogleService;
import com.example.studentify_android.utils.DateFormatUtil;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.io.IOException;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Response;

public class RegisterActi extends AppCompatActivity {

    @BindView(R.id.register_email)
    EditText email;
    @BindView(R.id.register_password)
    EditText password;
    @BindView(R.id.register_confirm_password)
    EditText confirmPass;
    @BindView(R.id.register_show_name)
    EditText name;
    @BindView(R.id.register_show_firstname)
    EditText firstname;
    @BindView(R.id.register_show_birthdate)
    EditText birthdate;
    @BindView(R.id.register_phoneNumber)
    EditText phone;
    @BindView(R.id.register_street)
    EditText street;
    @BindView(R.id.register_streetnumber)
    EditText streetNumber;
    @BindView(R.id.register_show_city)
    EditText city;
    @BindView(R.id.register_postalCode)
    EditText postalCode;
    @BindView(R.id.register_box)
    EditText box;
    @BindView(R.id.register_floor)
    EditText floor;
    @BindView(R.id.register_picture)
    EditText picture;
    @BindView(R.id.register_gender)
    EditText gender;


    private RegisterViewModel registerViewModel;

    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        activity = this;

        GoogleSignInAccount account = GoogleService.getAccount();

        if(account != null) {

            name.setText(account.getFamilyName());
            email.setText(account.getEmail());
            firstname.setText(account.getGivenName());
        }

        //viewModelObserver();
    }

    /*
    // VOIR COMMENTAIRE DANS LA CLASSE "RegisterViewModel"
    public void viewModelObserver() {
        registerViewModel = ViewModelProviders.of(this).get(RegisterViewModel.class);

        registerViewModel.getBirthdate().observe(this, birthdate -> this.birthdate.setText(birthdate));
        registerViewModel.getBox().observe(this, box -> this.box.setText(box));
        registerViewModel.getCity().observe(this, city -> this.city.setText(city));
        registerViewModel.getEmail().observe(this, email -> this.email.setText(email));
        registerViewModel.getFirstname().observe(this, firstname -> this.firstname.setText(firstname));
        registerViewModel.getFloor().observe(this, floor -> this.floor.setText(floor));
        registerViewModel.getName().observe(this, name -> this.name.setText(name));
        registerViewModel.getPassword().observe(this, password -> this.password.setText(password));
        registerViewModel.getConfirmPassword().observe(this, password -> this.confirmPass.setText(password));
        registerViewModel.getPhone().observe(this, phone -> this.phone.setText(phone));
        registerViewModel.getPostalCode().observe(this, postalCode -> this.postalCode.setText(postalCode));
        registerViewModel.getStreet().observe(this, street -> this.street.setText(street));
        registerViewModel.getStreetNumber().observe(this, streetNumber -> this.streetNumber.setText(streetNumber));
    }*/

    @OnClick(R.id.register_button) void submit() {

        Boolean success = true;

        if(email.getText().toString().isEmpty()) {
            email.setError(getResources().getString(R.string.error_empty));
            success = false;
        } else if (!email.getText().toString().matches(".+@.+\\..+")) {
            email.setError(getResources().getString(R.string.error_matche_email));
            success = false;
        }

        if(password.getText().toString().isEmpty()) {
            password.setError(getResources().getString(R.string.error_empty));
            success = false;
            // ".*\\W+.*\\d+.*[A-Z]+.*|.*\\d+.*\\W+.*[A-Z]+.*|.*[A-Z]+.*\\d+.*\\W+.*|.*\\d+.*[A-Z]+.*\\W+.*|.*[A-Z]+.*\\W+.*\\d+.*"
        }else if(!password.getText().toString().matches("\\w\\w\\w+")) {
            password.setError(getResources().getString(R.string.error_matche_password));
            success = false;
        }

        if(confirmPass.getText().toString().isEmpty()) {
            confirmPass.setError(getResources().getString(R.string.error_empty));
            success = false;
        }else if(!confirmPass.getText().toString().equals(password.getText().toString())) {
            confirmPass.setError(getResources().getString(R.string.register_confirm_pass_matche));
            success = false;
        }

        if(firstname.getText().toString().isEmpty()) {
            firstname.setError(getResources().getString(R.string.error_empty));
            success = false;
        }

        if(name.getText().toString().isEmpty()) {
            name.setError(getResources().getString(R.string.error_empty));
            success = false;
        }

        if(birthdate.getText().toString().isEmpty()) {
            birthdate.setError(getResources().getString(R.string.error_empty));
            success = false;
        }else if (!birthdate.getText().toString().matches("\\d{4}\\/\\d{2}\\/\\d{2}")) {
            birthdate.setError(getResources().getString(R.string.error_matche_birthdate));
            success = false;
        }else if (DateFormatUtil.getDateFormated(birthdate.getText().toString()).after(new Date()))
        {
            birthdate.setError(getResources().getString(R.string.error_fun_date_after));
            success = false;
        }

        if(streetNumber.getText().toString().isEmpty()) {
            streetNumber.setError(getResources().getString(R.string.error_empty));
            success = false;
        }

        if(city.getText().toString().isEmpty()) {
            city.setError(getResources().getString(R.string.error_empty));
            success = false;
        }

        if(street.getText().toString().isEmpty()) {
            street.setError(getResources().getString(R.string.error_empty));
            success = false;
        }

        if(postalCode.getText().toString().isEmpty()) {
            postalCode.setError(getResources().getString(R.string.error_empty));
            success = false;
        }else if (!postalCode.getText().toString().matches("^\\d{4}$")) {
            postalCode.setError(getResources().getString(R.string.error_matche_postalcode));
            success = false;
        }

        if(picture.getText().toString().isEmpty()) {
            picture.setError(getResources().getString(R.string.error_empty));
            success = false;
        }

        if(gender.getText().toString().isEmpty()) {
            gender.setError(getResources().getString(R.string.error_empty));
            success = false;
        }

        if(!phone.getText().toString().isEmpty() && !phone.getText().toString().matches("0\\d+")) {
            phone.setError(getResources().getString(R.string.error_phone_matche));
            success = false;
        }

        //TODO Créer un user

        if(success) {
            Address address = new Address();

            address.setStreetName(street.getText().toString());
            address.setCity(city.getText().toString());
            address.setStreetNumber(streetNumber.getText().toString());
            address.setZipCode(Integer.parseInt(postalCode.getText().toString()));

            if(!box.getText().toString().isEmpty()) {
                address.setBox(box.getText().toString());
            }
            if(!floor.getText().toString().isEmpty()) {
                address.setFloor(Integer.parseInt(floor.getText().toString()));
            }

            User user = new User();

            user.seteMail(email.getText().toString());
            user.setName(name.getText().toString());
            user.setFirstname(firstname.getText().toString());
            user.setBirthdate(DateFormatUtil.getDateFormated(birthdate.getText().toString()));
            user.setPassword(password.getText().toString());
            user.setConfirmPassword(confirmPass.getText().toString());
            user.setPicture(picture.getText().toString());
            user.setAddressNavigation(address);
            user.setSexe(gender.getText().toString());
            user.setCreationDate(new Date());

            if(!phone.getText().toString().isEmpty()) {

                user.setPhoneNumber(phone.getText().toString());
            }

            if (CheckIntenetConnection.checkConnection(this)) {
                new InsertUsertTask().execute(user);
            }else {
                Toast.makeText(this, "Vous n'êtes pas connecté à internet", Toast.LENGTH_LONG).show();
            }
        }
    }

    private class InsertUsertTask extends AsyncTask<User, Void, User> {

        @Override
        protected User doInBackground(User... users) {
            try {

                Response<User> response = new UserDAO().create(users[0]);

                if (response.isSuccessful() && response.code() == 201) {
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

        @Override
        protected void onPostExecute(User userRegister) {
            if(userRegister != null) {

                Toast.makeText(activity, "Succès de la création de l'utilisateur", Toast.LENGTH_LONG).show();

                UserLogin userLogin = new UserLogin();
                userLogin.setPassword(password.getText().toString());
                userLogin.setEmail(email.getText().toString());

                new LoginUserAsyncTask().execute(userLogin);


            } else {
                Toast.makeText(activity, "Échec de la création de l'utilisateur", Toast.LENGTH_LONG).show();
            }
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

                AuthSessionService.setUserId(activity.getApplicationContext(), token.getUserId());
                AuthSessionService.setToken(activity.getApplicationContext(), token.getAccessToken());
                AuthSessionService.setUserPicture(context,picture.getText().toString());
                startActivity(new Intent(activity, MainMenuActi.class));
            } else {
                Toast.makeText(activity, "Échec de la connexion du nouvelle utilisateur", Toast.LENGTH_LONG).show();
            }
        }
    }
}
