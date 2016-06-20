package com.example.caitlin.feedbacksave;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

public class PhotoFeedback extends SuperActivity {
    String fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_feedback);

        Bundle extras = getIntent().getExtras();
        fileName = extras.getString("Filename");

        downloadFile();
    }

    public void downloadFile(){
        new DownloadFileAsyncTask(this, (ImageView) findViewById(R.id.ivFB)).execute("");
    }
}
