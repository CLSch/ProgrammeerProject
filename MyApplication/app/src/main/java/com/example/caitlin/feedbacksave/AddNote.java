package com.example.caitlin.feedbacksave;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.exception.DropboxException;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class AddNote extends SuperActivity {
    EditText etTag;
    DropboxAPI dbApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        Bundle extras = getIntent().getExtras();
        DropBoxAPIWrapper dbWrapper = (DropBoxAPIWrapper) extras.getSerializable("dbWrapper");
        assert dbWrapper != null;
        dbApi = dbWrapper.getDropBoxAPI();

        etTag = (EditText) findViewById(R.id.etTagsNote);
        // verschuif focus van naam naar tags bij add.
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
    }

    // MOET EERST EEN TXT BESTAND OF NOTE MAKEN!!!
    public void addNoteClick(View v) {
        // check of type field en name field niet leeg zijn!!!
        // als er eentje leeg is geef daar een melding van

        File file = new File("working-draft.txt");
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        DropboxAPI.Entry response = null;
        try {
            response = dbApi.putFile("/magnum-opus.txt", inputStream, file.length(), null, null);
        } catch (DropboxException e) {
            e.printStackTrace();
        }
        Log.i("DbExampleLog", "The uploaded file's rev is: " + response.rev);

        //als beiden zijn ingevuld
        Intent currentSubjectIntent = new Intent(this, CurrentSubjectActivity.class);
        this.startActivity(currentSubjectIntent);
        Toast.makeText(this, "Note is toegevoegd (of niet)", Toast.LENGTH_SHORT).show();
        finish();

    }

    public void addTagClick(View v) {
        Toast.makeText(this, "Tag is toegevoegd", Toast.LENGTH_SHORT).show();
        etTag.setText("");
    }
}
