package com.example.filip.gpstracker.ui.tracking.presenter.sessions;

import com.example.filip.gpstracker.api.RequestManager;
import com.example.filip.gpstracker.api.ResponseListener;
import com.example.filip.gpstracker.pojo.Session;
import com.example.filip.gpstracker.ui.tracking.view.sessions.UserSessionView;
import com.example.filip.gpstracker.utils.FirebaseUtils;
import com.example.filip.gpstracker.utils.StringUtils;
import com.firebase.client.DataSnapshot;

import java.util.ArrayList;

/**
 * Created by Filip on 05/04/2016.
 */
public class TrackingUserSessionsPresenterImpl implements TrackingUserSessionsPresenter, ResponseListener<DataSnapshot> {
    private final RequestManager requestManager;
    private final UserSessionView userSessionView;

    public TrackingUserSessionsPresenterImpl(RequestManager requestManager, UserSessionView userSessionView) {
        this.requestManager = requestManager;
        this.userSessionView = userSessionView;
    }

    @Override
    public void requestSessionsFromFirebase() {
        requestManager.requestListOfSessions(this);
    }

    @Override
    public void deleteSession(String sessionName) {
        requestManager.deleteSessionFromFirebase(sessionName);
    }

    @Override
    public void onSuccess(DataSnapshot callback) {
        if (callback != null) {
            ArrayList<Session> sessions = FirebaseUtils.getListOfSessions(callback);
            userSessionView.fillAdapterWithItems(sessions);
        }
    }

    @Override
    public void onFailure(Throwable t) {
        StringUtils.logError(t);
    }
}
