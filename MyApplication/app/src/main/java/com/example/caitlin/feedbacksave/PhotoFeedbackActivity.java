/**
 * PhotoFeedbackActivity.java
 * Caitlin Schäffers
 * 10580441
 */

package com.example.caitlin.feedbacksave;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

public class PhotoFeedbackActivity extends SuperActivity {
    String filePath;
    String subject;
    Bitmap bitmap;
    ImageView ivFeedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_feedback);

        Bundle extras = getIntent().getExtras();
        subject = extras.getString("subjectName");
        filePath = extras.getString("filePath");
        ivFeedback = (ImageView) findViewById(R.id.ivFB);

//        if (savedInstanceState != null) {
//            bitmap = (Bitmap) savedInstanceState.getParcelable("bitmap");
//            ivFeedback.setImageBitmap(bitmap);
//        }
//        else {
        System.gc();
        downloadFile();
//        }
    }

    public void getBitmap(Bitmap bitmap){
        this.bitmap = bitmap;
        setImageView(ivFeedback);
    }

    public void setImageView(ImageView ivFeedback) {
        ivFeedback.setImageBitmap(bitmap);
    }

    public void downloadFile(){
        new DownloadFileAsyncTask(this, (ImageView) findViewById(R.id.ivFB)).execute(filePath);
    }
}
