package com.example.studentify_android.activities.mainMenuView;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.studentify_android.activities.module.event.EventActi;
import com.example.studentify_android.activities.module.restaurant.RestauActi;
import com.example.studentify_android.R;
import com.example.studentify_android.activities.startView.StartApp;
import com.example.studentify_android.service.AuthSessionService;
import com.example.studentify_android.service.GoogleService;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainMenuActi extends AppCompatActivity {


    @BindView(R.id.main_menu_profilIcon)
    ImageView iconImage;

    @BindView(R.id.eventButton)
    ImageButton eventButton;

    @BindView(R.id.home_progressBar)
    ProgressBar progressBar;

    @BindView(R.id.home_warning)
    ImageView warningImage;

    private Activity activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        ButterKnife.bind(this);
        activity = this;
        GoogleSignInAccount account = GoogleService.getAccount();

        if(account != null) {
            progressBar.setAlpha(0f);
            Uri personPhoto = account.getPhotoUrl();
            Glide.with(this).load(personPhoto).override(300,300).into(iconImage);

        }else {
            Picasso.get().load(AuthSessionService.getUserPicture(this)).resize(150, 150).into(iconImage);

            progressBar.setAlpha(0f);
        }
    }

    private void signOut() {
        AuthSessionService.disconnectUser(this);
        GoogleService.getClient().signOut()
                .addOnCompleteListener(this, (task) -> {
                    String toastMessage = this.getResources().getString(R.string.main_signOut);
                    Toast.makeText(this, toastMessage, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(this.getApplicationContext(), StartApp.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                });
    }


    @OnClick(R.id.main_logOff_button) void disconnected() {
        signOut();
    }

    @OnClick(R.id.main_setting_button) void setting() {
        startActivity(new Intent(this, SettingsActivity.class));
    }

    @OnClick(R.id.main_menu_profilIcon) void profile() {

        startActivity(new Intent(this, SettingsActivity.class));
    }

    @OnClick(R.id.eventButton) void goToEventActi() {

        startActivity(new Intent(this, EventActi.class));
    }

    @OnClick(R.id.restauButton) void goToRestauActi() {

        startActivity(new Intent(this, RestauActi.class));
    }

}
