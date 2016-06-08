package com.example.caitlin.feedbacksave;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;



// https://github.com/firebase/quickstart-android/blob/master/auth/app/src/main/java/com/google/firebase/quickstart/auth/EmailPasswordActivity.java#L101-L107
// google samples gebruikt
public class LoginActivity extends AppCompatActivity {
    EditText userName;
    EditText passWord;
    Firebase mRef;
    DatabaseReference rootRef;
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth.addAuthStateListener(authListener);

        userName = (EditText) findViewById(R.id.etUsername);
        passWord = (EditText) findViewById(R.id.etPassword);

        rootRef = FirebaseDatabase.getInstance().getReference();
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
        auth.createUserWithEmailAndPassword(userName, passWord)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //Log.d("createUserWithEmail:onComplete", task.isSuccessful().);

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // [START_EXCLUDE]
                        //hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });



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

    public boolean formValidation () {
        Boolean isValid = true;
        // als alle ingevulde velden gecheckt zijn return true
        // als iets fout is false
        return isValid;
    }


    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }
}
