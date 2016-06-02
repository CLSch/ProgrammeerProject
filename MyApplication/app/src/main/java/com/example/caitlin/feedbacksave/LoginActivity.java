package com.example.caitlin.feedbacksave;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {
    EditText userName;
    EditText passWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userName = (EditText) findViewById(R.id.etUsername);
        passWord = (EditText) findViewById(R.id.etPassword);
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
