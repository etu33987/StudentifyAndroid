package com.example.studentify_android.activities.mainMenuView;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.studentify_android.dataAccess.dao.UserDAO;
import com.example.studentify_android.model.Address;
import com.example.studentify_android.model.form.NewPassword;
import com.example.studentify_android.model.User;
import com.example.studentify_android.R;
import com.example.studentify_android.service.AuthSessionService;
import com.example.studentify_android.service.CheckIntenetConnection;
import com.example.studentify_android.utils.DateFormatUtil;

import java.io.IOException;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Response;

public class UpdateProfilActi extends AppCompatActivity {

    @BindView(R.id.update_email)
    EditText email;
    @BindView(R.id.update_show_name)
    EditText name;
    @BindView(R.id.update_show_firstname)
    EditText firstname;
    @BindView(R.id.update_show_birthdate)
    EditText birthdate;
    @BindView(R.id.update_phoneNumber)
    EditText phone;
    @BindView(R.id.update_street)
    EditText street;
    @BindView(R.id.update_streetnumber)
    EditText streetNumber;
    @BindView(R.id.update_show_city)
    EditText city;
    @BindView(R.id.update_postalCode)
    EditText postalCode;
    @BindView(R.id.update_box)
    EditText box;
    @BindView(R.id.update_floor)
    EditText floor;
    @BindView(R.id.update_picture)
    EditText picture;
    @BindView(R.id.update_gender)
    EditText gender;

    private Activity activity;
    private User userUpdate;
    private User currentUser;
    private Address address;

    private Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profil);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Update");

        currentUser = new User();
        activity = this;
        intent = getIntent();
        userUpdate = new User();
        address = new Address();
        userUpdate.setAddressNavigation(address);

        new GetUser().execute();
    }

    @OnClick(R.id.saveButton) void onSave() {

        Boolean success = true;

        if(email.getText().toString().isEmpty()) {
            email.setError(getResources().getString(R.string.error_empty));
            success = false;
        } else if (!email.getText().toString().matches(".+@.+\\..+")) {
            email.setError(getResources().getString(R.string.error_matche_email));
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

        if(success) {

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

            userUpdate.seteMail(email.getText().toString());
            userUpdate.setName(name.getText().toString());
            userUpdate.setFirstname(firstname.getText().toString());
            userUpdate.setBirthdate(DateFormatUtil.getDateFormated(birthdate.getText().toString()));
            userUpdate.setPicture(picture.getText().toString());
            userUpdate.setSexe(gender.getText().toString());
            userUpdate.setId(AuthSessionService.getUserId(this));

            if(!phone.getText().toString().isEmpty()) {
                userUpdate.setPhoneNumber(phone.getText().toString());
            }

            if (CheckIntenetConnection.checkConnection(this)) {
                new UpdateUser().execute(userUpdate);
            }else {
                Toast.makeText(this, R.string.error_no_connection, Toast.LENGTH_LONG).show();
            }
        }
    }

    @OnClick(R.id.cancelButton) void onCancel() {

        setResult(RESULT_CANCELED);
        finish();
    }

    @OnClick(R.id.changePasswordButton) void onChangePsw() {

        NewPassword newPassword = new NewPassword();

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);


        EditText mOldPswd = new EditText(this);
        EditText mNewPswd = new EditText(this);

        mOldPswd.setHint(R.string.change_pswd_current_password);
        mNewPswd.setHint(R.string.change_pswd_new_password);

        layout.addView(mOldPswd);
        layout.addView(mNewPswd);


        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setView(layout)
                .setPositiveButton("Ok", null)
                .setNegativeButton("Annuler", null)
                .setTitle("Modification de mot de passe")
                .create();

        alertDialog.setOnShowListener(dialog -> {

            Button buttonPositive = ((AlertDialog) dialog).getButton(DialogInterface.BUTTON_POSITIVE);
            buttonPositive.setOnClickListener(view -> {

                if(mNewPswd.getText().toString().isEmpty() || mOldPswd.getText().toString().isEmpty()) {
                    mNewPswd.setError(getResources().getString(R.string.error_empty));
                    mOldPswd.setError(getResources().getString(R.string.error_empty));
                }else {
                    if(!mNewPswd.getText().toString().matches("\\w\\w\\w+")) {
                        mNewPswd.setError(getResources().getString(R.string.error_matche_password));
                    }else {
                        newPassword.setCurrentPswd(mOldPswd.getText().toString());
                        newPassword.setNewPswd(mNewPswd.getText().toString());
                        newPassword.seteMail(currentUser.geteMail());

                        new UpdatePwdUser().execute(newPassword);
                    }
                }

            });
        });
        alertDialog.show();

    }

    private class GetUser extends AsyncTask<Void, Void, User> {

        @Override
        protected User doInBackground(Void... voids) {
            try {

                Response<User> response = new UserDAO().getById(AuthSessionService.getUserId(activity.getApplicationContext()));

                if (response.isSuccessful() && response.code() == 200) {
                    return response.body();
                }

                runOnUiThread(() -> {
                    Toast.makeText(activity, "Erreur : " + response.code(), Toast.LENGTH_LONG).show();
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
                    currentUser = user;

                    name.setText(user.getName());
                    firstname.setText(user.getFirstname());
                    email.setText(user.geteMail());
                    birthdate.setText(user.getBithdateFormated());
                    street.setText(user.getAddressNavigation().getStreetName());
                    city.setText(user.getAddressNavigation().getCity());
                    streetNumber.setText(user.getAddressNavigation().getStreetNumber());
                    postalCode.setText(String.valueOf(user.getAddressNavigation().getZipCode()));
                    picture.setText(user.getPicture());
                    gender.setText(user.getSexe());

                    if(user.getAddressNavigation().getBox() != null) {
                        box.setText(user.getAddressNavigation().getBox());
                    }
                    if(user.getAddressNavigation().getFloor() != null) {
                        floor.setText(user.getAddressNavigation().getFloor());
                    }
                    userUpdate.getAddressNavigation().setId(user.getAddressNavigation().getId());


                    if(user.getPhoneNumber() != null) {
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

    private class UpdateUser extends AsyncTask<User, Void, User> {

        @Override
        protected User doInBackground(User... users) {
            try {

                Response<User> response = new UserDAO().update(users[0]);

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

        @Override
        protected void onPostExecute(User userRegister) {
            if(userRegister != null) {

                Toast.makeText(activity, "Succès de la mise à jour de l'utilisateur", Toast.LENGTH_LONG).show();

                setResult(RESULT_OK);
                finish();


            } else {
                Toast.makeText(activity, "Échec de la mise à jour de l'utilisateur", Toast.LENGTH_LONG).show();
            }
        }
    }

    private class UpdatePwdUser extends AsyncTask<NewPassword, Void, Boolean> {

        @Override
        protected Boolean doInBackground(NewPassword... pswd) {
            try {

                Response<Void> response = new UserDAO().updatePswd(pswd[0]);

                if (response.isSuccessful() && response.code() == 204) {
                    return true;
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
            return false;
        }

        @Override
        protected void onPostExecute(Boolean isUpdated) {
            if(isUpdated) {

                Toast.makeText(activity, "Succès de la maj du mp de l'utilisateur", Toast.LENGTH_LONG).show();

                setResult(RESULT_OK);
                finish();


            } else {
                Toast.makeText(activity, "Échec de la maj du mp de l'utilisateur", Toast.LENGTH_LONG).show();
            }
        }
    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), MainMenuActi.class);
        startActivityForResult(myIntent, 0);
        return true;
    }
}
