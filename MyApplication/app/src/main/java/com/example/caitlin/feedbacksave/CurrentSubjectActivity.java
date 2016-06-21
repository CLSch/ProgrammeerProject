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
        adapter = new CustomFeedbackAdapter(this, photoList, subject);
        lvSubject = (ListView) findViewById(R.id.lvFeedback);
        //listviewToDo.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        assert lvSubject != null;
        lvSubject.setAdapter(adapter);
    }

    public void addPhotosToList() {
        photoList.clear();
        ArrayList<Photo> temp = helper.readAllPhotosPerSubject(subject);
        for (int i = 0; i < temp.size(); i++) {
            photoList.add(temp.get(i).getName());
        }
    }

    /** Alert gets fired on long click and makes it possible to delete files. */
    public void deletePhotoAlertDialog(final String path, final int id) {
        //TODO: vang SQLite injections? rare tekens af

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.edit_feedback));
        builder.setMessage(getString(R.string.choose_change_feedback));

        // Set up the buttons
        builder.setPositiveButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int pos) {
                dialog.cancel();
            }
        });

        builder.setNegativeButton(R.string.delete, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int pos) {
                deletePhoto(path, id);
            }
        });

        builder.show();
    }

    public void deletePhoto(String path, int id) {
        helper.deletePhoto(id);
        addPhotosToList();
        adapter.notifyDataSetChanged();
        new DeleteFileAsyncTask(this).execute(path);
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
