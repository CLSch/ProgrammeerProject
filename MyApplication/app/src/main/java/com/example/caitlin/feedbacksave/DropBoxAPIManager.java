package com.example.caitlin.feedbacksave;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.exception.DropboxException;
import com.dropbox.client2.session.AppKeyPair;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.logging.Handler;

/**
 * Created by Caitlin on 16-06-16.
 */
public class DropBoxAPIManager {
    private static DropBoxAPIManager ourInstance = new DropBoxAPIManager();

    //fields
    DropboxAPI<AndroidAuthSession> dBApi;
    AndroidAuthSession session;

    final static private String APP_KEY = "8iyoeiil5cpfeay";
    final static private String APP_SECRET = "ke82ftjb4b07ivk";
    private String token;
    final static String ACCOUNT_PREFS_NAME = "prefs";
    static String ACCESS_TOKEN_NAME = "ACCESS_TOKEN";

    /** constructor */
    private DropBoxAPIManager() {

//        SharedPreferences prefs = getPreferences(ACCOUNT_PREFS_NAME, 0);
//        String token = prefs.getString(ACCESS_TOKEN_NAME, null);

        if (token != null) {
//            // initialize key en secret
            //AccessTokenPair accessToken = new AccessTokenPair(key, secret);
            AppKeyPair appKeys = new AppKeyPair(APP_KEY, APP_SECRET);
            session = new AndroidAuthSession(appKeys, token);
            Log.d("in if stat, ses =", session.toString());
//
            //session = new AndroidAuthSession(appKeys);
            session.setOAuth2AccessToken(token);
        }
        else {
            initialization();
        }


        dBApi = new DropboxAPI<AndroidAuthSession>(session);

    }


    /** get the instance */
    public static DropBoxAPIManager getInstance() { //empty???
        return ourInstance;
    }

    public void initialization() {
        AppKeyPair appKeys = new AppKeyPair(APP_KEY, APP_SECRET);
        session = new AndroidAuthSession(appKeys);

    }

    public DropboxAPI getDropBoxApi() {
        return dBApi;
    }

    public void resume() {
        if (dBApi.getSession().authenticationSuccessful()) {
            try {
                // Required to complete auth, sets the access token on the session
                dBApi.getSession().finishAuthentication();

                //AccessTokenPair tokens = session.getAccessTokenPair();
                //TokenPair tokens = session.getAccessTokenPair();
//                String token = session.getOAuth2AccessToken();
//                Log.d("Tokenpair tokens is", token);
//                storeKeys(token);

//                Intent yearsIntent = new Intent(this, YearsActivity.class);
//                this.startActivity(yearsIntent);
//                finish();

                // sla deze token op in shared preferences
                //accessToken = dBApi.getSession().getOAuth2AccessToken();
            } catch (IllegalStateException e) {
                Log.i("DbAuthLog", "Error authenticating", e);
                //Toast.makeText(this, "Something went wrong while authenticating", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public String getToken() {
//        SharedPreferences prefs = getSharedPreferences(ACCOUNT_PREFS_NAME, 0);
//        return prefs.getString(ACCESS_TOKEN_NAME, null);
        return token;
    }

    public void setToken(String token) {
        ACCESS_TOKEN_NAME = token;
    }

//    public void storeKeys(String accessToken) {
//        // Save the access key for later
//
//        // oke maar die boven oncreate zijn final etc, dus wordt dit wel opgeslagen??
//        SharedPreferences prefs = getSharedPreferences(ACCOUNT_PREFS_NAME, 0);
//        SharedPreferences.Editor edit = prefs.edit();
//        edit.putString(ACCESS_TOKEN_NAME, accessToken);
//        edit.commit();
//    }
//
//    private void clearKeys() {
//        SharedPreferences prefs = getSharedPreferences(ACCOUNT_PREFS_NAME, 0);
//        SharedPreferences.Editor edit = prefs.edit();
//        edit.clear();
//        edit.commit();
//    }

    public void logOut() {
        dBApi.getSession().unlink();
//
//        // Clear our stored keys
//        clearKeys();
//        // Change UI state to display logged out version
//        setLoggedIn(false);

        //updateUI(null);
    }


    public void downloadFile(Context context) {
        new DownloadFileAsyncTask2(context).execute("hoi");
    }

//    public void metaData() {
//        new MetaDataAsyncTask(dBApi, pathName, handler).execute();
//    }
}
