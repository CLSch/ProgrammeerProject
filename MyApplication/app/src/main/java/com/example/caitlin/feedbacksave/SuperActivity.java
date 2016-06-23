/**
 * SuperActivity.java
 * Caitlin Schäffers
 * 10580441
 */

package com.example.caitlin.feedbacksave;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

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
    static String userId = "";

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
}
