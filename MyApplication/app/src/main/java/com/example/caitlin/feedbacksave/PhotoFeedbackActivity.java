/**
 * PhotoFeedbackActivity.java
 * Caitlin Schäffers
 * 10580441
 */

package com.example.caitlin.feedbacksave;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

/**
 * This activity shows the downloaded file from dropbox. The file gets returned to this activity as
 * as bitmap and binded to the imageview.
 */
public class PhotoFeedbackActivity extends SuperActivity {
    String filePath;
    Bitmap bitmap;
    ImageView ivFeedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_feedback);

        Bundle extras = getIntent().getExtras();
        filePath = extras.getString("filePath");
        ivFeedback = (ImageView) findViewById(R.id.ivFB);

        // make sure the bitmap gets removed by the garbage collector after an orientation change
        System.gc();

        downloadFile();
    }

    /* Gets the bitmap from the DownloadFileAsyncTask. */
    public void getBitmap(Bitmap bitmap){
        this.bitmap = bitmap;
        setImageView(ivFeedback);
    }

    /* Binds bitmap to the ImageView. */
    public void setImageView(ImageView ivFeedback) {
        ivFeedback.setImageBitmap(bitmap);
    }

    /* Downloads a dropbox file via the DownloadFileAsyncTask. */
    public void downloadFile(){
        new DownloadFileAsyncTask(this).execute(filePath);
    }
}
