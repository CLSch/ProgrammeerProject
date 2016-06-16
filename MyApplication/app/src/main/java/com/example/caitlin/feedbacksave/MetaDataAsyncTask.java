package com.example.caitlin.feedbacksave;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.exception.DropboxException;

import java.util.ArrayList;

/**
 * Created by Caitlin on 16-06-16.
 */
public class MetaDataAsyncTask extends AsyncTask<Void, Void, ArrayList> {
    private DropboxAPI dropboxAPI;
    private String path;
    private Handler handler;

    public MetaDataAsyncTask(DropboxAPI dbApi, String pathName, Handler handler) {
        this.dropboxAPI = dbApi;
        this.path = pathName;
        this.handler = handler;
    }

    @Override
    protected ArrayList doInBackground(Void... params) {
        ArrayList files = new ArrayList();
        try {
            DropboxAPI.Entry directory = dropboxAPI.metadata(path, 1000, null, true, null);
            for (DropboxAPI.Entry entry: directory.contents) {
                files.add(entry.fileName());
            }
        } catch (DropboxException e){

        }

        return files;
    }

    @Override
    protected void onPostExecute(ArrayList result) {
        Message message = handler.obtainMessage();
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("data", result);
        message.setData(bundle);
        handler.sendMessage(message);
    }
}
