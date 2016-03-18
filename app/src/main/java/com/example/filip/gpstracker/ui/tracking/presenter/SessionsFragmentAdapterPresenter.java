package com.example.filip.gpstracker.ui.tracking.presenter;

/**
 * Created by Filip on 10/03/2016.
 */
public interface SessionsFragmentAdapterPresenter {
    void requestListOfSessions();

    void sendDeleteRequest(String sessionName);
}
