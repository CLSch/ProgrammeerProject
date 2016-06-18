package com.example.caitlin.feedbacksave;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.exception.DropboxException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class AllSubjectsActivity extends SuperActivity {
    ListView lvASubjects;
    ArrayList<String> subjectsList = new ArrayList<>();
    CustomSubjectsAdapter adapter;
    DBHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_subjects);

//        Bundle extras = getIntent().getExtras();
//        subjectName = extras.getString("subjectName");

        if (helper.readAllYears() != null) {
            addSubjectsToList();
        }

//        subjectsList.add("Prog Project"); // HARDCODED !!!!
//        subjectsList.add("Heuristieken"); // HARDCODED !!!!

        makeAdapter(); // ook maken als er niks in staat?
    }

    public void makeAdapter(){
        adapter = new CustomSubjectsAdapter(this, subjectsList);
        lvASubjects = (ListView) findViewById(R.id.lvAllSubjects);
        //listviewToDo.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        assert lvASubjects != null;
        lvASubjects.setAdapter(adapter);
    }

    public void addSubjectsToList() {
        ArrayList<Subject> temp = helper.readAllSubjects();
        for (int i = 0; i < temp.size(); i++) {
            subjectsList.add(temp.get(i).getName());
        }
    }

    public void addSubjectClick(View v){
        Toast.makeText(this, "add a subject", Toast.LENGTH_LONG).show();
        // laat alert dialog zien voor de naam van het subject

        // TODO alertdialog

        helper.createSubject(String name);
        addSubjectsToList();
        adapter.notifyDataSetChanged();
    }
}
