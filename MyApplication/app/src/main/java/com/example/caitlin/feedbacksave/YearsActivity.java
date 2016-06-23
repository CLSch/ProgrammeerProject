/**
 * YearsActivity.java
 * Caitlin Schäffers
 * 10580441
 */

package com.example.caitlin.feedbacksave;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import java.util.ArrayList;

/**
 * This activity shows the user has added to their profile and makes it possible to delete and add
 * years. The Dropbox authentication screen for logging in gets launched on top of this activity.
 * It gets launched if there's no token.
 */
public class YearsActivity extends SuperActivity {
    ArrayList<String> yearsList = new ArrayList<>();
    CustomYearsAdapter adapter;
    ListView lvYears;
    DBHelper helper;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_years);

        // initialise DropboxAPI<AndroidAuthSession>
        DropBoxAPIManager.getInstance(this);

        // if there's no token start new session with new token, otherwise make a session with existing token
        String token = DropBoxAPIManager.getInstance().getToken();
        if (token == null) {
            DropBoxAPIManager.getInstance().dBApi.getSession().startOAuth2Authentication(YearsActivity.this);
        } else {
            DropBoxAPIManager.getInstance().dBApi.getSession().setOAuth2AccessToken(token);
            new AccountAsyncTask(DropBoxClient.getClient(token), this).execute();
        }
    }

    /* Call the resume function in the DropBoxAPIManager. */
    protected void onResume() {
        super.onResume();

        DropBoxAPIManager.getInstance().resume();
    }

    /** This function gets called after the AccountAsyncTask is done so the database can be initialised
     *  with the dropbox user-id. */
    public void afterAccountAsyncTask() {
        helper = new DBHelper(this);

        // create the first year if there's not a year in the database yet
        if (helper.readAllYears(UserIdSingleton.getInstance().getUserId()).isEmpty()) {
            helper.createYear(UserIdSingleton.getInstance().getUserId());
        }

        addYearsToList();
        makeAdapter();
    }

    /* Set the CustomYearsAdapter. */
    public void makeAdapter(){
        adapter = new CustomYearsAdapter(this, yearsList);
        lvYears = (ListView) findViewById(R.id.lvYear);
        assert lvYears != null;
        lvYears.setAdapter(adapter);
    }

    /* Fill the yearsList with the names of the yearitems in the Database. */
    public void addYearsToList() {
        yearsList.clear();
        ArrayList<Year> temp = helper.readAllYears(UserIdSingleton.getInstance().getUserId());
        for (int i = 0; i < temp.size(); i++) {
            yearsList.add(temp.get(i).getName());
        }
    }

    /* Show an alertdialog on long click to delete years. */
    public void deleteYearsAlertDialog(final String currentName, int pos) {
        // get the id of the clicked year
        ArrayList<Year> years = helper.readAllYears(UserIdSingleton.getInstance().getUserId());
        id = years.get(pos).getId();

        // set up the alertdialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.delete_year));
        builder.setMessage(getString(R.string.delete_warning));

        // Set up the buttons
        builder.setPositiveButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int pos) {
                dialog.cancel();
            }
        });
        builder.setNegativeButton(R.string.delete, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int pos) {
                //delete from database
                helper.deleteYear(id, UserIdSingleton.getInstance().getUserId());

                // delete from view
                adapter.remove(currentName);
            }
        });

        builder.show();
    }

    /* Add a year to the database and view after clicking the add button. */
    public void addYearClick(View v) {
        helper.createYear(UserIdSingleton.getInstance().getUserId());
        adapter.clear();
        addYearsToList();
        adapter.addAll();
        adapter.notifyDataSetChanged();
    }
}
