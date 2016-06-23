/**
 * YearsActivity.java
 * Caitlin Schäffers
 * 10580441
 */

package com.example.caitlin.feedbacksave;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class YearsActivity extends SuperActivity {
    ArrayList<String> yearsList = new ArrayList<>();
    CustomYearsAdapter adapter;
    ListView lvYears;
    DBHelper helper;
    int _id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_years);

        DropBoxAPIManager.getInstance(this);

        //SharedPreferences doei = this.getSharedPreferences(prefs, Context.MODE_PRIVATE);
        String token = DropBoxAPIManager.getInstance().getToken();
        if (token == null) {
            DropBoxAPIManager.getInstance().dBApi.getSession().startOAuth2Authentication(YearsActivity.this);
        } else {
            DropBoxAPIManager.getInstance().dBApi.getSession().setOAuth2AccessToken(token);
            new AccountAsyncTask(DropBoxClient.getClient(token), this).execute();
        }
    }

    protected void onResume() {
        super.onResume();

        DropBoxAPIManager.getInstance().resume();
    }

    public void afterAccountAsyncTask() {
        helper = new DBHelper(this);

        Log.d("voor", "de error");
        if (helper.readAllYears(UserId.getInstance().getUserId()).isEmpty()) {
            Log.d("in if statement", helper.readAllYears(UserId.getInstance().getUserId()).toString());
            helper.createYear(UserId.getInstance().getUserId());
        }

        addYearsToList();
        makeAdapter();
    }

    public void makeAdapter(){
        adapter = new CustomYearsAdapter(this, yearsList);
        lvYears = (ListView) findViewById(R.id.lvYear);
        assert lvYears != null;
        lvYears.setAdapter(adapter);
    }

    public void addYearsToList() {
        yearsList.clear();
        ArrayList<Year> temp = helper.readAllYears(UserId.getInstance().getUserId());
        for (int i = 0; i < temp.size(); i++) {
            yearsList.add(temp.get(i).getName());
        }
    }

    public void deleteYearsAlertDialog(final String currentName, int pos) {
        //TODO: vang SQLite injections? rare tekens af
        ArrayList<Year> years = helper.readAllYears(UserId.getInstance().getUserId());
        _id = years.get(pos).getId();
        //// ^^^ dit in een aparte functie?


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
        // MOET DELETE WORDEN
        builder.setNegativeButton(R.string.delete, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int pos) {
                //delete from database
                helper.deleteYear(_id, UserId.getInstance().getUserId());

                // delete from view, is dit nodig of kun je ook de adapter updaten?
                adapter.remove(currentName);
            }
        });

        builder.show();
    }

    public void addYearClick(View v) {
        helper.createYear(UserId.getInstance().getUserId());
        adapter.clear();
        addYearsToList();
        Log.d("DIT IS YEARSLIST", Integer.toString(yearsList.size()));
        adapter.addAll();
        adapter.notifyDataSetChanged();

//        int num = yearsList.size();
//        Log.d("dit is yearslist", yearsList.get(num - 1));
    }
}
