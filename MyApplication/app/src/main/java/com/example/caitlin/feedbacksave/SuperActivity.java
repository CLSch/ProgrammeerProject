package com.example.caitlin.feedbacksave;

import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.session.AccessTokenPair;
import com.dropbox.client2.session.AppKeyPair;
import com.dropbox.client2.session.TokenPair;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public class SuperActivity extends AppCompatActivity {
    DropboxAPI<AndroidAuthSession> dBApi;
//    final static private String APP_KEY = "8iyoeiil5cpfeay";
//    final static private String APP_SECRET = "ke82ftjb4b07ivk";
//    //private boolean loggedIn;
//    //private String accessToken;
    final static private String ACCOUNT_PREFS_NAME = "prefs";
    //final static private String ACCESS_TOKEN_NAME = "ACCESS_TOKEN";
//    AndroidAuthSession session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_super);

//        SharedPreferences prefs = getSharedPreferences(ACCOUNT_PREFS_NAME, 0);
//        String token = prefs.getString(ACCESS_TOKEN_NAME, null);


        /// INTERNET ///////////
        //String token = getTokenFromPreferences();
//        if (token != null) {

//            session.setOAuth2AccessToken(token);
//        } else {
//            mDBApi.getSession().startOAuth2Authentication(MyActivity.this);
//        }
        ///////////////////

//        Log.d("token is", token);
//        if (token != null) {
////            // initialize key en secret
//            //AccessTokenPair accessToken = new AccessTokenPair(key, secret);
//            AppKeyPair appKeys = new AppKeyPair(APP_KEY, APP_SECRET);
//            session = new AndroidAuthSession(appKeys, token);
//            Log.d("in if stat, ses =", session.toString());
////
//            //session = new AndroidAuthSession(appKeys);
//            session.setOAuth2AccessToken(token);
//        }
//        else {
//            initialization();
//        }
//
//        dBApi = new DropboxAPI<AndroidAuthSession>(session);
//        Log.d("dbApi 1", dBApi.toString());
//        dBApi.getSession().startOAuth2Authentication(SuperActivity.this);
//        Log.d("dbApi 2", dBApi.toString());

//        ActionBar actionBar = getActionBar();
//        actionBar.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.logout_menu, menu);
        return true;
    }

//    protected void onResume() {
//        super.onResume();
//
//        // IS DEZE NODIG???
//        //session = dBApi.getSession();
//
//        if (dBApi.getSession().authenticationSuccessful()) {
//            try {
//                // Required to complete auth, sets the access token on the session
//                dBApi.getSession().finishAuthentication();
//
//                //AccessTokenPair tokens = session.getAccessTokenPair();
//                //TokenPair tokens = session.getAccessTokenPair();
//                String token = session.getOAuth2AccessToken();
//                Log.d("Tokenpair tokens is", token);
//                storeKeys(token);
//
////                Intent yearsIntent = new Intent(this, YearsActivity.class);
////                this.startActivity(yearsIntent);
////                finish();
//
//                // sla deze token op in shared preferences
//                //accessToken = dBApi.getSession().getOAuth2AccessToken();
//            } catch (IllegalStateException e) {
//                Log.i("DbAuthLog", "Error authenticating", e);
//                Toast.makeText(this, "Something went wrong while authenticating", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }

//    public void initialization() {
//        AppKeyPair appKeys = new AppKeyPair(APP_KEY, APP_SECRET);
//        session = new AndroidAuthSession(appKeys);
//        Log.d("dit is session", session.toString());
//        //dBApi = new DropboxAPI<AndroidAuthSession>(session);
//        //Log.d("in initialization, ses=", session.toString());
//        //dBApi = new DropboxAPI<AndroidAuthSession>(session);
//    }

//    private void storeKeys(String accessToken) {
//        // Save the access key for later
//
//        // oke maar die boven oncreate zijn final etc, dus wordt dit wel opgeslagen??
//        SharedPreferences prefs = getSharedPreferences(ACCOUNT_PREFS_NAME, 0);
//        SharedPreferences.Editor edit = prefs.edit();
//        edit.putString(ACCESS_TOKEN_NAME, accessToken);
//        edit.commit();
//    }

    private void clearKeys() {
        SharedPreferences prefs = getSharedPreferences(ACCOUNT_PREFS_NAME, 0);
        SharedPreferences.Editor edit = prefs.edit();
        edit.clear();
        edit.commit();
    }


    public void logOutClick(MenuItem item) {
        //LOG OUT FOR DROPBOX


//        // Remove credentials from the session
        dBApi.getSession().unlink();
//
//        // Clear our stored keys
        clearKeys();
//        // Change UI state to display logged out version
//        setLoggedIn(false);

        //updateUI(null);
        Intent yearsActivityIntent = new Intent(this, YearsActivity.class);
        startActivity(yearsActivityIntent);
        yearsActivityIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                Intent.FLAG_ACTIVITY_NEW_TASK);
        finish();
        // close all activities
    }


//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_name) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
