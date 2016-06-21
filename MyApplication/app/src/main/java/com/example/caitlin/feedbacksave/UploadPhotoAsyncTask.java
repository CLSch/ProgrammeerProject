package com.example.caitlin.feedbacksave;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.WriteMode;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Caitlin on 15-06-16.
 */
public class UploadPhotoAsyncTask extends AsyncTask {
    private DbxClientV2 dbxClient;
    private File file;
    private AddPhotoFeedbackActivity activity;
    private ProgressDialog dialog;

    UploadPhotoAsyncTask(DbxClientV2 dbxClient, File file, AddPhotoFeedbackActivity activity ) {
        this.dbxClient = dbxClient;
        this.file = file;
        this.activity = activity;
        this.dialog = new ProgressDialog(activity);
    }

    protected void onPreExecute() {
        dialog.setMessage("Uploading image to Dropbox..");
        dialog.show();
    }

    @Override
    protected Object doInBackground(Object[] params) {
        try {
            // Upload to Dropbox
            InputStream inputStream = new FileInputStream(file);
            dbxClient.files().uploadBuilder("/" + file.getName()) //Path in the user's Dropbox to save the file.
                    .withMode(WriteMode.OVERWRITE) //always overwrite existing file
                    .uploadAndFinish(inputStream);
            Log.d("Upload Status", "Success");
        } catch (DbxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
//        super.onPostExecute(o);
        dialog.dismiss();
        activity.currentSubjectIntent();
    }
}

