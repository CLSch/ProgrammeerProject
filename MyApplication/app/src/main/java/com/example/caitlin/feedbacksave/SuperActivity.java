package com.example.caitlin.feedbacksave;

import android.app.ActionBar;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SuperActivity extends AppCompatActivity {
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_super);

        auth = FirebaseAuth.getInstance();

//        ActionBar actionBar = getActionBar();
//        actionBar.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.logout_menu, menu);
        return true;
    }

    public void loginUser(String eMail, String passWord) {
        Log.d("signIn:", eMail);

        // [START sign_in_with_email]
        auth.signInWithEmailAndPassword(eMail, passWord).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                // If sign in fails, display a message to the user. If sign in succeeds
                // the auth state listener will be notified and logic to handle the
                // signed in user can be handled in the listener.
                if (!task.isSuccessful()) {
                    Log.d("signInWithEmail", task.getException().toString());
                    Toast.makeText(SuperActivity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                } else {
                    // laad in juiste database voor volgende activity
                    Intent yearsIntent = new Intent(SuperActivity.this, YearsActivity.class);
                    // krijg iets terug van de API en stop dat in de extra??
                    //yearsIntent.putExtra("NameTable", listName);
                    startActivity(yearsIntent);
                    finish();
                }

            }
        });
    }

    public void logOutClick(MenuItem item) {
        auth.signOut();
        //updateUI(null);
        Intent logOutIntent = new Intent(this, LoginActivity.class);
        startActivity(logOutIntent);
        logOutIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                Intent.FLAG_ACTIVITY_NEW_TASK);
        finish();
        // close all activities
    }


//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_name) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
