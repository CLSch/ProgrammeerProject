/**
 * DropBoxClient.java
 * Caitlin Schäffers
 * 10580441
 */

package com.example.caitlin.feedbacksave;

import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;

/**
 * This is a class for a DropboxClient object.
 * Made after the example of the following tutorial:
 * Adding Dropbox to an Android App - Valdio Veliu, 19 april 2016
 * https://www.sitepoint.com/adding-the-dropbox-api-to-an-android-app/
 */
public class DropBoxClient {
    public static DbxClientV2 getClient(String ACCESS_TOKEN) {
        // Create Dropbox client
        DbxRequestConfig config = new DbxRequestConfig("dropbox/sample-app", "en_US");
        return new DbxClientV2(config, ACCESS_TOKEN);
    }
}
