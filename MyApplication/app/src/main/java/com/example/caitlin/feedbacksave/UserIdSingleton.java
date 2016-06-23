/**
 * UserIdSingleton.java
 * Caitlin Schäffers
 * 10580441
 */

package com.example.caitlin.feedbacksave;

/**
 * Class for the UserId Singleton. This singleton contains the dropbox user-id.
 */
public class UserIdSingleton {
    private static UserIdSingleton ourInstance = new UserIdSingleton();
    private String userId;

    public static UserIdSingleton getInstance() { //empty???
        return ourInstance;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String id) {
        userId = id;
    }
}
