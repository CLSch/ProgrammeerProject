package com.example.caitlin.feedbacksave;

import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;

/**
 * Created by Caitlin on 15-06-16.
 */
public class DropBoxClient {
    public static DbxClientV2 getClient(String ACCESS_TOKEN) {
        // Create Dropbox client
        DbxRequestConfig config = new DbxRequestConfig("dropbox/sample-app", "en_US");
        return new DbxClientV2(config, ACCESS_TOKEN);
    }
}
