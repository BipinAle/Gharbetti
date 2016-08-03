package com.example.bipin.gharbetti.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInstaller;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bipin.gharbetti.R;
import com.example.bipin.gharbetti.fragments.PaidFrag;
import com.example.bipin.gharbetti.pojos.UserNamePassword;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestAsyncTask;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button loginBt, signUpBt;
    LoginButton fbtLogin;
    CallbackManager mcallbackManager;
    EditText usernameEt;
    EditText passwordEt;
    AccessTokenTracker tokenTracker;
    ProfileTracker profileTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(this);

        setContentView(R.layout.activity_login);

        SharedPreferences preferences=getSharedPreferences("checker",MODE_PRIVATE);
        String loggedInOut=preferences.getString("key",null);
//        if (loggedInOut!=null)
//        {
//            Intent intent=new Intent(this,HomeActivity.class);
//            startActivity(intent);
//            finish();
//        }

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        usernameEt = (EditText) findViewById(R.id.username);
        passwordEt = (EditText) findViewById(R.id.password);
        loginBt = (Button) findViewById(R.id.login);
        signUpBt = (Button) findViewById(R.id.signUp);
        fbtLogin = (LoginButton) findViewById(R.id.login_button);
        mcallbackManager = CallbackManager.Factory.create();
        fbtLogin.setReadPermissions("public_profile");

        tokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {

            }
        };
        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {


            }

        };

        FacebookCallback<LoginResult> callback = new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                if (Profile.getCurrentProfile() == null) {
                    profileTracker = new ProfileTracker() {
                        @Override
                        protected void onCurrentProfileChanged(Profile oldProfile, Profile newProfile) {

                            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                            intent.putExtra("profile", newProfile.getName());
                            Toast.makeText(getApplicationContext(), newProfile.getName() + "", Toast.LENGTH_LONG).show();
                            startActivity(intent);

                            profileTracker.stopTracking();
                        }
                    };
                    // no need to call startTracking() on mProfileTracker
                    // because it is called by its constructor, internally.
                } else {
                    Profile newProfile = Profile.getCurrentProfile();
                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    intent.putExtra("profile", newProfile.getName());
                    startActivity(intent);

                }
                SharedPreferences preferences = getSharedPreferences("checker", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("key", "loggedIn");
                editor.apply();
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), "You cancelled the process", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onError(FacebookException error) {

                Toast.makeText(getApplicationContext(), "Something went error", Toast.LENGTH_LONG).show();

            }
        };

        if (fbtLogin != null)

            fbtLogin.registerCallback(mcallbackManager, callback);

        loginBt.setOnClickListener(this);
        signUpBt.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        tokenTracker.stopTracking();
        profileTracker.stopTracking();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mcallbackManager.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.login) {

            String uName = usernameEt.getText().toString();
            String pass = passwordEt.getText().toString();

            SharedPreferences preferences = getSharedPreferences("SignUp", Context.MODE_PRIVATE);
            String pref_name = preferences.getString("fName", "n/a");
            String pref_pass = preferences.getString("password", "n/a");
            if (uName.equals(pref_name) && pass.equals(pref_pass)) {

                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
            }
        }
        if (v.getId() == R.id.signUp) {
            Intent intent = new Intent(this, SignUpActivity.class);
            startActivity(intent);
        }
    }



}
