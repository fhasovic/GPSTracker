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
public interface DataManager {
    void sendLocationToFirebase(Location location);

    void requestLocations(ResponseListener<LocationWrapper> listener);

    void addDistanceToNewLocationToDatabase(Location currentLocation, Location lastLocation);

    void sendUserLoginAttemptToFirebase(String email, String password, ResponseListener<String> listener);

    void sendRegistrationAttemptToFirebase(String username, String email, String password, RequestResponseListener listener);

    void checkIfUsernameIsAlreadyTaken(String username, RequestResponseListener listener);

    void requestListOfSessions(ResponseListener<DataSnapshot> listener);

    ArrayList<Session> createListOfSessions(DataSnapshot snapshot);

    void checkIfTheUserSessionAlreadyExists(String sessionName, RequestResponseListener listener);

    void deleteSessionFromFirebase(String sessionName);

    void sendStatsToFirebase(long startTime, long endTime);

    void requestCurrentTrackingSessionStats(ResponseListener<Stats> listener);

    void saveCurrentStatsInDatabaseHelper(Stats stats);

    Stats getCurrentSessionTrackingStatsFromDatabaseHelper();

    void requestListOfStatsForCurrentUserSessions(ResponseListener<DataSnapshot> listener);

    ArrayList<Stats> createListOfStats(DataSnapshot snapshot);
}
