/**
 * CurrentSubjectActivity.java
 * Caitlin Schäffers
 * 10580441
 */

package com.example.caitlin.feedbacksave;

import android.content.DialogInterface;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;

/**
 * Activity that shows all feedback belonging to a subject. Handles making a picture and adding
 * a picture from the gallery to add it as feedback to Dropbox.
 */
public class CurrentSubjectActivity extends SuperActivity {
    ListView lvSubject;
    ArrayList<String> photoList = new ArrayList<>();
    CustomFeedbackAdapter adapter;
    DBHelper helper;
    String subject;
    String token;
    String FBName;
    private static int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_subject);

        helper = new DBHelper(this);
        token = DropBoxAPIManager.getInstance().getToken();

        Bundle extras = getIntent().getExtras();
        subject = extras.getString("subjectName");

        // get photo feedback from database if there are photo items in the database
        if (helper.subjectItemExists(subject, UserIdSingleton.getInstance().getUserId())
                && !helper.readAllPhotosPerSubject(subject, UserIdSingleton.getInstance().getUserId()).isEmpty()) {
            addPhotosToList();
        }

        // remember feedback name given by user on orientation change
        if (savedInstanceState != null) {
            FBName = savedInstanceState.getString("FBName");
        }

        makeAdapter();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                // make file with the URI returned in data
                File file = new File(URI_to_Path.getPath(getApplication(), data.getData()));
                if (file != null) {
                    new UploadPhotoAsyncTask(DropBoxClient.getClient(token), file, this).execute();
                }
            }
        }
    }

    /* Set up the CustomFeedbackAdapter. */
    public void makeAdapter(){
        adapter = new CustomFeedbackAdapter(this, photoList, subject);
        lvSubject = (ListView) findViewById(R.id.lvFeedback);
        assert lvSubject != null;
        lvSubject.setAdapter(adapter);
    }

    /* Get photo items from the database and put them in photoList. */
    public void addPhotosToList() {
        photoList.clear();
        ArrayList<Photo> temp = helper.readAllPhotosPerSubject(subject, UserIdSingleton.getInstance().getUserId());
        for (int i = 0; i < temp.size(); i++) {
            photoList.add(temp.get(i).getName());
        }
    }

    /* Alert dialog gets fired on longclick and makes it possible to delete files. */
    public void deletePhotoAlertDialog(final String path, final int id) {
        // set up alert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.edit_feedback));
        builder.setMessage(getString(R.string.choose_change_feedback));

        // set up the buttons
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

    public void makePhotoAlertDialog(String hint) {
        // set up alert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.make_photo);

        // set up inputfield for dialog
        final EditText input = new EditText(this);
        input.setHint(hint);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // set up the buttons
        builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int pos) {
                // force user to add a name
                if (input.getText().length() == 0) {
                    makePhotoAlertDialog(getString(R.string.empty_name_error));
                    return;
                }

                FBName = input.getText().toString();
                makePictureIntent();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int pos) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    /* Save the dropbox path of the uploaded photo to database. */
    public void savePathToDB(String path) {
        helper.createPhoto(FBName ,path, subject, UserIdSingleton.getInstance().getUserId());
        currentSubjectIntent();
    }

    /* Gets called from the Asynctask to go back to this activity. */
    public void currentSubjectIntent() {
        Intent currentSubjectIntent = new Intent(this, CurrentSubjectActivity.class);
        currentSubjectIntent.putExtra("subjectName", subject);
        this.startActivity(currentSubjectIntent);
        finish();
    }

    /* Delete photo feedback from the database and view. */
    public void deletePhoto(String path, int id) {
        helper.deletePhoto(id, UserIdSingleton.getInstance().getUserId());
        addPhotosToList();
        adapter.notifyDataSetChanged();
        new DeleteFileAsyncTask(this).execute(path);
    }

    /* Start the camera app on the phone. */
    public void makePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    /* Go to AddPhotoFeedbackActivity to create a name for the photo. */
    public void addPhotoFromGalleryClick(View v){
        Intent addPhotoFeedbackIntent = new Intent(this, AddPhotoFeedbackActivity.class);
        addPhotoFeedbackIntent.putExtra("subjectName", subject);
        this.startActivity(addPhotoFeedbackIntent);
    }

    /* Make an alert dialog when the user wants to take a picture to get a name for it. */
    public void makePhotoClick(View v){
        makePhotoAlertDialog(getString(R.string.feedback_name));
    }

    /* Save the name the user gave for the picture for when the orientation changes. */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("FBName", FBName);
    }
}
