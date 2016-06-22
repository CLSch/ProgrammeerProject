package com.example.caitlin.feedbacksave;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.DropBoxManager;
import android.util.Log;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.exception.DropboxException;
import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.users.FullAccount;

import java.util.Map;

/**
 * Created by Caitlin on 22-06-16.
 */
public class AccountAsyncTask extends AsyncTask<Object, Void, String> {
    private DbxClientV2 dbxClient;
    private YearsActivity activity;
    private ProgressDialog dialog;

    AccountAsyncTask(DbxClientV2 dbxClient, YearsActivity activity) {
        this.dbxClient = dbxClient;
        this.activity = activity;
        this.dialog = new ProgressDialog(activity);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog.setMessage(activity.getString(R.string.loading_account));
        dialog.show();
    }

    @Override
    protected String doInBackground(Object[] objects) {
        Log.d("in doinbackground", "accountasynctask");
        FullAccount account;
        String userId = "";
        try {
            account = dbxClient.users().getCurrentAccount();
            Log.d("dit is account", account.toString());
            userId = account.getAccountId();
            Log.d("dit is userid", userId);
        } catch (DbxException e) {
            Log.d("in catch", "accountasync");
            e.printStackTrace();
        }


        return userId;
    }

    @Override
    protected void onPostExecute(String userId) {
        super.onPostExecute(userId);
        dialog.dismiss();
        UserId.getInstance().setUserId(userId);
        Log.d("userid in account async", UserId.getInstance().getUserId());
        activity.afterAccountAsyncTask();
    }
}
