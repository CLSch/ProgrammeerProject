package com.example.caitlin.feedbacksave;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.WriteMode;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Caitlin on 15-06-16.
 */
public class UploadPhotoAsyncTask extends AsyncTask<Object, Void, String> {
    private DbxClientV2 dbxClient;
    private File file;
    private AddPhotoFeedbackActivity addPhotoActivity;
    private CurrentSubjectActivity curSubActivity;
    private SuperActivity superActivity;
    private ProgressDialog dialog;

    UploadPhotoAsyncTask(DbxClientV2 dbxClient, File file, AddPhotoFeedbackActivity activity ) {
        this.dbxClient = dbxClient;
        this.file = file;
        this.addPhotoActivity = activity;
        this.dialog = new ProgressDialog(activity);
    }

    UploadPhotoAsyncTask(DbxClientV2 dbxClient, File file, CurrentSubjectActivity activity ) {
        this.dbxClient = dbxClient;
        this.file = file;
        this.curSubActivity = activity;
        this.dialog = new ProgressDialog(activity);
    }

    UploadPhotoAsyncTask(DbxClientV2 dbxClient, File file, SuperActivity activity ) {
        this.dbxClient = dbxClient;
        this.file = file;
        this.superActivity = activity;
        this.dialog = new ProgressDialog(activity);
    }

    protected void onPreExecute() {
        if (superActivity != null) {
            dialog.setMessage("Saving changes..");
        } else {
            dialog.setMessage("Uploading image to Dropbox..");
        }
        dialog.show();
    }

    @Override
    protected String doInBackground(Object[] params) {
        try {
//            BitmapFactory.Options option = new BitmapFactory.Options();
//            option.inSampleSize = 2;
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
        return file.getName();
    }

    @Override
    protected void onPostExecute(String filePath) {
//        super.onPostExecute(filePath);
        dialog.dismiss();
        if (addPhotoActivity != null) {
            addPhotoActivity.savePathToDB(filePath);
        } else if (curSubActivity != null){
            curSubActivity.savePathToDB(filePath);
        }
    }
}

