package com.example.caitlin.feedbacksave;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
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
    TextView errorMes;
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
        errorMes = (TextView) findViewById(R.id.tvErrorLogin);

        errorMes.setTextColor(Color.RED);

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

    public void loginUser(String eMail, String passWord) {
        Log.d("signIn:", eMail);
        if (!formValidation(eMail, passWord)) {
            errorMes.setVisibility(View.VISIBLE);
            return;
        }

        // [START sign_in_with_email]
        auth.signInWithEmailAndPassword(eMail, passWord).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                // If sign in fails, display a message to the user. If sign in succeeds
                // the auth state listener will be notified and logic to handle the
                // signed in user can be handled in the listener.
                if (!task.isSuccessful()) {
                    Log.w("signInWithEmail", task.getException());
                    Toast.makeText(LoginActivity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                } else {
                    // laad in juiste database voor volgende activity
                    Intent yearsIntent = new Intent(LoginActivity.this, YearsActivity.class);
                    // krijg iets terug van de API en stop dat in de extra??
                    //yearsIntent.putExtra("NameTable", listName);
                    startActivity(yearsIntent);
                    finish();
                }

            }
        });
    }

    public void loginClick (View v) {
        // iets laten checken met de firebase? API.
        String un = userName.getText().toString();
        String pw = passWord.getText().toString();

        loginUser(un, pw);
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


    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }
}
