package com.example.caitlin.feedbacksave;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.exception.DropboxException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by Caitlin on 22-06-16.
 */
public class UploadFileAsyncTask extends AsyncTask<Object, Void, Object>{
    SuperActivity activity;
    private ProgressDialog dialog;
    File file;

    UploadFileAsyncTask(SuperActivity activity, File file) {
        this.activity = activity;
        this.dialog = new ProgressDialog(activity);
        this.file = file;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog.setMessage("Saving changes..");
        dialog.show();
    }

    @Override
    protected Object doInBackground(Object[] params) {
//        String path = params[0];
//        Uri dbUri = Uri.parse(path);
//        File file = new File(URI_to_Path.getPath(activity, dbUri));
        FileInputStream inputStream = null;
        try {
            Log.d("in eerste try, file", file.toString());
            inputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            Log.d("in eerste catch", "catch");
            e.printStackTrace();
        }
        DropboxAPI.Entry response = null;
        try {
            Log.d("in tweede try, file", file.toString());
            response = DropBoxAPIManager.getInstance().dBApi.putFile("/" + file.getName(), inputStream,
                    file.length(), null, null);
        } catch (DropboxException e) {
            Log.d("in tweede catch", "catch");
            e.printStackTrace();
        }
        //Log.i("DbExampleLog", "The uploaded file's rev is: " + response.rev);

        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        dialog.dismiss();
    }
}
