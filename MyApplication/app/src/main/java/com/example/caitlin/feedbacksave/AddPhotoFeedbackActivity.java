package com.example.caitlin.feedbacksave;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.exception.DropboxException;
import com.dropbox.client2.exception.DropboxUnlinkedException;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.prefs.Preferences;

// https://www.sitepoint.com/adding-the-dropbox-api-to-an-android-app/

public class AddPhotoFeedbackActivity extends SuperActivity {
    EditText etTag;
    EditText etFBName;
    TextView errorMes;
    String FBName;
    String tags;
    DBHelper helper;
    String subject;
    boolean checkPerm;
    private static int RESULT_GALLERY = 1;
    private static int SELECT_FILE = 1;
    private static int IMAGE_REQUEST_CODE = 1;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_photo_feedback);

        helper = new DBHelper(this);

        Bundle extras = getIntent().getExtras();
        subject = extras.getString("subjectName");

        Log.d("onCreate", DropBoxAPIManager.getInstance().toString());
        Log.d("onCreate", DropBoxAPIManager.getInstance().getToken().toString());
        token = DropBoxAPIManager.getInstance().getToken();

        etTag = (EditText) findViewById(R.id.etTagsPhoto);
        etFBName = (EditText) findViewById(R.id.etFBName);

        errorMes = (TextView) findViewById(R.id.tvErrorPFB);

        assert errorMes != null;
        errorMes.setTextColor(Color.RED);

       checkPerm = Utility.checkPermission(AddPhotoFeedbackActivity.this);
    }

    /** When the user chooses a picture from the gallery this method gets called. */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("tot hier gekomen", "1");
        if (resultCode != RESULT_OK || data == null) return;
        Log.d("tot hier gekomen", "2");
        // Check which request we're responding to
        if (requestCode == IMAGE_REQUEST_CODE) {
            Log.d("tot hier gekomen", "3");
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                Log.d("tot hier gekomen", "4");
                //Image URI received
                File file = new File(URI_to_Path.getPath(getApplication(), data.getData()));
                if (file != null) {
                    Log.d("tot hier gekomen", "5");
                    // ADD FILE PATH TO DB
                    helper.createPhoto(FBName ,URI_to_Path.getPath(getApplication(), data.getData()), subject);
                    //Initialize UploadTask
                    Log.d("tot hier gekomen", "rare crash");
                    Log.d("Addphoto onactivity", token);
                    new UploadPhotoAsyncTask(DropBoxClient.getClient(token), file, this).execute();
                }
            }
        }
    }

    public boolean formValidation(String name) {
        // als alle ingevulde velden gecheckt zijn return true
        // als iets fout is false

        if (name.length() == 0) {
            errorMes.setText(R.string.fill_in_all_fields);
            return false;
        }

        ArrayList<Photo> temp = helper.readAllPhotosPerSubject(subject);
        for (int i = 0; i < temp.size(); i++) {
            if (name.equals(temp.get(i).getName())) {
                Log.d("in if click", "dialog");
                errorMes.setText(R.string.unique_name_error);
                return false;
                //input.setHint("Write here a unique subject name");
            }
        }
        return true;
    }

    public void addFeedbackClick (View v){

        if (!formValidation(etFBName.getText().toString())) {
            return;
        }

        FBName = etFBName.getText().toString();

        if (checkPerm) {
            galleryIntent();
        }
        // check of type field en name field niet leeg zijn!!!
        // als er eentje leeg is geef daar een melding van
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    galleryIntent();
                } else {
                    Toast.makeText(this ,"Without permission no feedback can be uploaded", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    /** Gets called to start your gallery app on your phone with. */
    private void galleryIntent() {
        Log.d("AddPhotoFeedback", token.toString());

        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent , RESULT_GALLERY);

//        Intent intent = new Intent(Intent.ACTION_GET_CONTENT).setType("image/*");
//        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    /** Is being called from the Asynctask to go back to this activity. */
    public void currentSubjectIntent() {
        Intent currentSubjectIntent = new Intent(this, CurrentSubjectActivity.class);
        // extras?
        currentSubjectIntent.putExtra("subjectName", subject);
        this.startActivity(currentSubjectIntent);
    }

    public void addTagClick(View v){
        tags += etTag.getText().toString() + ", ";
        Toast.makeText(this, "Tag is toegevoegd", Toast.LENGTH_SHORT).show();
        etTag.setText("");
    }
}
