package com.example.caitlin.feedbacksave;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

public class AddPhotoFeedback extends SuperActivity {
    EditText etTag;
    EditText etFBName;
    TextView errorMes;
    StorageReference storageRootRef;
    StorageReference imagesRef;
    StorageReference photoRef;
    UploadTask uploadTask;
    boolean checkPerm;
    private static int RESULT_LOAD_IMAGE = 1;
    private static int SELECT_FILE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_add_photo_feedback);

        FirebaseStorage storage = FirebaseStorage.getInstance();

        storageRootRef = storage.getReferenceFromUrl("gs://project-1258991994024708208.appspot.com");

        etTag = (EditText) findViewById(R.id.etTagsPhoto);
        etFBName = (EditText) findViewById(R.id.etFBName);

        errorMes = (TextView) findViewById(R.id.tvErrorPFB);

        errorMes.setTextColor(Color.RED);

       checkPerm = Utility.checkPermission(AddPhotoFeedback.this);
    }

    public void createRef(String FBName) {
        // Create a reference to "mountains.jpg"
        // filename als het jpg is?? hoe kijken voor png/jpg? wat als een naam al een extentie heeft???
        String childName = FBName + ".jpg";
        photoRef = storageRootRef.child(childName);

        // Create a reference to 'images/mountains.jpg'
        String imagesName = "images/" + childName;
        imagesRef = storageRootRef.child(imagesName);

    }

    public void uploadPhotoFromFile() {
        Uri file = Uri.fromFile(new File("path/to/images/rivers.jpg"));
        StorageReference riversRef = storageRootRef.child("images/"+file.getLastPathSegment());

        final StorageReference photoRef = storageRootRef.child("photos").child(fileUri.getLastPathSegment());
        uploadTask = riversRef.putFile(file);

// Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
            }
        });
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

        String FBName = etFBName.getText().toString();
        createRef(FBName);

        if (checkPerm) {
            galleryIntent();
        }
        // check of type field en name field niet leeg zijn!!!
        // als er eentje leeg is geef daar een melding van

        //als beiden zijn ingevuld
        Intent currentSubjectIntent = new Intent(this, CurrentSubjectActivity.class);
        this.startActivity(currentSubjectIntent);
        Toast.makeText(this, "Feedback is toegevoegd (of niet)", Toast.LENGTH_SHORT).show();
        finish();

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
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    public void addTagClick(View v){
        Toast.makeText(this, "Tag is toegevoegd", Toast.LENGTH_SHORT).show();
        etTag.setText("");
    }
}
