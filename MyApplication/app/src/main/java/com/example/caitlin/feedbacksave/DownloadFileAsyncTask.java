package com.example.caitlin.feedbacksave;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.exception.DropboxException;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by Caitlin on 16-06-16.
 */
public class DownloadFileAsyncTask extends AsyncTask<String, Void, File> {
    PhotoFeedback activity;
    ImageView ivFB;
    private ProgressDialog dialog;

    DownloadFileAsyncTask(PhotoFeedback activity, ImageView ivFeedback) {
        this.activity = activity;
        this.ivFB = ivFeedback;
        this.dialog = new ProgressDialog(activity);
    }

    protected void onPreExecute() {
        // Progress Dialog
        dialog.setMessage("Downloading image from Dropbox..");
        dialog.show();
    }

//    protected void onPostExecute(File result) {
//        super.onPostExecute(result);
//    }

    protected File doInBackground(String... params) {
        String filePath = params[0];
        Log.d("dit is filePath", filePath);
        File file = new File(activity.getFilesDir(), filePath);
        Log.d("dit is file", file.toString());
        FileOutputStream outputStream = null;
        try {
            Log.d("in doInBackground", "eerste try");
            outputStream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            Log.d("in doInBackground", "in 1e catch");
            e.printStackTrace();
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }
        DropboxAPI.DropboxFileInfo info = null;
        try {
            Log.d("in doInBackground", "tweede try");
            info = DropBoxAPIManager.getInstance().getDropBoxApi().getFile(filePath, null, outputStream, null);
            Log.d("in doInBackground", "voor return");
            return file;
        } catch (DropboxException e) {
            Log.d("in doInBackground", "in 2e catch");
            e.printStackTrace();
        }
        //Log.i("DbExampleLog", "The file's rev is: " + info.getMetadata().rev);
        return file;
    }


    @Override
    protected void onPostExecute(File file) {
        super.onPostExecute(file);
        dialog.dismiss();
        String filePath = file.getPath();
        Bitmap bitmap = BitmapFactory.decodeFile(filePath);
        activity.getBitmap(bitmap);
    }
}
