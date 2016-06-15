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

public class AddPhotoFeedback extends SuperActivity {
    EditText etTag;
    EditText etFBName;
    TextView errorMes;
    String FBName;
    String tags;
    Uri uploadUri;
    Uri downloadUri;
    boolean checkPerm;
    private static int RESULT_LOAD_IMAGE = 1;
    private static int SELECT_FILE = 1;
    private static int IMAGE_REQUEST_CODE = 1;
    private String ACCESS_TOKEN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_photo_feedback);

        ACCESS_TOKEN = getToken();

        etTag = (EditText) findViewById(R.id.etTagsPhoto);
        etFBName = (EditText) findViewById(R.id.etFBName);

        errorMes = (TextView) findViewById(R.id.tvErrorPFB);

        errorMes.setTextColor(Color.RED);

       checkPerm = Utility.checkPermission(AddPhotoFeedback.this);
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
                    //Initialize UploadTask
                    new UploadPhotoAsyncTask(DropBoxClient.getClient(ACCESS_TOKEN), file, getApplicationContext()).execute();
                }
            }
        }
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == SELECT_FILE && resultCode == RESULT_OK && data != null && data.getData() != null) {
//
//            uploadUri = data.getData();
//            // METADATA
//            //uploadPhotoFromFile();
//        }
//    }

//    public void uploadPhotoFromFile() {
//        File tmpFile = new File(uploadUri, "IMG_2012-03-12_10-22-09_thumb.jpg");
//
//        FileInputStream fis = new FileInputStream(tmpFile);
//
//        try {
//            DropboxAPI.Entry newEntry = dBApi.putFileOverwrite("IMG_2012-03-12_10-22-09_thumb.jpg", fis, tmpFile.length(), null);
//        } catch (DropboxUnlinkedException e) {
//            Log.e("DbExampleLog", "User has unlinked.");
//        } catch (DropboxException e) {
//            Log.e("DbExampleLog", "Something went wrong while uploading.");
//        }
//    }


//    public void uploadPhotoFromFile() {
//
//
////        Uri file = Uri.fromFile(new File("path/to/images/rivers.jpg"));
////        StorageReference riversRef = storageRootRef.child("images/"+file.getLastPathSegment());
//
//        final StorageReference photoRef = storageRootRef.child("photos").child(uploadUri.getLastPathSegment());
//
//        String finalTags = tags.substring(0, tags.lastIndexOf(","));
//
//        StorageMetadata metadata = new StorageMetadata.Builder().setCustomMetadata("name", FBName).setCustomMetadata("tags", finalTags)
//                .build();
//
//        uploadTask = photoRef.putFile(uploadUri, metadata);
//
//// Register observers to listen for when the download is done or if it fails
//        uploadTask.addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception exception) {
//                Toast.makeText(AddPhotoFeedback.this, "Error: upload failed", Toast.LENGTH_SHORT).show();
//            }
//        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
//                downloadUri = taskSnapshot.getDownloadUrl();
//                Log.d("downloadUri is", downloadUri.toString());
//
//                Intent currentSubjectIntent = new Intent(AddPhotoFeedback.this, CurrentSubjectActivity.class);
//                AddPhotoFeedback.this.startActivity(currentSubjectIntent);
//                //Log.d("photoRefPath", photoRef.getPath());
//                //currentSubjectIntent.putExtra("photorefPath", photoRef.getPath());
//                //currentSubjectIntent.putExtra("photoRef", (Parcelable) photoRef);
//                String path = "photos/" + uploadUri.getLastPathSegment();
//                //currentSubjectIntent.putExtra("uploadUri", uploadUri);
//                currentSubjectIntent.putExtra("path", path);
//                Toast.makeText(AddPhotoFeedback.this, "Feedback is toegevoegd (of niet)", Toast.LENGTH_SHORT).show();
//                finish();
//            }
//        });
//    }

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

    private void galleryIntent()
    {   Intent intent = new Intent(Intent.ACTION_GET_CONTENT).setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
        Toast.makeText(this, "in galleryIntent", Toast.LENGTH_SHORT).show();
    }

    public void addTagClick(View v){
        tags += etTag.getText().toString() + ", ";
        Toast.makeText(this, "Tag is toegevoegd", Toast.LENGTH_SHORT).show();
        etTag.setText("");
    }
}
