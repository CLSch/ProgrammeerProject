package com.example.caitlin.feedbacksave;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.provider.SyncStateContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.session.AccessTokenPair;
import com.dropbox.client2.session.AppKeyPair;
import com.dropbox.client2.session.TokenPair;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


import java.util.Map;



// https://github.com/firebase/quickstart-android/blob/master/auth/app/src/main/java/com/google/firebase/quickstart/auth/EmailPasswordActivity.java#L101-L107
// google samples gebruikt

// SETTINGS KNOP TOEVOEGEN MET LOGOUT????
public class LoginActivity extends AppCompatActivity {
    EditText userName;
    EditText passWord;
    TextView errorMes;
    private DropboxAPI<AndroidAuthSession> mDBApi;
    final static private String APP_KEY = "8iyoeiil5cpfeay";
    final static private String APP_SECRET = "ke82ftjb4b07ivk";
    private boolean loggedIn;
    private String accessToken;
    final static private String ACCOUNT_PREFS_NAME = "prefs";
    final static private String ACCESS_KEY_NAME = "ACCESS_KEY";
    final static private String ACCESS_SECRET_NAME = "ACCESS_SECRET";
    //private DropboxAPI mApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Firebase.setAndroidContext(this);
//        AndroidAuthSession session = buildSession();
        AndroidAuthSession session = initialization();
        mDBApi = new DropboxAPI<AndroidAuthSession>(session);
        setContentView(R.layout.activity_login);

        //initializationFunction();

        // dropbox token = J7VUX6zVny0AAAAAAAAIxeb1NXIFS4GRaTAcodjxzO6FnujH-S3JHrttrDqFyBfO

        // deze in superactivity??? deze start de authenticatie?
        mDBApi.getSession().startOAuth2Authentication(LoginActivity.this);

        userName = (EditText) findViewById(R.id.etUsername);
        passWord = (EditText) findViewById(R.id.etLPassword);
        errorMes = (TextView) findViewById(R.id.tvErrorLogin);

        errorMes.setTextColor(Color.RED);

//        authListener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                FirebaseUser user = firebaseAuth.getCurrentUser();
//                if (user != null) {
//                    // User is signed in
//                    // go to auth activity
//                    // such as user logging in
//                } else {
//                    // User is signed out
//                }
//            }
//        };
    }

    protected void onResume() {
        super.onResume();

        if (mDBApi.getSession().authenticationSuccessful()) {
            try {
                // Required to complete auth, sets the access token on the session
                mDBApi.getSession().finishAuthentication();
                TokenPair tokens = mDBApi.getSession().getAccessTokenPair();
                storeKeys(tokens.key, tokens.secret);

                // sla deze token op in shared preferences
                accessToken = mDBApi.getSession().getOAuth2AccessToken();
            } catch (IllegalStateException e) {
                Log.i("DbAuthLog", "Error authenticating", e);
                Toast.makeText(this, "Something went wrong while authenticating", Toast.LENGTH_SHORT).show();
            }
        }
    }


    // on resume van tutorial

//    onResume = true;
//    @Override
//    protected void onResume() {
//        AndroidAuthSession session = mApi.getSession();
//        if (session.authenticationSuccessful()) {
//            try {
//                session.finishAuthentication();
//                TokenPair tokens = session.getAccessTokenPair();
//                storeKeys(tokens.key, tokens.secret);
//                setLoggedIn(onResume);
//            } catch (IllegalStateException e) {
//                showToast("Couldn't authenticate with Dropbox:" + e.getLocalizedMessage());
//            }
//        }
//        super.onResume();
//    }
//    private void storeKeys(String key, String secret) {
//        SharedPreferences prefs = getSharedPreferences(Constants.ACCOUNT_PREFS_NAME, 0);
//        Editor edit = prefs.edit();
//        edit.putString(Constants.ACCESS_KEY_NAME, key);
//        edit.putString(Constants.ACCESS_SECRET_NAME, secret);
//        edit.commit();
//    }


    public AndroidAuthSession initialization() {
        AppKeyPair appKeys = new AppKeyPair(APP_KEY, APP_SECRET);
        AndroidAuthSession session = new AndroidAuthSession(appKeys);
        mDBApi = new DropboxAPI<AndroidAuthSession>(session);
        return session;
    }

    private void storeKeys(String key, String secret) {
        // Save the access key for later

        // oke maar die boven oncreate zijn final etc, dus wordt dit wel opgeslagen??
        SharedPreferences prefs = getSharedPreferences(ACCOUNT_PREFS_NAME, 0);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putString(ACCESS_KEY_NAME, key);
        edit.putString(ACCESS_SECRET_NAME, secret);
        edit.commit();
    }

    private void clearKeys() {
        SharedPreferences prefs = getSharedPreferences(ACCOUNT_PREFS_NAME, 0);
        SharedPreferences.Editor edit = prefs.edit();
        edit.clear();
        edit.commit();
    }

