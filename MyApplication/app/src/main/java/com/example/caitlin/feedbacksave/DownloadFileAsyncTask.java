/**
 * DownloadFileAsyncTask.java
 * Caitlin Schäffers
 * 10580441
 */

package com.example.caitlin.feedbacksave;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.exception.DropboxException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Asynctask to download files with from dropbox.
 */
public class DownloadFileAsyncTask extends AsyncTask<String, Void, File> {
    PhotoFeedbackActivity activity;
    private ProgressDialog dialog;

    DownloadFileAsyncTask(PhotoFeedbackActivity activity) {
        this.activity = activity;
        this.dialog = new ProgressDialog(activity);
    }

    protected void onPreExecute() {
        // set up progress Dialog
        dialog.setMessage("Downloading image from Dropbox..");
        dialog.show();
    }

    protected File doInBackground(String... params) {
        String filePath = params[0];

        // makes a new file with the dropbox filepath
        File file = new File(activity.getFilesDir(), filePath);
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }
        DropboxAPI.DropboxFileInfo info = null;
        try {
            info = DropBoxAPIManager.getInstance().getDropBoxApi().getFile(filePath, null, outputStream, null);
            return file;
        } catch (DropboxException e) {
            e.printStackTrace();
        }
        return file;
    }


    @Override
    protected void onPostExecute(File file) {
        super.onPostExecute(file);
        // only dismiss if the activity is destroyed to avoid a detached window crash
        if (!activity.isDestroyed()) {
            dialog.dismiss();
        }

        // make bitmap from file
        String filePath = file.getPath();
        Bitmap bitmap = BitmapFactory.decodeFile(filePath);
        activity.getBitmap(bitmap);
    }
}
