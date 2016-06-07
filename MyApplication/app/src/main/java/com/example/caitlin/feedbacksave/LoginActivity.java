package com.example.caitlin.feedbacksave;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    EditText userName;
    EditText passWord;
    Firebase mRef;
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth.addAuthStateListener(authListener);

        userName = (EditText) findViewById(R.id.etUsername);
        passWord = (EditText) findViewById(R.id.etPassword);

        mRef = new Firebase("https://project-1258991994024708208.firebaseio.com");
        auth = FirebaseAuth.getInstance();

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    // go to auth activity
                    // such as user logging in
                } else {
                    // User is signed out
                }
            }
        };
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        auth.addAuthStateListener(authListener);
//    }

    public void createAccount(String userName, String passWord) {
        mRef.createUser("e-mail", "password", new Firebase.ValueResultHandler<Map<String, Object>>() {
            @Override
            public void onSuccess(Map<String, Object> result) {
                Log.d("created user with uid: ", result.get("uid").toString());
            }

            @Override
            public void onError(FirebaseError firebaseError) {
                // there was an error
            }
        });
    }

    public void loginUser() {
        mRef.authWithPassword("e-mail", "password", new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                Log.d("User ID: ", authData.getUid());
                Log.d("Provider: ", authData.getProvider());
            }
            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                // there was an error
            }
        });
    }

    public void loginClick (View v) {
        // iets laten checken met de firebase? API.
        //userName.getText().toString();
        //passWord.getText().toString();

        // laad in juiste database voor volgende activity
        Intent yearsIntent = new Intent(this, YearsActivity.class);
        // krijg iets terug van de API en stop dat in de extra??
        //yearsIntent.putExtra("NameTable", listName);
        startActivity(yearsIntent);
        finish();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }
}
