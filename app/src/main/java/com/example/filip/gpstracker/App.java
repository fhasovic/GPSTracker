package com.example.filip.gpstracker;

import android.app.Application;

import com.example.filip.gpstracker.api.RequestManager;
import com.example.filip.gpstracker.api.RequestManagerImpl;
import com.example.filip.gpstracker.helpers.firebase.FirebaseHelper;
import com.example.filip.gpstracker.helpers.firebase.FirebaseHelperImpl;
import com.example.filip.gpstracker.constants.Constants;
import com.firebase.client.Firebase;

/**
 * Created by Filip on 05/04/2016.
 */
public class App extends Application {
    private String currentUser;

    private String currentSession;

    private RequestManager requestManager;

    private static App sInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        sInstance = this;

        Firebase.setAndroidContext(this); //app-wide android context for firebase(don't have to set it up everywhere)

        Firebase firebase = new Firebase(Constants.BASE_URL);

        FirebaseHelper firebaseHelper = new FirebaseHelperImpl(firebase);

        requestManager = new RequestManagerImpl(firebaseHelper);
    }

    public static App getInstance() {
        return sInstance;
    }

    public void setCurrentUser(String username) {
        currentUser = username;
    }

    public String getCurrentUser() {
        return currentUser;
    }

    public String getCurrentSession() {
        return currentSession;
    }

    public void setCurrentSession(String currentSession) {
        this.currentSession = currentSession;
    }

    public RequestManager getRequestManager() {
        return requestManager;
    }
}
