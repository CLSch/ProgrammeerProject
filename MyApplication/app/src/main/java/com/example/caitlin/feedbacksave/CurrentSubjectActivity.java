package com.example.caitlin.feedbacksave;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.exception.DropboxException;
import com.dropbox.core.v2.DbxClientV2;

import java.util.ArrayList;

public class CurrentSubjectActivity extends SuperActivity {
    ListView lvSubject;
    // dit wordt waarschijnlijk een arraylist van feedback objects
    ArrayList<String> photoList = new ArrayList<>();
    CustomFeedbackAdapter adapter;
    DBHelper helper;
    String subject;
    int _id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_subject);

        helper = new DBHelper(this);

        Bundle extras = getIntent().getExtras();
        subject = extras.getString("subjectName");

        if (helper.subjectItemExists(subject) && !helper.readAllPhotosPerSubject(subject).isEmpty()) {
            addPhotosToList();
        }

        makeAdapter();
    }

    public void makeAdapter(){
        adapter = new CustomFeedbackAdapter(this, photoList);
        lvSubject = (ListView) findViewById(R.id.lvFeedback);
        //listviewToDo.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        assert lvSubject != null;
        lvSubject.setAdapter(adapter);
    }

    public void addPhotosToList() {
        ArrayList<Photo> temp = helper.readAllPhotosPerSubject(subject);
        for (int i = 0; i < temp.size(); i++) {
            photoList.add(temp.get(i).getName());
        }
    }

    public void deletePhotoAlertDialog(final String currentName, int pos) {
        //TODO: vang SQLite injections? rare tekens af
        ArrayList<Photo> subjects = helper.readAllPhotosPerSubject(subject);
        _id = subjects.get(pos).getId();
        //// ^^^ dit in een aparte functie?


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.edit_feedback));
        builder.setMessage(getString(R.string.choose_change_feedback));

        // Set up the buttons
        // MOET CHANGE WORDEN
        builder.setPositiveButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int pos) {
                dialog.cancel();
                //makeNewSubjectAlertDialog(getString(R.string.create_new_name));
            }
        });
//        // MOET CANCEL WORDEN
//        builder.setNeutralButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int pos) {
//                dialog.cancel();
//            }
//        });

        // MOET DELETE WORDEN
        builder.setNegativeButton(R.string.delete, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int pos) {
                //delete from database
                deletePhoto("");
//                helper.deleteSubject(_id);
//
//                // delete from view, is dit nodig of kun je ook de adapter updaten?
//                adapter.remove(currentName);
//                Toast.makeText(CurrentSubjectActivity.this, "item is deleted" , Toast.LENGTH_SHORT).show();
            }
        });

        builder.show();
    }

    public void deletePhoto(String path) {
        new DeleteFileAsyncTask(this).execute("");
    }

    public void addPhotoFromGalleryClick(View v){
        Intent addPhotoFeedbackIntent = new Intent(this, AddPhotoFeedbackActivity.class);
        addPhotoFeedbackIntent.putExtra("subjectName", subject);
        this.startActivity(addPhotoFeedbackIntent);
    }

    public void makePhotoClick(View v){
        //deletePhoto("");
    }

//    public void addNoteClick(View v){
//        Intent addNoteIntent = new Intent(this, AddNote.class);
//        // geef alle feedback mee
//        //addNoteIntent.putExtra("dbWrapper", wrapper);
//        this.startActivity(addNoteIntent);
//    }
}
