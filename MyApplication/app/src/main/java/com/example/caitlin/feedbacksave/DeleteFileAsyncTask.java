/**
 * DeleteFileAsyncTask.java
 * Caitlin Schäffers
 * 10580441
 */

package com.example.caitlin.feedbacksave;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.dropbox.client2.exception.DropboxException;

/**
 * Asynctask for deleting files from Dropbox.
 */
public class DeleteFileAsyncTask extends AsyncTask<String, Void, String> {
    Context context;

    DeleteFileAsyncTask(Context con) {
        this.context = con;
    }

    protected String doInBackground(String...params) {
        String path = params[0];
        try {
            DropBoxAPIManager.getInstance().getDropBoxApi().delete(path);
        } catch (DropboxException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Toast.makeText(context, "File successfully deleted", Toast.LENGTH_SHORT ).show();
    }
}
