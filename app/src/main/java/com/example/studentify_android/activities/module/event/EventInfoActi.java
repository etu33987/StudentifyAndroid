package com.example.studentify_android.activities.module.event;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studentify_android.dataAccess.dao.EventDAO;
import com.example.studentify_android.dataAccess.dao.Interface.IEventDAO;
import com.example.studentify_android.dataAccess.dao.Interface.IParticipationDAO;
import com.example.studentify_android.dataAccess.dao.ParticipationDAO;
import com.example.studentify_android.dataAccess.dao.UserDAO;
import com.example.studentify_android.model.Event;
import com.example.studentify_android.model.Participation;
import com.example.studentify_android.model.User;
import com.example.studentify_android.R;
import com.example.studentify_android.service.AuthSessionService;
import com.example.studentify_android.service.CheckIntenetConnection;
import com.example.studentify_android.utils.ObjectWrapperForBinder;

import java.io.IOException;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Response;

public class EventInfoActi extends AppCompatActivity {

    @BindView(R.id.event_title)
    TextView titleText;
    @BindView(R.id.event_desc)
    TextView descText;
    @BindView(R.id.event_type)
    TextView typeText;
    @BindView(R.id.event_beginDate)
    TextView beginDateText;
    @BindView(R.id.event_endDate)
    TextView endDateText;
    @BindView(R.id.event_author)
    TextView authorText;
    @BindView(R.id.event_place_street)
    TextView streetText;
    @BindView(R.id.event_place_city)
    TextView cityText;
    @BindView(R.id.event_place_streetNumber)
    TextView streetNumberText;
    @BindView(R.id.event_place_postalCode)
    TextView postalCodeText;
    @BindView(R.id.event_place_box)
    TextView boxText;
    @BindView(R.id.event_place_floor)
    TextView floorText;

    @BindView(R.id.event_joinButton)
    Button joinButton;
    @BindView(R.id.eventModifButton)
    Button eventModifButton;

