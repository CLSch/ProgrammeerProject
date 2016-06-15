package com.example.caitlin.feedbacksave;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.dropbox.client2.DropboxAPI;

import java.util.ArrayList;

public class AllSubjectsActivity extends SuperActivity {
    ListView lvASubjects;
    ArrayList<String> subjectsList = new ArrayList<>();
    CustomSubjectsAdapter adapter;
    DropboxAPI dbApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_subjects);

        Bundle extras = getIntent().getExtras();
        DropBoxAPIWrapper dbWrapper = (DropBoxAPIWrapper) extras.getSerializable("apiWrapper");
        assert dbWrapper != null;
        dbApi = dbWrapper.getDropBoxAPI();
//        dbApi = extras.getParcelable("dbApi");

        Log.d("dit is dbApi in ASA", dBApi.toString());

        subjectsList.add("Prog Project"); // HARDCODED !!!!
        subjectsList.add("Heuristieken"); // HARDCODED !!!!

        makeAdapter();
    }

    public void makeAdapter(){
        adapter = new CustomSubjectsAdapter(this, subjectsList, dbApi);
        lvASubjects = (ListView) findViewById(R.id.lvAllSubjects);
        //listviewToDo.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        assert lvASubjects != null;
        lvASubjects.setAdapter(adapter);
    }

    public void addSubjectClick(View v){
        Toast.makeText(this, "add a subject", Toast.LENGTH_LONG).show();
        // laat alert dialog zien voor de naam van het subject
    }
}
