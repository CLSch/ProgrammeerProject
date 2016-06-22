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

import java.io.File;

public class SuperActivity extends AppCompatActivity {
//    DropboxAPI<AndroidAuthSession> dBApi;
//    final static private String APP_KEY = "8iyoeiil5cpfeay";
//    final static private String APP_SECRET = "ke82ftjb4b07ivk";
//    //private boolean loggedIn;
//    //private String accessToken;
//    private String token;
//    final static String ACCOUNT_PREFS_NAME = "prefs";
//    static String ACCESS_TOKEN_NAME = "ACCESS_TOKEN";
//    AndroidAuthSession session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_super);

//        SharedPreferences prefs = getSharedPreferences(ACCOUNT_PREFS_NAME, 0);
//        String token = prefs.getString(ACCESS_TOKEN_NAME, null);

//        ActionBar actionBar = getActionBar();
//        actionBar.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.logout_menu, menu);
        return true;
    }

    public void logOutClick(MenuItem item) {
        //LOG OUT FOR DROPBOX

        DropBoxAPIManager.getInstance().logOut();

        Intent yearsActivityIntent = new Intent(this, YearsActivity.class);
        yearsActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        yearsActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(yearsActivityIntent);
        finish();

        // close all activities
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // maak DB file
        DBHelper helper = new DBHelper(this);
        File file = helper.getDatabaseFile();

        new UploadFileAsyncTask(this, file).execute();

        // sla nieuwste versie van DB op
    }
}
