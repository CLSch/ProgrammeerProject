package com.example.caitlin.feedbacksave;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class CurrentSubjectActivity extends SuperActivity {
    ListView lvSubject;
    // dit wordt waarschijnlijk een arraylist van feedback objects
    ArrayList<String> feedbackList = new ArrayList<>();
    CustomFeedbackAdapter adapter;
    //StorageReference photoRef;
    //String photorefPath;
    String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_subject);

        Bundle extras = getIntent().getExtras();

        path = "/photos/40146";

//        if (extras.getString("path") == null) {
//            //photorefPath = "photos/40146";
//            path = "/photos/40146";
//        }
//        else {
//            //photorefPath = extras.getString("photorefPath");
//            //photoRef = extras.getParcelable("photoRef");
//            //uploadUri = extras.get
//            path = extras.getString("path");
//        }

        feedbackList.add("Feedback 10-12-14"); // HARDCODED !!!!
        feedbackList.add("FB van Hannah"); // HARDCODED !!!!

        makeAdapter();
    }

    public void makeAdapter(){
        adapter = new CustomFeedbackAdapter(this, feedbackList, path);
        lvSubject = (ListView) findViewById(R.id.lvFeedback);
        //listviewToDo.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        assert lvSubject != null;
        lvSubject.setAdapter(adapter);
    }

    public void addPhotoClick(View v){
        //Toast.makeText(this, "Add Photo from Galery", Toast.LENGTH_SHORT).show();
        Intent addPhotoFeedbackIntent = new Intent(this, AddPhotoFeedback.class);
        // geef alle feedback mee
        //allSubjectsIntent.putExtra("NameTable", tableName);
        this.startActivity(addPhotoFeedbackIntent);
    }

    public void makePhotoClick(View v){
        Toast.makeText(this, "Make Photo", Toast.LENGTH_SHORT).show();
    }

    public void addNoteClick(View v){
        Intent addNoteIntent = new Intent(this, AddNote.class);
        // geef alle feedback mee
        //allSubjectsIntent.putExtra("NameTable", tableName);
        this.startActivity(addNoteIntent);
    }
}