    private Boolean hasJoin;
    private Activity activity;
    private User currentUser;
    private Event event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_info);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Événement");
        activity = this;
        hasJoin = false;
        currentUser = new User();

        event = (Event)((ObjectWrapperForBinder)getIntent().getExtras().getBinder("eventItem")).getData();

        String title = event.getTitle();
        String desc = event.getDescription();
        String type = event.getType();
        Date endDate = event.getEndDate();
        String authorFirstName = event.getAuthorNavigation().getFirstname();
        String authorName = event.getAuthorNavigation().getName();

        if(event.getAddressNavigation() != null) {
            String street = event.getAddressNavigation().getStreetName();
            String city = event.getAddressNavigation().getCity();
            String streetNumber = event.getAddressNavigation().getStreetNumber();
            Integer zipCode = event.getAddressNavigation().getZipCode();
            String box = event.getAddressNavigation().getBox();
            Integer floor = event.getAddressNavigation().getFloor();

            streetText.setText(String.valueOf(street));
            cityText.setText(String.valueOf(city));
            streetNumberText.setText(String.valueOf(streetNumber));
            postalCodeText.setText(String.valueOf(zipCode));

            if(boxText != null) {
                boxText.setText(String.valueOf(box));
            }

            if(floorText != null) {
                floorText.setText(String.valueOf(floor));
            }
        }

        titleText.setText(String.valueOf(title));
        descText.setText(String.valueOf(desc));
        typeText.setText(String.valueOf(type));
        beginDateText.setText(event.getStartEventFormated());

        if(endDate != null) {
            endDateText.setText(event.getEndEventFormated());
        }
        authorText.setText(authorName + " " + authorFirstName);

        getSupportActionBar().setSubtitle(title);

        if (CheckIntenetConnection.checkConnection(this)) {
            new GetOwnerAsync().execute();

        }else {
            Toast.makeText(this, R.string.error_no_connection, Toast.LENGTH_LONG).show();
        }
    }

    private void setJoinButton(Boolean hasJoin) {

        joinButton.setVisibility(View.VISIBLE);

        if(hasJoin){
            joinButton.setText(R.string.event_unFollowButton);

            this.hasJoin = true;

        }else {
            joinButton.setText(R.string.event_followButton);

            this.hasJoin = false;
        }
    }

    @OnClick(R.id.event_joinButton) void joinButton() {

        if(hasJoin) {
            new DeleteParticipAsync().execute();
        }
        else {
            new GetUser().execute();
        }
    }

    @OnClick(R.id.eventModifButton) void modifButton(){
        Bundle bundle = new Bundle();
        bundle.putBinder("eventItem", new ObjectWrapperForBinder(event));
        startActivity(new Intent(this, UpdateEventActi.class).putExtras(bundle));
    }

    // Vérifie si l'utilisateur est l'auteur de l'event
    private class GetOwnerAsync extends AsyncTask<Void, Void, Boolean> {
        private final IEventDAO eventDAO = new EventDAO();
        Event event = (Event)((ObjectWrapperForBinder)getIntent().getExtras().getBinder("eventItem")).getData();

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                Response<Boolean> response = eventDAO.isOwner(event.getId());
                if (response.isSuccessful() && response.code() == 200) {
                    return response.body();
                }else {
                    runOnUiThread(() -> {Toast.makeText(activity, "Erreur : " + response.code(), Toast.LENGTH_LONG).show();
                        try {
                            Toast.makeText(activity, "Échec : " + response.errorBody().string(), Toast.LENGTH_LONG).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                }
            }catch (Exception e)
            {
                runOnUiThread(() -> {
                    AlertDialog.Builder alert = new AlertDialog.Builder(EventInfoActi.this);
                    alert.setTitle("Exception error");
                    alert.setMessage(e+"");
                    alert.setPositiveButton("Ok", null);
                    alert.create().show();
                });
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean isOwner)
        {
            if(isOwner) {
                joinButton.setVisibility(View.INVISIBLE);
                eventModifButton.setVisibility(View.VISIBLE);
            }else {
                new GetParticipAsync().execute();
            }

        }
    }

    // Vérifie si l'utilisateur participe déjà à l'event
    private class GetParticipAsync extends AsyncTask<Void, Void, Boolean> {
        private final IParticipationDAO participationDAO = new ParticipationDAO();

        @Override
        protected Boolean doInBackground(Void... voids) {
            Event event = (Event)((ObjectWrapperForBinder)getIntent().getExtras().getBinder("eventItem")).getData();

            try {
                Response<Boolean> response = participationDAO.getUserPartiFromEvent(AuthSessionService.getUserId(activity.getApplicationContext()), event.getId());
                if (response.isSuccessful() && response.code() == 200) {
                    return response.body();
                }else {
                    runOnUiThread(() -> {Toast.makeText(activity, "Erreur : " + response.code(), Toast.LENGTH_LONG).show();
                        try {
                            Toast.makeText(activity, "Échec : " + response.errorBody().string(), Toast.LENGTH_LONG).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                }
            }catch (Exception e)
            {
                runOnUiThread(() -> {
                    AlertDialog.Builder alert = new AlertDialog.Builder(EventInfoActi.this);
                    alert.setTitle("Exception error");
                    alert.setMessage(e+"");
                    alert.setPositiveButton("Ok", null);
                    alert.create().show();
                });
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean isParticip)
        {
            setJoinButton(isParticip);
        }
    }

    // Enlève la participation de l'utilisateur à l'event
    private class DeleteParticipAsync extends AsyncTask<Void, Void, Boolean> {
        private final IParticipationDAO participationDAO = new ParticipationDAO();

        @Override
        protected Boolean doInBackground(Void... voids) {
            Event event = (Event)((ObjectWrapperForBinder)getIntent().getExtras().getBinder("eventItem")).getData();

            try {
                Response<Boolean> response = participationDAO.deleteParticipation(event.getId());
                if (response.isSuccessful() && response.code() == 204) {
                    return true;
                }else {
                    runOnUiThread(() -> {Toast.makeText(activity, "Erreur : " + response.code(), Toast.LENGTH_LONG).show();
                        Toast.makeText(activity, "Échec : " + response.message(), Toast.LENGTH_LONG).show();
                    });
                }
            }catch (Exception e)
            {
                runOnUiThread(() -> {
                    AlertDialog.Builder alert = new AlertDialog.Builder(EventInfoActi.this);
                    alert.setTitle("Exception error");
                    alert.setMessage(e+"");
                    alert.setPositiveButton("Ok", null);
                    alert.create().show();
                });
            }
            return false;
        }
        protected void onPostExecute(Boolean deleteSuccess) {
            if(deleteSuccess) {

                hasJoin = false;
                setJoinButton(false);

            } else {
                Toast.makeText(getApplicationContext(), "Échec de la suppression du participant", Toast.LENGTH_LONG).show();
            }
        }
    }

    // Ajout la participation de l'utilisateur à l'event
    private class AddParticipAsync extends AsyncTask<Void, Void, Participation> {
        private final IParticipationDAO participationDAO = new ParticipationDAO();

        @Override
        protected Participation doInBackground(Void... voids) {
            Event event = (Event)((ObjectWrapperForBinder)getIntent().getExtras().getBinder("eventItem")).getData();

            Participation participation = new Participation();
            participation.setUserNavigation(currentUser);
            participation.setEventNavigation(event);
            try {
                Response<Participation> response = participationDAO.addParticipation(participation);
                if (response.isSuccessful() && response.code() == 201) {
                    return response.body();
                }else {
                    runOnUiThread(() -> {Toast.makeText(activity, "Erreur : " + response.code(), Toast.LENGTH_LONG).show();
                        try {
                            Toast.makeText(activity, "Échec " + response.errorBody().string(), Toast.LENGTH_LONG).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                }
            }catch (Exception e)
            {
                runOnUiThread(() -> {
                    AlertDialog.Builder alert = new AlertDialog.Builder(EventInfoActi.this);
                    alert.setTitle("Exception error");
                    alert.setMessage(e+"");
                    alert.setPositiveButton("Ok", null);
                    alert.create().show();
                });
            }
            return null;
        }
        protected void onPostExecute(Participation participation) {
            if(participation != null) {

                hasJoin = true;
                setJoinButton(true);

            } else {
                Toast.makeText(getApplicationContext(), "Échec de l'ajout du participant", Toast.LENGTH_LONG).show();
            }
        }
    }

    // Récupère toutes les infos de l'utilisateur courant
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

                currentUser = user;
                new AddParticipAsync().execute();

            } else {
                Toast.makeText(getApplicationContext(), R.string.error_not_login, Toast.LENGTH_LONG).show();
            }
        }
    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), EventActi.class);
        startActivityForResult(myIntent, 0);
        return true;
    }
}
