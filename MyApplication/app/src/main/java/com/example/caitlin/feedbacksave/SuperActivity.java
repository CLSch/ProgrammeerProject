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

/**
 * This activity gets extended by all other activities. It makes logging out possible.
 */
public class SuperActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_super);
    }

    /* Make the logout icon in the app title menu. */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //add items to action bar if it is present
        getMenuInflater().inflate(R.menu.logout_menu, menu);
        return true;
    }

    /* Resets the token and redirects user to the YearsActivity. */
    public void logOutClick(MenuItem item) {
        DropBoxAPIManager.getInstance().logOut();

        // go to the mainactivity (yearsactivity) and close all other axtivities
        Intent yearsActivityIntent = new Intent(this, YearsActivity.class);
        yearsActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        yearsActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(yearsActivityIntent);
        finish();
    }
}
