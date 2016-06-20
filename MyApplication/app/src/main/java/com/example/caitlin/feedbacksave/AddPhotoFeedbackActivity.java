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
import java.util.ArrayList;

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
    private static int RESULT_LOAD_IMAGE = 1;
    private static int SELECT_FILE = 1;
    private static int IMAGE_REQUEST_CODE = 1;
    private String ACCESS_TOKEN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_photo_feedback);

        helper = new DBHelper(this);

        Bundle extras = getIntent().getExtras();
        subject = extras.getString("subjectName");

        ACCESS_TOKEN = DropBoxAPIManager.getInstance().getToken();

        etTag = (EditText) findViewById(R.id.etTagsPhoto);
        etFBName = (EditText) findViewById(R.id.etFBName);

        errorMes = (TextView) findViewById(R.id.tvErrorPFB);

        errorMes.setTextColor(Color.RED);

       checkPerm = Utility.checkPermission(AddPhotoFeedbackActivity.this);
    }

//    public void createRef(String FBName) {
//        // Create a reference to "mountains.jpg"
//        // filename als het jpg is?? hoe kijken voor png/jpg? wat als een naam al een extentie heeft???
//        String childName = FBName + ".jpg";
//        photoRef = storageRootRef.child(childName);
//
//        // Create a reference to 'images/mountains.jpg'
//        String imagesName = "images/" + childName;
//        imagesRef = storageRootRef.child(imagesName);
//
//    }

    private void upload() {
        if (ACCESS_TOKEN == null)return;
        //Select image to upload
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        startActivityForResult(Intent.createChooser(intent,
                "Upload to Dropbox"), IMAGE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK || data == null) return;
        // Check which request we're responding to
        if (requestCode == IMAGE_REQUEST_CODE) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                //Image URI received
                File file = new File(URI_to_Path.getPath(getApplication(), data.getData()));
                if (file != null) {
                    // ADD FILE PATH TO DB
                    helper.createPhoto(FBName ,URI_to_Path.getPath(getApplication(), data.getData()), subject);
                    //Initialize UploadTask
                    Log.d("Addphoto onactivity", ACCESS_TOKEN);
                    new UploadPhotoAsyncTask(DropBoxClient.getClient(ACCESS_TOKEN), file, this).execute();
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

        // check password en username met de database???
//        if (!passWord.equals(passWordControl)) {
//            errorMes.setText(R.string.passwords_match);
//            return false;
//        }
        return true;
    }

    public void addFeedbackClick (View v){

        FBName = etFBName.getText().toString();

        if (checkPerm) {
            galleryIntent();
        }
        // check of type field en name field niet leeg zijn!!!
        // als er eentje leeg is geef daar een melding van

        //als beiden zijn ingevuld
//        Intent currentSubjectIntent = new Intent(this, CurrentSubjectActivity.class);
//        this.startActivity(currentSubjectIntent);
//        Toast.makeText(this, "Feedback is toegevoegd (of niet)", Toast.LENGTH_SHORT).show();
//        finish();

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

    private void galleryIntent() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT).setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
        Toast.makeText(this, "in galleryIntent", Toast.LENGTH_SHORT).show();
    }

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
