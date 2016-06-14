package com.example.caitlin.feedbacksave;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StreamDownloadTask;

import java.io.File;
import java.io.InputStream;

public class PhotoFeedback extends SuperActivity {
    //TextView tvFeedback;
    StorageReference photoRef;
    String downloadPath;
    ImageView ivFeedback;
    Uri uploadUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_feedback);

        ivFeedback = (ImageView) findViewById(R.id.ivFB);
        //tvFeedback = (TextView) findViewById(R.id.tvFB);

        Bundle extras = getIntent().getExtras();
        photoRef = extras.getParcelable("photoRef");
        //uploadUri = extras.get
        downloadPath = extras.getString("path");
        Log.d("photoRef extra", photoRef.toString());
    }

//    private void beginDownload() {
//        // Get path
//        //String path = "photos/" + mFileUri.getLastPathSegment();
//
//        // Kick off download service
//        Intent intent = new Intent(this, MyDownloadService.class);
//        intent.setAction(MyDownloadService.ACTION_DOWNLOAD);
//        intent.putExtra(MyDownloadService.EXTRA_DOWNLOAD_PATH, path);
//        startService(intent);
//
////        // Show loading spinner
////        showProgressDialog();
//    }

    //islandRef = storageRef.child("images/island.jpg");

    public void downloadPhoto() {

        //File localFile = File.createTempFile("images", "jpg");

//        photoRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot)
//
        storageRootRefTest.child(downloadPath).getStream().addOnSuccessListener(new OnSuccessListener<StreamDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(StreamDownloadTask.TaskSnapshot taskSnapshot) {
                Log.d("download succes", "ja");
                Bitmap bmImage = null;
                try {
                    InputStream stream = taskSnapshot.getStream();
                    bmImage = BitmapFactory.decodeStream(stream);
                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
                    e.printStackTrace();
                }

                ivFeedback.setImageBitmap(bmImage);
                //close stream????
                // Local temp file has been created
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.w("download:FAILURE", exception);
                // close stream????
            }
        });
    }
}
