package com.example.caitlin.feedbacksave;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
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
public class DownloadFileAsyncTask2 extends AsyncTask<String, Void, File> {
    Context con;

    DownloadFileAsyncTask2(Context context) {
        this.con = context;
    }

//    protected void onPostExecute(File result) {
//        super.onPostExecute(result);
//    }

    protected File doInBackground(String... params) {
        Log.d("in doInBackground", "begin");
        String filename = params[0];
        File file = new File("app_folder/FeedbackSave/20160601_213528.jpg");
        Log.d("dit is file", file.toString());
        FileOutputStream outputStream = null;
        try {
            Log.d("in doInBackground", "eerste try");
            outputStream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            Log.d("in doInBackground", "in 1e catch");
            e.printStackTrace();
        }
        DropboxAPI.DropboxFileInfo info = null;
        try {
            Log.d("in doInBackground", "tweede try");
            info = DropBoxAPIManager.getInstance().getDropBoxApi().getFile("/20160601_213528.jpg", null, outputStream, null);
            Log.d("in doInBackground", "voor return");
            return file;
        } catch (DropboxException e) {
            Log.d("in doInBackground", "in 2e catch");
            e.printStackTrace();
        }
        Log.i("DbExampleLog", "The file's rev is: " + info.getMetadata().rev);
        return null;
    }


    @Override
    protected void onPostExecute(File file) {
        super.onPostExecute(file);
        Toast.makeText(con, "Image uploaded successfully", Toast.LENGTH_SHORT).show();
    }
}