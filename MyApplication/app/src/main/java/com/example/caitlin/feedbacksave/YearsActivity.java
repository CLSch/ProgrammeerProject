package com.example.caitlin.feedbacksave;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.session.AppKeyPair;

import java.util.ArrayList;

public class YearsActivity extends SuperActivity {
    ArrayList<String> yearsList = new ArrayList<>();
    CustomYearsAdapter adapter;
    ListView lvYears;
    DBHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_years);

//        SharedPreferences prefs = getSharedPreferences(ACCOUNT_PREFS_NAME, 0);
//        String token = prefs.getString(ACCESS_TOKEN_NAME, null);

        DropBoxAPIManager.getInstance().dBApi.getSession().startOAuth2Authentication(YearsActivity.this);

        ArrayList<Year> temp = helper.readAllYears();
        if (temp == null) {
            helper.createYear();
        }

        addYearsToList();
        makeAdapter();
    }

    protected void onResume() {
        super.onResume();

        DropBoxAPIManager.getInstance().resume();
    }

    public void makeAdapter(){
        adapter = new CustomYearsAdapter(this, yearsList);
        lvYears = (ListView) findViewById(R.id.lvYear);
        assert lvYears != null;
        lvYears.setAdapter(adapter);
    }

    public void addYearsToList() {
        ArrayList<Year> temp = helper.readAllYears();
        for (int i = 0; i < temp.size(); i++) {
            yearsList.add(temp.get(i).getName());
        }
    }

    public void addYearClick(View v) {
        Toast.makeText(this, "add a year", Toast.LENGTH_SHORT).show();
        helper.createYear();
        addYearsToList();
        adapter.notifyDataSetChanged();
    }
}