//    private AndroidAuthSession buildSession() {
//        AppKeyPair appKeyPair = new AppKeyPair(Constants.DROPBOX_APP_KEY,Constants.DROPBOX_APP_SECRET);
//        AndroidAuthSession session;
//        String[] stored = getKeys();
//        if (stored != null) {
//            AccessTokenPair accessToken = new AccessTokenPair(stored[0],stored[1]);
//            session = new AndroidAuthSession(appKeyPair, Constants.ACCESS_TYPE,accessToken);
//        } else {
//            session = new AndroidAuthSession(appKeyPair, SyncStateContract.Constants.ACCESS_TYPE);
//        }
//        return session;
//    }

    // SLA DB TOKEN OP IN SHARED PREFERENCES


//    public void loginUser(String eMail, String passWord) {
//        Log.d("signIn:", eMail);

        // [START sign_in_with_email]
//        auth.signInWithEmailAndPassword(eMail, passWord).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//
//                // If sign in fails, display a message to the user. If sign in succeeds
//                // the auth state listener will be notified and logic to handle the
//                // signed in user can be handled in the listener.
//                if (!task.isSuccessful()) {
//                    Log.d("signInWithEmail", task.getException().toString());
//                    Toast.makeText(LoginActivity.this, "Authentication failed.",
//                            Toast.LENGTH_SHORT).show();
//                } else {
//                    // laad in juiste database voor volgende activity
//                    Intent yearsIntent = new Intent(LoginActivity.this, YearsActivity.class);
//                    // krijg iets terug van de API en stop dat in de extra??
//                    //yearsIntent.putExtra("NameTable", listName);
//                    startActivity(yearsIntent);
//                    finish();
//                }
//
//            }
//        });
//    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        auth.addAuthStateListener(authListener);
//    }

//    public void loginUser(String eMail, String passWord) {
//        Log.d("signIn:", eMail);
//        if (!formValidation(eMail, passWord)) {
//            errorMes.setVisibility(View.VISIBLE);
//            return;
//        }
//
//        // [START sign_in_with_email]
//        auth.signInWithEmailAndPassword(eMail, passWord).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//
//                // If sign in fails, display a message to the user. If sign in succeeds
//                // the auth state listener will be notified and logic to handle the
//                // signed in user can be handled in the listener.
//                if (!task.isSuccessful()) {
//                    Log.d("signInWithEmail", task.getException().toString());
//                    Toast.makeText(LoginActivity.this, "Authentication failed.",
//                            Toast.LENGTH_SHORT).show();
//                } else {
//                    // laad in juiste database voor volgende activity
//                    Intent yearsIntent = new Intent(LoginActivity.this, YearsActivity.class);
//                    // krijg iets terug van de API en stop dat in de extra??
//                    //yearsIntent.putExtra("NameTable", listName);
//                    startActivity(yearsIntent);
//                    finish();
//                }
//
//            }
//        });
//    }

    public void loginClick (View v) {
        // iets laten checken met de firebase? API.
        String un = userName.getText().toString();
        String pw = passWord.getText().toString();

        if (!formValidation(un, pw)) {
            errorMes.setVisibility(View.VISIBLE);
            return;
        }

        Toast.makeText(LoginActivity.this, "in loginclick", Toast.LENGTH_SHORT).show();
        //loginUser(un, pw);
    }

    public void registerClick (View v) {
        Intent registerIntent = new Intent(this, RegisterActivity.class);
        startActivity(registerIntent);
        finish();
    }

    public boolean formValidation(String mail, String passWord) {
        // als alle ingevulde velden gecheckt zijn return true
        // als iets fout is false

        if (mail.length() == 0 || passWord.length() == 0) {
            errorMes.setText(R.string.fill_in_all_fields);
            return false;
        }

        // check password en username met de database???
//        if (!passWord.equals(passWordControl)) {
//            errorMes.setText(R.string.passwords_match);
//            return false;
//        }
        return true;
    }

//    private void logOut() {
//        // Remove credentials from the session
//        mApi.getSession().unlink();
//
//        // Clear our stored keys
//        clearKeys();
//        // Change UI state to display logged out version
//        setLoggedIn(false);
//    }

//    @Override
//    public void onStop() {
//        super.onStop();
////        if (authListener != null) {
////            auth.removeAuthStateListener(authListener);
////        }
//    }
}
