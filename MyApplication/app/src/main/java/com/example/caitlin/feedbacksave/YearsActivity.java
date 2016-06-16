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
    // krijg list terug uit FireBase API?
    ArrayList<String> yearsList = new ArrayList<>();
    CustomYearsAdapter adapter;
    ListView lvYears;
//    final static private String APP_KEY = "8iyoeiil5cpfeay";
//    final static private String APP_SECRET = "ke82ftjb4b07ivk";
    //private boolean loggedIn;
    //private String accessToken;
//    final static private String ACCOUNT_PREFS_NAME = "prefs";
//    final static private String ACCESS_TOKEN_NAME = "ACCESS_TOKEN";
//    AndroidAuthSession session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_years);

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

//        DropBoxAPIManager.getInstance().dBApi = new DropboxAPI<AndroidAuthSession>(session);
//        Log.d("dbApi 1", DropBoxAPIManager.getInstance().dBApi.toString());
        DropBoxAPIManager.getInstance().dBApi.getSession().startOAuth2Authentication(YearsActivity.this);
//        Log.d("dbApi 2", DropBoxAPIManager.getInstance().dBApi.toString());



        yearsList.add("Year 1"); // HARDCODED !!!!
        yearsList.add("Year 2"); // HARDCODED !!!!

        makeAdapter();
    }

    protected void onResume() {
        super.onResume();

        DropBoxAPIManager.getInstance().resume();

        // IS DEZE NODIG???
        //session = dBApi.getSession();

//        if (DropBoxAPIManager.getInstance().dBApi.getSession().authenticationSuccessful()) {
//            try {
//                // Required to complete auth, sets the access token on the session
//                DropBoxAPIManager.getInstance().dBApi.getSession().finishAuthentication();
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
    }

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

    public void makeAdapter(){
        adapter = new CustomYearsAdapter(this, yearsList);
        lvYears = (ListView) findViewById(R.id.lvYear);
        //listviewToDo.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        assert lvYears != null;
        lvYears.setAdapter(adapter);
    }

    public void addYearClick(View v) {
        Toast.makeText(this, "add a year", Toast.LENGTH_SHORT).show();
    }
}
