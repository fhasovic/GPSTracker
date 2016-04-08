package com.example.filip.gpstracker.api;

import android.location.Location;

import com.example.filip.gpstracker.helpers.firebase.FirebaseHelper;
import com.example.filip.gpstracker.pojo.LocationWrapper;
import com.example.filip.gpstracker.pojo.Stats;
import com.example.filip.gpstracker.utils.AuthenticationUtils;
import com.firebase.client.DataSnapshot;

/**
 * Created by Filip on 05/03/2016.
 */
public class RequestManagerImpl implements RequestManager {
    private final FirebaseHelper firebaseHelper;

    public RequestManagerImpl(FirebaseHelper firebaseHelper) {
        this.firebaseHelper = firebaseHelper;
    }

    @Override
    public void sendLocationToFirebase(Location location) {
        if (location != null)
            firebaseHelper.pushLocationToFirebase(location.getLatitude(), location.getLongitude());
    }

    @Override
    public void requestLocations(ResponseListener<LocationWrapper> listener) {
        firebaseHelper.requestLocations(listener);
    }

    @Override
    public void attemptToLogTheUserIn(String email, String password, ResponseListener<String> listener) {
        firebaseHelper.authenticateUserForLogin(email, password, listener);
    }

    @Override
    public void attemptToRegisterTheUser(String username, String email, String password, RequestResponseListener listener) {
        firebaseHelper.authenticateUserForRegistration(username, email, password, listener);
    }

    @Override
    public void checkIfUsernameIsAlreadyTaken(final String username, final RequestResponseListener listener) {
        firebaseHelper.getListOfTakenUsernames(new ResponseListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot callback) {
                if (!AuthenticationUtils.checkIfUserAlreadyExists(username, callback))
                    listener.onSuccess();
                else
                    listener.onFailure();
            }

            @Override
            public void onFailure(Throwable t) {
                listener.onFailure();
            }
        });
    }

    @Override
    public void requestListOfSessions(ResponseListener<DataSnapshot> listener) {
        firebaseHelper.getListOfSessions(listener);
    }

    @Override
    public void checkIfTheUserSessionAlreadyExists(final String sessionName, final RequestResponseListener listener) {
        firebaseHelper.getListOfSessions(new ResponseListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot callback) {
                if (!AuthenticationUtils.checkIfSessionAlreadyExists(sessionName, callback)) {
                    firebaseHelper.createNewSessionListItemAfterTrackingStarted(sessionName);
                    listener.onSuccess();
                } else
                    listener.onFailure();
            }

            @Override
            public void onFailure(Throwable t) {
                listener.onFailure();
            }
        });
    }

    @Override
    public void deleteSessionFromFirebase(String sessionName) {
        firebaseHelper.deleteASessionFromFirebase(sessionName);
    }

    @Override
    public void sendStatsToFirebase(Stats stats) {
        firebaseHelper.pushStatsToFirebase(stats.getTimeElapsed(), stats.getDistanceTraversed()); //pushes to firebase when tracking stopped
    }

    @Override
    public void requestCurrentTrackingSessionStats(ResponseListener<Stats> listener) {
        firebaseHelper.getCurrentTrackingSessionStats(listener);
    }

    @Override
    public void requestListOfStatsForCurrentUserSessions(ResponseListener<DataSnapshot> listener) {
        firebaseHelper.requestStatsForCurrentUser(listener);
    }
}