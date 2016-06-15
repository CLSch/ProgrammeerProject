package com.example.caitlin.feedbacksave;

import com.dropbox.client2.DropboxAPI;

import java.io.Serializable;

/**
 * Created by Caitlin on 15-06-16.
 */
public class DropBoxAPIWrapper implements Serializable {
    private DropboxAPI dbApi;

    /** constructor for todoItems from database */
    public DropBoxAPIWrapper(DropboxAPI dBApi) {
        dbApi = dBApi;
    }

    public DropboxAPI getDropBoxAPI() {
        return dbApi;
    }
}
