package com.example.studentify_android.Activities.Module.Event;

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

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentify_android.DataAccess.dao.EventDAO;
import com.example.studentify_android.DataAccess.dao.Interface.IEventDAO;
import com.example.studentify_android.Activities.MainMenuView.MainMenuActi;
import com.example.studentify_android.Model.Event;
import com.example.studentify_android.R;
import com.example.studentify_android.Service.CheckIntenetConnection;
import com.example.studentify_android.Utils.ObjectWrapperForBinder;

import butterknife.BindView;
import retrofit2.Response;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class EventActi extends AppCompatActivity {

    static final int ADD_EVENT = 1;

    private RecyclerView mRecyclerView;
    private EventAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @BindView(R.id.event_progressBar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Événement");

        if (CheckIntenetConnection.checkConnection(this)) {
            new LoadEvent().execute();
        }else {
            Toast.makeText(this, R.string.error_no_connection, Toast.LENGTH_LONG).show();
        }
    }

    private void buildRecyclerView(ArrayList<Event> events) {
        mRecyclerView = findViewById(R.id.eventRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);

        mAdapter = new EventAdapter(events, this);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(position -> {

            final Bundle bundle = new Bundle();
            bundle.putBinder("eventItem", new ObjectWrapperForBinder(events.get(position)));
            startActivity(new Intent(this, EventInfoActi.class).putExtras(bundle));
        });
    }

    @OnClick(R.id.goToAddEventButton) void onClick() {

        Intent addEvent = new Intent (this, AddEventActi.class);
        startActivityForResult(addEvent, ADD_EVENT);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent eventToAdd){
        super.onActivityResult(requestCode, resultCode, eventToAdd);

        if(resultCode == RESULT_CANCELED) {
            Toast toast = Toast.makeText(getApplicationContext(), "Ajout annulé", Toast.LENGTH_LONG);
            toast.show();
        }else if (resultCode == RESULT_OK) {
            Toast.makeText(this, "Événement ajouté avec succès", Toast.LENGTH_LONG);
        }
    }

    private class LoadEvent extends AsyncTask<Void, Void, ArrayList<Event>>
    {
        private IEventDAO eventDAO = new EventDAO();

        @Override
        protected ArrayList<Event> doInBackground(Void... voids) {

            try {
                Response<ArrayList<Event>> response = eventDAO.getAll();
                if (response.isSuccessful() && response.code() == 200) {
                    return response.body();
                }else {
                    runOnUiThread(() -> {Toast.makeText(EventActi.this, "Erreur : " + response.code(), Toast.LENGTH_LONG).show();
                        try {
                            Toast.makeText(EventActi.this, "Échec : " + response.errorBody().string(), Toast.LENGTH_LONG).show();
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
        protected void onPostExecute(ArrayList<Event> events)
        {
            if(!events.isEmpty()) {

                progressBar.setAlpha(0f);
                buildRecyclerView(events);
            }else {
                progressBar.setAlpha(0f);
                Toast.makeText(EventActi.this, R.string.event_no_to_show, Toast.LENGTH_LONG).show();
            }
        }
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
