/**
 * UploadPhotoAsyncTask.java
 * Caitlin Schäffers
 * 10580441
 */

package com.example.caitlin.feedbacksave;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.WriteMode;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Asynctask that uploads pictures to dropbox.
 */
public class UploadPhotoAsyncTask extends AsyncTask<Object, Void, String> {
    private DbxClientV2 dbxClient;
    private File file;
    private AddPhotoFeedbackActivity addPhotoActivity;
    private CurrentSubjectActivity curSubActivity;
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

    protected void onPreExecute() {
        //set up the progress dialog
        if (addPhotoActivity != null) {
            dialog.setMessage(addPhotoActivity.getString(R.string.uploading_image));
        } else {
            dialog.setMessage(curSubActivity.getString(R.string.uploading_image));
        }
        dialog.show();
    }

    @Override
    protected String doInBackground(Object[] params) {
        try {
            InputStream inputStream = new FileInputStream(file);

            // uses the path in the user's Dropbox to save the file
            dbxClient.files().uploadBuilder("/" + file.getName())
                    .withMode(WriteMode.OVERWRITE)
                    .uploadAndFinish(inputStream);
        } catch (DbxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file.getName();
    }

    @Override
    protected void onPostExecute(String filePath) {
        super.onPostExecute(filePath);

        if (addPhotoActivity != null) {
            // dismiss the dialog only when the activity it's called on top of is destroyed
            if (!addPhotoActivity.isDestroyed()) {
                dialog.dismiss();
            }
            addPhotoActivity.savePathToDB(filePath);

        } else {
            // dismiss the dialog only when the activity it's called on top of is destroyed
            if (!curSubActivity.isDestroyed()) {
                dialog.dismiss();
            }
            curSubActivity.savePathToDB(filePath);
        }
    }
}

