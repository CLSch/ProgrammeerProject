/**
 * AccountAsyncTask.java
 * Caitlin Schäffers
 * 10580441
 */

package com.example.caitlin.feedbacksave;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.users.FullAccount;

/**
 * This AsyncTask gets the Dropbox-accountID from the logged in Dropbox-user.
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

        // set up the progressdialog
        dialog.setMessage(activity.getString(R.string.loading_account));
        dialog.show();
    }

    @Override
    protected String doInBackground(Object[] objects) {
        FullAccount account;
        String userId = "";

        try {
            // get the Dropbox user-id
            account = dbxClient.users().getCurrentAccount();
            userId = account.getAccountId();
        } catch (DbxException e) {
            e.printStackTrace();
        }

        return userId;
    }

    @Override
    protected void onPostExecute(String userId) {
        super.onPostExecute(userId);
        try {
            dialog.dismiss();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

        // set the UserId with the current dropbox user-id
        UserIdSingleton.getInstance().setUserId(userId);

        activity.afterAccountAsyncTask();
    }
}
