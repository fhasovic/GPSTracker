package com.example.filip.gpstracker.ui.tracking.presenter.sessions;

/**
 * Created by Filip on 05/04/2016.
 */
public interface TrackingUserSessionsPresenter {
    void requestSessionsFromFirebase();

    void deleteSession(String sessionName);
}
