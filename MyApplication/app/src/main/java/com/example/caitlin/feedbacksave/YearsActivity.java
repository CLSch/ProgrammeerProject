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
import com.dropbox.core.v2.DbxClientV2;

import java.util.ArrayList;

public class YearsActivity extends SuperActivity {
    ArrayList<String> yearsList = new ArrayList<>();
    CustomYearsAdapter adapter;
    ListView lvYears;
    DBHelper helper;
    String userId;
    private DbxClientV2 dbxClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_years);

        DropBoxAPIManager.getInstance(this);

//        SharedPreferences prefs = getSharedPreferences(ACCOUNT_PREFS_NAME, 0);
//        String token = prefs.getString(ACCESS_TOKEN_NAME, null);

        String token = DropBoxAPIManager.getInstance().getToken();
        if (token == null) {
            DropBoxAPIManager.getInstance().dBApi.getSession().startOAuth2Authentication(YearsActivity.this);
        } else {
            DropBoxAPIManager.getInstance().dBApi.getSession().setOAuth2AccessToken(token);
            new AccountAsyncTask(DropBoxClient.getClient(token), this).execute();
        }

//        int num = yearsList.size();
//        Log.d("dit is yearslist", yearsList.get(num - 1));

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
