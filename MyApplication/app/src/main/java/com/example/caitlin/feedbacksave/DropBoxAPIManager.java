package com.example.caitlin.feedbacksave;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;

/**
 * Created by Caitlin on 16-06-16.
 */
public class DropBoxAPIManager {
    private static DropBoxAPIManager ourInstance = new DropBoxAPIManager();

    //fields
    DropboxAPI<AndroidAuthSession> dBApi;

    /** constructor */
    private DropBoxAPIManager() { //empty???

    }


    /** get the instance */
    public static DropBoxAPIManager getInstance() { //empty???
        return ourInstance;
    }

}
