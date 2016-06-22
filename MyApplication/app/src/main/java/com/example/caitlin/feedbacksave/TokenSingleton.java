package com.example.caitlin.feedbacksave;

/**
 * Created by Caitlin on 22-06-16.
 */
public class TokenSingleton {
    private static TokenSingleton ourInstance = new TokenSingleton();

    //fields
    private String token;

    /** constructor */
    private TokenSingleton() { //empty???
    }

    /** get the instance */
    public static TokenSingleton getInstance() { //empty???
        return ourInstance;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
