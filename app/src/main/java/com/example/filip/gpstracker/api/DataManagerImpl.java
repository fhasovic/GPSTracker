package com.example.filip.gpstracker.api;

import android.location.Location;

import com.example.filip.gpstracker.model.LocationWrapper;
import com.example.filip.gpstracker.model.Session;
import com.example.filip.gpstracker.model.Stats;
import com.firebase.client.DataSnapshot;

import java.util.ArrayList;

/**
 * Created by Filip on 05/03/2016.
 */
public class DataManagerImpl implements DataManager {
    private final DatabaseHelper databaseHelper;
    private final FirebaseHelper firebaseHelper;

    public DataManagerImpl(DatabaseHelper databaseHelper, FirebaseHelper firebaseHelper) {
        this.databaseHelper = databaseHelper;
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
    public void addDistanceToNewLocationToDatabase(Location currentLocation, Location lastLocation) {
        databaseHelper.addDistanceFromNewLocationsToTotalDistance(currentLocation, lastLocation);
    }

    @Override
    public void sendUserLoginAttemptToFirebase(String email, String password, ResponseListener<String> listener) {
        firebaseHelper.authenticateUserForLogin(email, password, listener);
    }

    @Override
    public void sendRegistrationAttemptToFirebase(String username, String email, String password, RequestResponseListener listener) {
        firebaseHelper.authenticateUserForRegistration(username, email, password, listener);
    }

    @Override
    public void checkIfUsernameIsAlreadyTaken(final String username, final RequestResponseListener listener) {
        firebaseHelper.getListOfTakenUsernames(username, new ResponseListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot callback) {
                if (!databaseHelper.userAlreadyExistsInDatabase(username, callback))
                    listener.onSuccess();
                else
                    listener.onFailure(null);
            }

            @Override
            public void onFailure(Throwable t) {
                listener.onFailure(t);
            }
        });
    }

    @Override
    public void requestListOfSessions(ResponseListener<DataSnapshot> listener) {
        firebaseHelper.getListOfSessions(listener);
    }

    @Override
    public ArrayList<Session> createListOfSessions(DataSnapshot snapshot) {
        return databaseHelper.createListOfSessionsFromSnapshot(snapshot);
    }

    @Override
    public void checkIfTheUserSessionAlreadyExists(final String sessionName, final RequestResponseListener listener) {
        firebaseHelper.getListOfSessions(new ResponseListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot callback) {
                if (!databaseHelper.sessionAlreadyExistsInDatabase(sessionName, callback)) {
                    firebaseHelper.createNewSessionListItemAfterTrackingStarted(sessionName);
                    listener.onSuccess();
                } else
                    listener.onFailure(null);
            }

            @Override
            public void onFailure(Throwable t) {
                listener.onFailure(t);
            }
        });
    }

    @Override
    public void deleteSessionFromFirebase(String sessionName) {
        firebaseHelper.deleteASessionFromFirebase(sessionName);
    }

    @Override
    public void sendStatsToFirebase(long startTime, long endTime) {
        databaseHelper.addTimeSpentWhileTrackingLastStarted(databaseHelper.calculateTimeElapsed(startTime, endTime));
        Stats stats = databaseHelper.getCurrentSessionStats(); //gets the complete stats
        firebaseHelper.pushStatsToFirebase(stats.getTimeElapsed(), stats.getDistanceTraversed()); //pushes to firebase when tracking stopped
    }

    @Override
    public void requestCurrentTrackingSessionStats(ResponseListener<Stats> listener) {
        firebaseHelper.getCurrentTrackingSessionStats(listener);
    }

    @Override
    public void saveCurrentStatsInDatabaseHelper(Stats stats) {
        databaseHelper.setCurrentSessionStats(stats);
    }

    @Override
    public Stats getCurrentSessionTrackingStatsFromDatabaseHelper() {
        return databaseHelper.getCurrentSessionStats();
    }

    @Override
    public void removeLocationListener() {
        firebaseHelper.removeLocationsListener();
    }

    @Override
    public void requestListOfStatsForCurrentUserSessions(ResponseListener<DataSnapshot> listener) {
        firebaseHelper.requestStatsForCurrentUser(listener);
    }

    @Override
    public ArrayList<Stats> createListOfStats(DataSnapshot snapshot) {
        return databaseHelper.createListOfStatsFromSnapshot(snapshot);
    }
}