package com.example.studentify_android.activities.module.restaurant;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studentify_android.activities.module.restaurant.review.ReviewActi;
import com.example.studentify_android.dataAccess.dao.Interface.IRestauDAO;
import com.example.studentify_android.dataAccess.dao.RestauDAO;
import com.example.studentify_android.model.Restaurant;
import com.example.studentify_android.R;
import com.example.studentify_android.utils.ObjectWrapperForBinder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Response;

public class RestauInfoActi extends AppCompatActivity {

    @BindView(R.id.restau_title)
    TextView titleText;
    @BindView(R.id.restau_desc)
    TextView descText;
    @BindView(R.id.restau_type)
    TextView typeText;
    @BindView(R.id.restau_place_street)
    TextView streetText;
    @BindView(R.id.restau_place_city)
    TextView cityText;
    @BindView(R.id.restau_place_streetNumber)
    TextView streetNumberText;
    @BindView(R.id.restau_place_postalCode)
    TextView postalCodeText;
    @BindView(R.id.restau_place_box)
    TextView boxText;
    @BindView(R.id.restau_place_floor)
    TextView floorText;
    @BindView(R.id.restau_menu)
    TextView menuText;
    @BindView(R.id.restau_schedule)
    TextView scheduleText;

    @BindView(R.id.restau_ratingBar)
    RatingBar ratingBar;

    private Restaurant restau;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restau_info);
        ButterKnife.bind(this);

        restau = (Restaurant)((ObjectWrapperForBinder)getIntent().getExtras().getBinder("restauItem")).getData();

        String title = restau.getName();
        String desc = restau.getDescription();
        String type = restau.getType();
        String street = restau.getAddressNavigation().getStreetName();
        String city = restau.getAddressNavigation().getCity();
        String streetNumber = restau.getAddressNavigation().getStreetNumber();
        Integer zipCode = restau.getAddressNavigation().getZipCode();
        String box = restau.getAddressNavigation().getBox();
        Integer floor = restau.getAddressNavigation().getFloor();
        String menu = restau.getMenu();
        String schedule = restau.getSchedule();

        titleText.setText(String.valueOf(title));
        descText.setText(String.valueOf(desc));
        typeText.setText(String.valueOf(type));
        streetText.setText(String.valueOf(street));
        cityText.setText(String.valueOf(city));
        streetNumberText.setText(String.valueOf(streetNumber));
        postalCodeText.setText(String.valueOf(zipCode));
        boxText.setText(String.valueOf(box));
        floorText.setText(String.valueOf(floor));
        menuText.setText(String.valueOf(menu));
        scheduleText.setText(String.valueOf(schedule));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Restaurant");
        getSupportActionBar().setSubtitle(title);

        new LoadRating().execute();
    }

    @OnClick(R.id.restau_goToRating) void goToRatingActi() {

        Bundle bundle = new Bundle();
        bundle.putBinder("restau", new ObjectWrapperForBinder(restau));
        startActivity(new Intent(this, ReviewActi.class).putExtras(bundle).putExtra("restauId", restau.getId()));
    }

    public boolean onOptionsItemSelected(MenuItem item){

        Intent myIntent = new Intent(getApplicationContext(), RestauActi.class);
        startActivityForResult(myIntent, 0);
        return true;
    }

    private class LoadRating extends AsyncTask<Void, Void, Float>
    {
        private final IRestauDAO restauDAO = new RestauDAO();

        @Override
        protected Float doInBackground(Void... voids) {
            Restaurant restau = (Restaurant)((ObjectWrapperForBinder)getIntent().getExtras().getBinder("restauItem")).getData();
            Log.w("MOI", restau.getId()+"");
            try {
                Response<Float> response = restauDAO.getRatingForOne(restau.getId());
                if (response.isSuccessful() && response.code() == 200) {
                    return response.body();
                }else {
                    if(response.code() == 404) {
                        runOnUiThread(() -> Toast.makeText(RestauInfoActi.this, "Soyez le premier à poster un avis ! :D", Toast.LENGTH_LONG).show());
                    }else {
                        runOnUiThread(() -> Toast.makeText(RestauInfoActi.this, "Erreur : " + response.code(), Toast.LENGTH_LONG).show());
                    }
                }
            }catch (Exception e)
            {
                runOnUiThread(() -> {
                    AlertDialog.Builder alert = new AlertDialog.Builder(RestauInfoActi.this);
                    alert.setTitle("Exception error");
                    alert.setMessage(e+"");
                    alert.setPositiveButton("Ok", null);
                    alert.create().show();
                });
            }
            return null;
        }

        @Override
        protected void onPostExecute(Float rate)
        {
            if(rate != null) {
                ratingBar.setRating(rate);
            }
        }
    }
}
