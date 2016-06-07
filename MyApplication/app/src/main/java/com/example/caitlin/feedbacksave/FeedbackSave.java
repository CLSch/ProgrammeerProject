package com.example.caitlin.feedbacksave;

import com.firebase.client.Firebase;
import com.firebase.client.authentication.Constants;

/**
 * Created by Caitlin on 06-06-16.
 */
public class FeedbackSave extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
