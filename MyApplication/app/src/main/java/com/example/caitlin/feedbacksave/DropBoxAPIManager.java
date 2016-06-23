/**
 * DropBoxAPIManager.java
 * Caitlin Schäffers
 * 10580441
 */

package com.example.caitlin.feedbacksave;

import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.session.AppKeyPair;

/**
 * This is a singleton for the DropBoxAPI. It manages all functions of the DropBoxAPI and Dropbox
 * authetication.
 */
public class DropBoxAPIManager {
    private static DropBoxAPIManager ourInstance;
    DropboxAPI<AndroidAuthSession> dBApi;
    AndroidAuthSession session;
    YearsActivity activity;
    final static private String APP_KEY = "8iyoeiil5cpfeay";
    final static private String APP_SECRET = "ke82ftjb4b07ivk";
    private String token;
    final static String ACCOUNT_PREFS_NAME = "prefs";
    static String ACCESS_TOKEN_NAME = "ACCESS_TOKEN";

    private DropBoxAPIManager(YearsActivity activity) {
        this.activity = activity;

        // get token from shared preferences
        SharedPreferences prefs = activity.getSharedPreferences(ACCOUNT_PREFS_NAME, 0);
        String token = prefs.getString(ACCESS_TOKEN_NAME, null);

        // if there's a saved token, restore session with token
        if (token != null) {
            AppKeyPair appKeys = new AppKeyPair(APP_KEY, APP_SECRET);
            session = new AndroidAuthSession(appKeys, token);
            session.setOAuth2AccessToken(token);
        }
        else {
            initialization();
        }
        // make DropboxAPI with session
        dBApi = new DropboxAPI<AndroidAuthSession>(session);
    }

    public static DropBoxAPIManager getInstance() { //empty???
        return ourInstance;
    }

    public static DropBoxAPIManager getInstance(YearsActivity activity) {
        ourInstance = new DropBoxAPIManager(activity);
        return ourInstance;
    }

    /* Create a new session. */
    public void initialization() {
        AppKeyPair appKeys = new AppKeyPair(APP_KEY, APP_SECRET);
        session = new AndroidAuthSession(appKeys);
    }

    /* Return the DropBoxAPI instance. */
    public DropboxAPI getDropBoxApi() {
        return dBApi;
    }

    /* Gets called when the app resumes in the android lifecycle. */
    public void resume() {
        if (dBApi.getSession().authenticationSuccessful()) {
            try {
                // set the access token on the session
                dBApi.getSession().finishAuthentication();

                // set the token
                token = dBApi.getSession().getOAuth2AccessToken();

                // sla deze token op in shared preferences
                storeToken(token);

                // get the user-id of the now logged in dropbox user
                new AccountAsyncTask(DropBoxClient.getClient(token), activity).execute();
            } catch (IllegalStateException e) {
                Log.i("DbAuthLog", "Error authenticating", e);
                Toast.makeText(activity, "Something went wrong while authenticating", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /* Retrieve token from the sharedpreferences. */
    public String getToken() {
        SharedPreferences prefs = activity.getSharedPreferences(ACCOUNT_PREFS_NAME, 0);
        String token = prefs.getString(ACCESS_TOKEN_NAME, null);
        return token;
    }

    /* Store the token in shared preferences. */
    public void storeToken(String token) {
        SharedPreferences prefs = activity.getSharedPreferences(ACCOUNT_PREFS_NAME, 0);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putString(ACCESS_TOKEN_NAME, token);
        edit.commit();
    }

    /* Empty sharedpreferences when used logs out. */
    private void clearKeys() {
        SharedPreferences prefs = activity.getSharedPreferences(ACCOUNT_PREFS_NAME, 0);
        SharedPreferences.Editor edit = prefs.edit();
        edit.clear();
        edit.commit();
    }

    /* Unlink session from DropBoxAPI and forget the token. */
    public void logOut() {
        dBApi.getSession().unlink();
        clearKeys();
    }
}
