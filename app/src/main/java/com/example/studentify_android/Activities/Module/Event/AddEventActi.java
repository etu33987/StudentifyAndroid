package com.example.studentify_android.Activities.Module.Event;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.example.studentify_android.DataAccess.dao.EventDAO;
import com.example.studentify_android.DataAccess.dao.UserDAO;
import com.example.studentify_android.Model.Address;
import com.example.studentify_android.Model.Event;
import com.example.studentify_android.Model.User;
import com.example.studentify_android.R;
import com.example.studentify_android.Service.AuthSessionService;
import com.example.studentify_android.Service.CheckIntenetConnection;
import com.example.studentify_android.Utils.DateFormatUtil;

import java.io.IOException;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Response;

public class AddEventActi extends AppCompatActivity {

    @BindView(R.id.addEventTitleEvent)
    EditText titleEventEditText;
    @BindView(R.id.addEventDesc)
    EditText descEventText;
    @BindView(R.id.addEventStartDate)
    EditText startDateText;
    @BindView(R.id.addEventEndDate)
    EditText endDateText;
    @BindView(R.id.addEventType)
    EditText type;

    @BindView(R.id.addEventBox)
    EditText box;
    @BindView(R.id.addEventPostalCode)
    EditText postalCode;
    @BindView(R.id.addEventStreet)
    EditText street;
    @BindView(R.id.addEventCity)
    EditText city;
    @BindView(R.id.addEventStreetNumber)
    EditText streetNumber;
    @BindView(R.id.addEventFloor)
    EditText floor;

    Intent intent;
    Event event;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        ButterKnife.bind(this);
        activity = this;

        event = new Event();
        intent = getIntent();

    }

    private Boolean checkForm(){
        Boolean success = true;

        if(titleEventEditText.getText().toString().isEmpty()) {
            titleEventEditText.setError(getResources().getString(R.string.error_empty));
            success = false;
        }

        if(startDateText.getText().toString().isEmpty()) {
            startDateText.setError(getResources().getString(R.string.error_empty));
            success = false;
        } else if (!startDateText.getText().toString().matches("\\d{4}\\/\\d{2}\\/\\d{2}")) {

            startDateText.setError(getResources().getString(R.string.error_matche_birthdate));
            success = false;
        } else if(!(DateFormatUtil.getDateFormated(startDateText.getText().toString()).after(new Date()))) {
            startDateText.setError(getResources().getString(R.string.error_date_after_today));
            success = false;
        }

        if(!endDateText.getText().toString().isEmpty() && !endDateText.getText().toString().matches("\\d{4}\\/\\d{2}\\/\\d{2}")){
            endDateText.setError(getResources().getString(R.string.error_matche_birthdate));
            success = false;
        }else if(!endDateText.getText().toString().isEmpty()) {

            if(!DateFormatUtil.getDateFormated(endDateText.getText().toString()).after(DateFormatUtil.getDateFormated(startDateText.getText().toString()))) {
                endDateText.setError(getResources().getString(R.string.error_date_after_begin_date));
                success = false;
            }
        }

        if(type.getText().toString().isEmpty()) {
            type.setError(getResources().getString(R.string.error_empty));
            success = false;
        }

        if(!postalCode.getText().toString().isEmpty() && !postalCode.getText().toString().matches("^\\d{4}$")) {
            postalCode.setError(getResources().getString(R.string.error_matche_postalcode));
            success = false;
        }

        return success;
    }

    @OnClick(R.id.addEventButton) void addButton() {

        if(checkForm()) {
            boolean addressExist = false;
            Address address = new Address();

            if(!street.getText().toString().isEmpty()) {
                addressExist = true;
                address.setStreetName(street.getText().toString());
            }
            if(!city.getText().toString().isEmpty()) {
                addressExist = true;
                address.setCity(city.getText().toString());
            }
            if(!streetNumber.getText().toString().isEmpty()) {
                addressExist = true;
                address.setStreetNumber(streetNumber.getText().toString());
            }
            if(!postalCode.getText().toString().isEmpty()) {
                addressExist = true;
                address.setZipCode(Integer.parseInt(postalCode.getText().toString()));
            }

            if(!box.getText().toString().isEmpty()) {
                addressExist = true;
                address.setBox(box.getText().toString());
            }
            if(!floor.getText().toString().isEmpty()) {
                addressExist = true;
                address.setFloor(Integer.parseInt(floor.getText().toString()));
            }

            event.setTitle(titleEventEditText.getText().toString());
            event.setDescription(descEventText.getText().toString());
            event.setBeginDate(DateFormatUtil.getDateFormated(startDateText.getText().toString()));
            if(!endDateText.getText().toString().isEmpty()) {
                event.setEndDate(DateFormatUtil.getDateFormated(endDateText.getText().toString()));
            }

            if(addressExist) {
                event.setAddressNavigation(address);
            }

            event.setType(type.getText().toString());

            if (CheckIntenetConnection.checkConnection(this)) {

                if(addressExist) {
                    new AlertDialog.Builder(this)
                            .setTitle(R.string.warning_addEvent_title)
                            .setMessage(R.string.warning_addEvent_message)

                            .setPositiveButton(R.string.warning_addEvent_ok, (dialog, which) -> {
                                new GetUser().execute();
                            })

                            .setNegativeButton(R.string.warning_addEvent_cancel, null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                } else {
                    new GetUser().execute();
                }

            }else {
                Toast.makeText(this, R.string.error_no_connection, Toast.LENGTH_LONG).show();
            }
        }

    }

    @OnClick(R.id.cancelAddEventButton) void cancelButton() {

        setResult(RESULT_CANCELED, intent);
        finish();
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

                event.setAuthorNavigation(user);
                new InsertEventTask().execute(event);

            } else {
                Toast.makeText(getApplicationContext(), R.string.error_not_login, Toast.LENGTH_LONG).show();
            }
        }
    }

    private class InsertEventTask extends AsyncTask<Event, Void, Event> {

        @Override
        protected Event doInBackground(Event... events) {
            try {

                Response<Event> response =  new EventDAO().add(events[0]);
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
        protected void onPostExecute(Event event) {
            if(event != null) {

                Toast.makeText(activity, "Succès de l'ajout de l'envent", Toast.LENGTH_LONG).show();
                setResult(RESULT_OK, intent);
                finish();

            } else {
                Toast.makeText(activity, "Échec de l'ajout de l'envent", Toast.LENGTH_LONG).show();
            }
        }
    }
}
