package com.example.caitlin.feedbacksave;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    EditText userName;
    EditText passWord;
    Firebase mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        userName = (EditText) findViewById(R.id.etUsername);
        passWord = (EditText) findViewById(R.id.etPassword);

        mRef = new Firebase("https://project-1258991994024708208.firebaseio.com");


    }

    public void createAccount() {
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
}
