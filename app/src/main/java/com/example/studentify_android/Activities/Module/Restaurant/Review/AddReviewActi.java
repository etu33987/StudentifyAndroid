package com.example.studentify_android.Activities.Module.Restaurant.Review;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.studentify_android.Activities.Module.Restaurant.RestauActi;
import com.example.studentify_android.DataAccess.dao.RestauDAO;
import com.example.studentify_android.DataAccess.dao.ReviewDAO;
import com.example.studentify_android.DataAccess.dao.UserDAO;
import com.example.studentify_android.Model.Form.ReviewAdd;
import com.example.studentify_android.Model.Restaurant;
import com.example.studentify_android.Model.User;
import com.example.studentify_android.R;
import com.example.studentify_android.Service.AuthSessionService;
import com.example.studentify_android.Service.CheckIntenetConnection;

import java.io.IOException;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Response;

public class AddReviewActi extends AppCompatActivity {

    @BindView(R.id.reviewDesc)
    EditText reviewDesc;
    @BindView(R.id.reviewRatingBar)
    RatingBar reviewRatingBar;

    Intent intent;
    ReviewAdd review;

    Activity activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);
        ButterKnife.bind(this);

        review = new ReviewAdd();

        intent = getIntent();

        activity = this;

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Avis");
    }

    public boolean onOptionsItemSelected(MenuItem item){

        Intent myIntent = new Intent(getApplicationContext(), RestauActi.class);
        startActivityForResult(myIntent, 0);
        return true;
    }


    @OnClick(R.id.addReviewButton) void addButton() {

        review.setDescription(reviewDesc.getText().toString());
        review.setNote(Integer.parseInt(String.valueOf(String.valueOf(reviewRatingBar.getRating()).charAt(0))));
        review.setDate(new Date());

        if (CheckIntenetConnection.checkConnection(this)) {
            new GetUser().execute();
        }else {
            Toast.makeText(this, "Vous n'êtes pas connecté à internet", Toast.LENGTH_LONG).show();
        }
    }

    @OnClick(R.id.cancelAddReviewButton) void cancelButton() {

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

                review.setuserNavigation(user);
                new GetRestau().execute();

            } else {
                Toast.makeText(getApplicationContext(), R.string.error_not_login, Toast.LENGTH_LONG).show();
            }
        }
    }

    private class GetRestau extends AsyncTask<Void, Void, Restaurant> {

        @Override
        protected Restaurant doInBackground(Void... voids) {
            try {

                Response<Restaurant> response = new RestauDAO().getOne(intent.getExtras().getInt("restauId"));

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

        protected void onPostExecute(Restaurant restaurant) {
            if(restaurant != null) {

                review.setrestaurantNavigation(restaurant);

                new InsertReviewTask().execute(review);

            } else {
                Toast.makeText(getApplicationContext(), "Impossible de récupérer le restaurant", Toast.LENGTH_LONG).show();
            }
        }
    }

    private class InsertReviewTask extends AsyncTask<ReviewAdd, Void, ReviewAdd> {

        @Override
        protected ReviewAdd doInBackground(ReviewAdd... reviews) {
            try {

                Response<ReviewAdd> response =  new ReviewDAO().add(reviews[0]);
                if (response.isSuccessful() && response.code() == 201) {
                    return response.body();
                }

                runOnUiThread(() -> {Toast.makeText(activity, "Erreur : " + response.code(), Toast.LENGTH_LONG).show();
                    try {
                        Toast.makeText(activity, "Échec : " + response.errorBody().string(), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ReviewAdd review) {
            if(review != null) {

                Toast.makeText(activity, "Succès de l'ajout de l'avis", Toast.LENGTH_LONG).show();
                setResult(RESULT_OK, intent);
                finish();

            } else {
                Toast.makeText(activity, "Échec de l'ajout de l'avis", Toast.LENGTH_LONG).show();
            }
        }
    }
}
