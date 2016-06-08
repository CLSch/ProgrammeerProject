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

public class RegisterActivity extends SuperActivity {
    EditText userMail;
    EditText passWord;
    EditText passWordC;
    TextView errorMes;
    Firebase mRef;
    DatabaseReference rootRef;
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth = FirebaseAuth.getInstance();

        userMail = (EditText) findViewById(R.id.etEmail);
        passWord = (EditText) findViewById(R.id.etRPassword);
        passWordC = (EditText) findViewById(R.id.etRPasswordControl);
        errorMes = (TextView) findViewById(R.id.tvErrorReg);

        errorMes.setTextColor(Color.RED);

        rootRef = FirebaseDatabase.getInstance().getReference();
        mRef = new Firebase("https://project-1258991994024708208.firebaseio.com");

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

    public void createAccount(final String mail, final String passWord,final String passWordControl) {

        if (!formValidation(mail, passWord, passWordControl)) {
            errorMes.setVisibility(View.VISIBLE);
            // give error
            // make user do it again
            // rode tekst onder de box die visible wordt hierdoor?
            return;
        }

        auth.createUserWithEmailAndPassword(mail, passWord).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //Log.d("createUserWithEmail:onComplete", task.isSuccessful().);

                // If sign in fails, display a message to the user. If sign in succeeds
                // the auth state listener will be notified and logic to handle the
                // signed in user can be handled in the listener.
                if (!task.isSuccessful()) {
                    Log.d("task", task.getException().toString());
                    Toast.makeText(RegisterActivity.this, "Authentication failed creating an account.", Toast.LENGTH_SHORT).show();
                } else {
                    loginUser(mail, passWord);
                }

                // [START_EXCLUDE]
                //hideProgressDialog();
                // [END_EXCLUDE]
            }
        });
    }


    // OF LAAD LOGIN VIA LOGINACTIVITY VERLOPEN??????
//    public void loginUser(String mail, String passWord) {
//        Log.d("signIn:", mail);
//
//        // [START sign_in_with_email]
//        auth.signInWithEmailAndPassword(mail, passWord).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//
//                // If sign in fails, display a message to the user. If sign in succeeds
//                // the auth state listener will be notified and logic to handle the
//                // signed in user can be handled in the listener.
//                if (!task.isSuccessful()) {
//                    Log.d("signInWithEmail", task.getException().toString());
//                    Toast.makeText(RegisterActivity.this, "Authentication failed.",
//                            Toast.LENGTH_SHORT).show();
//                }
//                else {
//                    // laad in juiste database voor volgende activity
//                    Intent yearsIntent = new Intent(RegisterActivity.this, YearsActivity.class);
//                    // krijg iets terug van de API en stop dat in de extra??
//                    //yearsIntent.putExtra("NameTable", listName);
//                    startActivity(yearsIntent);
//                    finish();
//                }
//            }
//        });
//    }


    public void registerClick (View v) {
        // iets laten checken met de firebase? API.
        String um = userMail.getText().toString();
        String pw = passWord.getText().toString();
        String pwc = passWordC.getText().toString();

        createAccount(um, pw, pwc);
    }

    public void loginClick (View v) {
        Intent loginIntent = new Intent(this, LoginActivity.class);
        startActivity(loginIntent);
        finish();
    }

    public boolean formValidation(String mail, String passWord, String passWordControl) {
        // als alle ingevulde velden gecheckt zijn return true
        // als iets fout is false

        if (mail.length() == 0 || passWord.length() == 0 || passWordControl.length() == 0) {
            errorMes.setText(R.string.fill_in_all_fields);
            return false;
        }

        if (!passWord.equals(passWordControl)) {
            errorMes.setText(R.string.passwords_match);
            return false;
        }
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
