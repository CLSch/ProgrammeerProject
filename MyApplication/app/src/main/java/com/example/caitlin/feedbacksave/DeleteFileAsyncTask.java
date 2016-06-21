package com.example.caitlin.feedbacksave;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.dropbox.client2.exception.DropboxException;

/**
 * Created by Caitlin on 20-06-16.
 */
public class DeleteFileAsyncTask extends AsyncTask<String, Void, String> {
    Context context;

    DeleteFileAsyncTask(Context con) {
        this.context = con;
    }

    protected String doInBackground(String...params) {
        String path = params[0];
        try {
            DropBoxAPIManager.getInstance().getDropBoxApi().delete(path); //"/20160601_213528.jpg"
        } catch (DropboxException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
//        super.onPostExecute(s);
        Toast.makeText(context, "File successfully deleted", Toast.LENGTH_SHORT ).show();
    }
}
