package com.example.caitlin.feedbacksave;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class CurrentSubjectActivity extends AppCompatActivity {
    ListView lvSubject;
    // dit wordt waarschijnlijk een arraylist van feedback objects
    ArrayList<String> feedbackList = new ArrayList<>();
    CustomSubjectsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_subject);

        feedbackList.add("Feedback 10-12-14"); // HARDCODED !!!!
        feedbackList.add("FB van Hannah"); // HARDCODED !!!!

        makeAdapter();
    }

    public void makeAdapter(){
        adapter = new CustomSubjectsAdapter(this, feedbackList);
        lvSubject = (ListView) findViewById(R.id.lvFeedback);
        //listviewToDo.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        assert lvSubject != null;
        lvSubject.setAdapter(adapter);
    }

    public void addPhotoClick(View v){
        Toast.makeText(this, "Add Photo from Galery", Toast.LENGTH_SHORT).show();
        Intent addPhotoFeedbackIntent = new Intent(this, AddPhotoFeedback.class);
        // geef alle feedback mee
        //allSubjectsIntent.putExtra("NameTable", tableName);
        this.startActivity(addPhotoFeedbackIntent);
    }

    public void makePhotoClick(View v){
        Toast.makeText(this, "Make Photo", Toast.LENGTH_SHORT).show();
    }

    public void addNoteClick(View v){
        Toast.makeText(this, "Make a note", Toast.LENGTH_SHORT).show();
        Intent addNoteIntent = new Intent(this, AddNote.class);
        // geef alle feedback mee
        //allSubjectsIntent.putExtra("NameTable", tableName);
        this.startActivity(addNoteIntent);
    }
}
