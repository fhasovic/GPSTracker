package com.example.filip.gpstracker.api;

import android.location.Location;

import com.example.filip.gpstracker.pojo.LocationWrapper;
import com.example.filip.gpstracker.pojo.Stats;
import com.firebase.client.DataSnapshot;

/**
 * Created by Filip on 05/03/2016.
 */
public interface RequestManager {
    void sendLocationToFirebase(Location location);

    void sendStatsToFirebase(Stats stats);

    void attemptToLogTheUserIn(String email, String password, ResponseListener<String> listener);

    void attemptToRegisterTheUser(String username, String email, String password, RequestResponseListener listener);

    void requestLocations(ResponseListener<LocationWrapper> listener);

    void requestListOfSessions(ResponseListener<DataSnapshot> listener);

    void requestCurrentTrackingSessionStats(ResponseListener<Stats> listener);

    void requestListOfStatsForCurrentUserSessions(ResponseListener<DataSnapshot> listener);

    void checkIfUsernameIsAlreadyTaken(String username, RequestResponseListener listener);

    void checkIfTheUserSessionAlreadyExists(String sessionName, RequestResponseListener listener);

    void deleteSessionFromFirebase(String sessionName);
}
