package com.example.studentify_android.Activities.StartView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studentify_android.Activities.MainMenuView.MainMenuActi;
import com.example.studentify_android.R;
import com.example.studentify_android.Service.AuthSessionService;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StartApp extends AppCompatActivity {

    @BindView(R.id.logo)
    ConstraintLayout logoLayout;
    @BindView(R.id.principale)
    ConstraintLayout princLayout;
    @BindView(R.id.logoAnim)
    ImageView logoAnim;
    @BindView(R.id.logoName)
    TextView logoName;
    @BindView(R.id.start_privacy_text)
    TextView start_privacy_text;
    @BindView(R.id.buttonsLayout)
    LinearLayout buttonsLayout;
    @BindView(R.id.register_button)
    Button register_button;
    @BindView(R.id.google_button)
    SignInButton google_button;
    @BindView(R.id.signIn_button)
    Button signIn_button;

    GoogleSignInClient googleclient;


    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_app);
        ButterKnife.bind(this);

        //Clear of SharedPreferences
        this.getSharedPreferences("moi", Context.MODE_PRIVATE).edit().clear().commit();

        //GoogleSignIn

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.clienID))
                .requestEmail()
                .build();
        googleclient = GoogleSignIn.getClient(this,gso);

        //Animation de début

        //AIDE MÉMOIRE : Changer l'alpha quand l'app sera finie pour enlever ces lignes de codes
        google_button.animate().alpha(0).start();
        signIn_button.animate().alpha(0).start();
        register_button.animate().alpha(0).start();
        //buttonsLayout.animate().alpha(0).start();
        logoName.animate().alpha(0).start();
        start_privacy_text.animate().alpha(0).start();

        logoLayout.setVisibility(LinearLayout.VISIBLE);
        princLayout.setVisibility(LinearLayout.GONE);

        new CountDownTimer(1000,1000){
            @Override
            public void onTick(long millisUntilFinished){}

            @Override
            public void onFinish(){

                // Code qui anime l'apparition des moyens de connexions

                logoLayout.setVisibility(LinearLayout.GONE);
                princLayout.setVisibility(LinearLayout.VISIBLE);

                logoAnim.animate().translationY(-200F).setDuration(1000);
                logoAnim.animate().scaleY(0.5F).setDuration(1000);
                logoAnim.animate().scaleX(0.5F).setDuration(1000);

                logoName.animate().alpha(1F).setDuration(1000).setStartDelay(500);
                start_privacy_text.animate().alpha(1F).setDuration(1000).setStartDelay(500);

                google_button.animate().alpha(1F).setDuration(1000).setStartDelay(380);
                signIn_button.animate().alpha(1F).setDuration(1000).setStartDelay(400);
                register_button.animate().alpha(1F).setDuration(1000).setStartDelay(460);

               // buttonsLayout.animate().alpha(1F).setDuration(1000).setStartDelay(500);


                // METTRE ICI LE CODE SI BESOIN EST


            }
        }.start();
    }

    @Override
    protected void onStart() {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account != null) {
            startActivity(new Intent (this, MainMenuActi.class));
        }
        super.onStart();
    }

    @OnClick(R.id.register_button) void register_button() {
        startActivity(new Intent(this, RegisterActi.class));
    }

    @OnClick(R.id.signIn_button) void signIn_button() {
        startActivity(new Intent(this, SignInActi.class));
    }


    @OnClick(R.id.google_button) void google_button() {
        Intent signInIntent = googleclient.getSignInIntent();
        startActivityForResult(signInIntent, 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 0){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completeTask){
        try {
            GoogleSignInAccount account = completeTask.getResult(ApiException.class);
            //TODO : Vérifier si cette adresse email existe déja en BD. Si oui, lui demander son mot de passe sinon, l'envoyer vers l'inscription
            startActivity(new Intent(this, RegisterActi.class));
        } catch (ApiException e) {
            Log.w("Google", "signInResult: failed code = " + e.getStatusCode());
            Toast.makeText(this, "Failed", Toast.LENGTH_LONG).show();
        }
    }

    @OnClick(R.id.start_privacy_text) void privacy_button() {
        startActivity(new Intent(this, PrivacyActi.class));
    }

}
