package com.example.studentify_android.Activities.Module.Restaurant.Review;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.studentify_android.Activities.MainMenuView.MainMenuActi;
import com.example.studentify_android.Activities.Module.Restaurant.RestauActi;
import com.example.studentify_android.Activities.Module.Restaurant.RestauAdapter;
import com.example.studentify_android.Activities.Module.Restaurant.RestauInfoActi;
import com.example.studentify_android.DataAccess.dao.Interface.IRestauDAO;
import com.example.studentify_android.DataAccess.dao.Interface.IReviewDAO;
import com.example.studentify_android.DataAccess.dao.RestauDAO;
import com.example.studentify_android.DataAccess.dao.ReviewDAO;
import com.example.studentify_android.Model.Restaurant;
import com.example.studentify_android.Model.Review;
import com.example.studentify_android.MyApp;
import com.example.studentify_android.R;
import com.example.studentify_android.Utils.ObjectWrapperForBinder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Response;

public class ReviewActi extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ReviewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @BindView(R.id.review_progressBar)
    ProgressBar progressBar;

    Intent intent;
    Restaurant restaurant;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        ButterKnife.bind(this);

        intent = getIntent();
        restaurant = (Restaurant)((ObjectWrapperForBinder)getIntent().getExtras().getBinder("restau")).getData();

        Log.i("MOIII", restaurant+"<------222222222222---------------");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Review");

        new LoadReview().execute();
    }

    @OnClick(R.id.addReviewButton) void onAdd() {
        startActivityForResult(new Intent(this, AddReviewActi.class)
                .putExtra("restauId", getIntent().getExtras().getInt("restauId")), 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent restauInfos){
        super.onActivityResult(requestCode, resultCode, restauInfos);

        if(resultCode == RESULT_CANCELED) {
            Toast toast = Toast.makeText(getApplicationContext(), "Ajout annulé", Toast.LENGTH_LONG);
            toast.show();
        }else if (resultCode == RESULT_OK) {
            Toast.makeText(this, "Avis ajouté avec succès", Toast.LENGTH_LONG);
        }
    }

    private class LoadReview extends AsyncTask<Void, Void, ArrayList<Review>> {
        private IReviewDAO restauDAO = new ReviewDAO();

        @Override
        protected ArrayList<Review> doInBackground(Void... voids) {

            try {
                Response<ArrayList<Review>> response = restauDAO.getAllForOne(restaurant.getId());
                if (response.isSuccessful() && response.code() == 200) {
                    return response.body();
                }else {
                    runOnUiThread(() -> Toast.makeText(ReviewActi.this, "Erreur : " + response.code(), Toast.LENGTH_LONG).show());
                }
            }catch (Exception e)
            {
                runOnUiThread(() -> {
                    AlertDialog.Builder alert = new AlertDialog.Builder(ReviewActi.this);
                    alert.setTitle("Exception error");
                    alert.setMessage(e+"");
                    alert.setPositiveButton("Ok", null);
                    alert.create().show();
                });
            }
            return new ArrayList<>();
        }

        @Override
        protected void onPostExecute(ArrayList<Review> reviews)
        {
            if(!reviews.isEmpty()) {
                
                buildRecyclerView(reviews);
            }else {
                Toast.makeText(MyApp.getInstance(), "Pas d'avis à afficher", Toast.LENGTH_LONG);
            }
            progressBar.setAlpha(0f);
        }
    }

    private void buildRecyclerView(ArrayList<Review> reviews) {
        mRecyclerView = findViewById(R.id.reviewRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);

        mAdapter = new ReviewAdapter(reviews, this);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = (SearchView)item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                mAdapter.filter(s);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}
