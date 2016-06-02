package com.example.caitlin.feedbacksave;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddNote extends AppCompatActivity {
    EditText etTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        etTag = (EditText) findViewById(R.id.etTagsNote);
        // verschuif focus van naam naar tags bij add.
    }

    public void addNoteClick (View v){
        // check of type field en name field niet leeg zijn!!!
        // als er eentje leeg is geef daar een melding van

        //als beiden zijn ingevuld
        Intent currentSubjectIntent = new Intent(this, CurrentSubjectActivity.class);
        this.startActivity(currentSubjectIntent);
        Toast.makeText(this, "Note is toegevoegd (of niet)", Toast.LENGTH_SHORT).show();
        finish();

    }

    public void addTagClick(View v){
        Toast.makeText(this, "Tag is toegevoegd", Toast.LENGTH_SHORT).show();
        etTag.setText("");
    }
}
