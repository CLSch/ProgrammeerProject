package com.example.caitlin.feedbacksave;

/**
 * Created by Caitlin on 22-06-16.
 */
public class UserId {
    // single instance
    private static UserId ourInstance = new UserId();

    //fields
    private String userId;

    /** constructor */
    private UserId() { //empty???
    }

    /** get the instance */
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
