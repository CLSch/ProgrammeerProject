/**
 * CurrentSubjectActivity.java
 * Caitlin Schäffers
 * 10580441
 */

package com.example.caitlin.feedbacksave;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.exception.DropboxException;
import com.dropbox.core.v2.DbxClientV2;

import java.io.File;
import java.util.ArrayList;

public class CurrentSubjectActivity extends SuperActivity {
    ListView lvSubject;
    // dit wordt waarschijnlijk een arraylist van feedback objects
    ArrayList<String> photoList = new ArrayList<>();
    CustomFeedbackAdapter adapter;
    DBHelper helper;
    String subject;
    Uri imageUri;
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

        if (helper.subjectItemExists(subject, UserId.getInstance().getUserId())
                && !helper.readAllPhotosPerSubject(subject, UserId.getInstance().getUserId()).isEmpty()) {
            addPhotosToList();
        }

        makeAdapter();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("in onactivityresult", "cursubact");
        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                Log.d("in if requestcode", "onact");

                imageUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),"filename_" +
                String.valueOf(System.currentTimeMillis()) + ".jpg"));

                //use imageUri here to access the image

                //Bundle extras = data.getExtras();

                Log.d("URI",imageUri.toString());

                File file = new File(URI_to_Path.getPath(getApplication(), data.getData()));
                Log.d("dit is file", file.toString());
                if (file != null) {
                    new UploadPhotoAsyncTask(DropBoxClient.getClient(token), file, this).execute();
                }

                //Bitmap bitmap = (Bitmap) extras.get("data");

                // here you will get the image as bitmap
            }
        }
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
        ArrayList<Photo> temp = helper.readAllPhotosPerSubject(subject, UserId.getInstance().getUserId());
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

    public void makePhotoAlertDialog() {
        //TODO: vang SQLite injections? rare tekens af

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.make_photo);

        final EditText input = new EditText(this);
        input.setHint(R.string.feedback_name);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int pos) {
                dialog.cancel();
            }
        });

        builder.setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int pos) {
                if (input.getText().length() == 0) {
                    makePhotoAlertDialog();

                    Log.d("ik zit in if", "onclick dialog");
                    return;
                }

                // TODO CHECK OP DUBBELE NAMEN, FBNAME in onsaveinstance opslaan? voor orientation changes
                FBName = input.getText().toString();
                makePictureIntent();
            }
        });

        builder.show();
    }

    public void savePathToDB(String path) {
        helper.createPhoto(FBName ,path, subject, UserId.getInstance().getUserId());
        currentSubjectIntent();
    }

    /** Is being called from the Asynctask to go back to this activity. */
    public void currentSubjectIntent() {
        Intent currentSubjectIntent = new Intent(this, CurrentSubjectActivity.class);
        // extras?
        currentSubjectIntent.putExtra("subjectName", subject);
        this.startActivity(currentSubjectIntent);
        finish();
    }

    public void deletePhoto(String path, int id) {
        helper.deletePhoto(id, UserId.getInstance().getUserId());
        addPhotosToList();
        adapter.notifyDataSetChanged();
        new DeleteFileAsyncTask(this).execute(path);
    }

    public void makePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        imageUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),"filename_" +
//                String.valueOf(System.currentTimeMillis()) + ".jpg"));
//        Log.d("dit is imageUri", imageUri.toString());
//        takePictureIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, imageUri);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    public void addPhotoFromGalleryClick(View v){
        Intent addPhotoFeedbackIntent = new Intent(this, AddPhotoFeedbackActivity.class);
        addPhotoFeedbackIntent.putExtra("subjectName", subject);
        this.startActivity(addPhotoFeedbackIntent);
    }

    // http://stackoverflow.com/questions/14154104/how-to-take-a-photo-save-it-and-get-the-photo-in-android
    public void makePhotoClick(View v){
        makePhotoAlertDialog();
    }

//    public void addNoteClick(View v){
//        Intent addNoteIntent = new Intent(this, AddNote.class);
//        // geef alle feedback mee
//        //addNoteIntent.putExtra("dbWrapper", wrapper);
//        this.startActivity(addNoteIntent);
//    }
}
