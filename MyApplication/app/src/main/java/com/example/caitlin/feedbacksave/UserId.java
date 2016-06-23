/**
 * UserId.java
 * Caitlin Schäffers
 * 10580441
 */

package com.example.caitlin.feedbacksave;

/**
 * Class for the UserId Singleton. This singleton contain the dropbox user-id.
 */
public class UserId {
    private static UserId ourInstance = new UserId();
    private String userId;

    public static UserId getInstance() { //empty???
        return ourInstance;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String id) {
        userId = id;
    }
}
