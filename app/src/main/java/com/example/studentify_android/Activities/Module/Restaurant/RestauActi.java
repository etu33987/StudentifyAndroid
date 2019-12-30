package com.example.studentify_android.Activities.Module.Restaurant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.studentify_android.Activities.Module.Event.EventActi;
import com.example.studentify_android.DataAccess.dao.Interface.IRestauDAO;
import com.example.studentify_android.DataAccess.dao.RestauDAO;
import com.example.studentify_android.Activities.MainMenuView.MainMenuActi;
import com.example.studentify_android.Model.Restaurant;
import com.example.studentify_android.R;
import com.example.studentify_android.Service.CheckIntenetConnection;
import com.example.studentify_android.Utils.ObjectWrapperForBinder;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Response;

public class RestauActi extends AppCompatActivity {

    static final int ADD_EVENT = 1;

    private RecyclerView mRecyclerView;
    private RestauAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private ArrayAdapter<Restaurant> restaurantArrayAdapter;

    @BindView(R.id.restau_progressBar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restau);
        ButterKnife.bind(this);

        restaurantArrayAdapter = new ArrayAdapter<>(this, R.layout.restau_item);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Restaurant");

        if (CheckIntenetConnection.checkConnection(this)) {
            new RestauActi.LoadRestau().execute();
        }else {
            Toast.makeText(this, "Vous n'êtes pas connecté à internet", Toast.LENGTH_LONG).show();
        }
    }



    private class LoadRestau extends AsyncTask<Void, Void, ArrayList<Restaurant>>
    {
        private IRestauDAO restauDAO = new RestauDAO();

        @Override
        protected ArrayList<Restaurant> doInBackground(Void... voids) {

            try {
                Response<ArrayList<Restaurant>> response = restauDAO.getAll();
                if (response.isSuccessful() && response.code() == 200) {
                    return response.body();
                }else {
                    runOnUiThread(() -> {Toast.makeText(RestauActi.this, "Erreur : " + response.code(), Toast.LENGTH_LONG).show();
                        try {
                            Toast.makeText(RestauActi.this, "Échec : " + response.errorBody().string(), Toast.LENGTH_LONG).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                }
            }catch (Exception e)
            {
               e.printStackTrace();
            }
            return new ArrayList<>();
        }

        @Override
        protected void onPostExecute(ArrayList<Restaurant> restaurants)
        {
            if(!restaurants.isEmpty()) {

                progressBar.setAlpha(0f);
                buildRecyclerView(restaurants);
                restaurantArrayAdapter.addAll(restaurants);
            }else {
                progressBar.setAlpha(0f);
                Toast.makeText(RestauActi.this, "Aucun restaurant à afficher", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void buildRecyclerView(ArrayList<Restaurant> restaurants) {
        mRecyclerView = findViewById(R.id.restauRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);

        mAdapter = new RestauAdapter(restaurants);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(position -> {

            final Bundle bundle = new Bundle();
            bundle.putBinder("restauItem", new ObjectWrapperForBinder(restaurants.get(position)));
            startActivity(new Intent(this, RestauInfoActi.class).putExtras(bundle));
        });
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
