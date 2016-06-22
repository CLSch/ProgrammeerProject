package com.example.caitlin.feedbacksave;

import android.os.AsyncTask;
import android.util.Log;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.exception.DropboxException;

/**
 * Created by Caitlin on 22-06-16.
 */
public class MetadataAsyncTask extends AsyncTask<String, Void, Boolean> {

    YearsActivity activity;

    MetadataAsyncTask(YearsActivity activity) {
        this.activity = activity;
    }

    @Override
    protected Boolean doInBackground(String... params) {
        String path = params[0];
        try {
            Log.d("in try, path is", path);
            DropboxAPI.Entry entry = DropBoxAPIManager.getInstance().dBApi.metadata(path, 1, null, false, null); //"/database.sqlite"
            Log.d("dit is entry", entry.toString());
            return true;
        } catch (DropboxException e) {
            Log.d("in catch", "exeption");
            e.printStackTrace();
        }

        return false;
    }

    @Override
    protected void onPostExecute(Boolean exists) {
        super.onPostExecute(exists);
        if (exists) {
            activity.metadataExists();
        }
    }
}
