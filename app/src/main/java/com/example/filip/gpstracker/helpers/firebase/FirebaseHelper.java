package com.example.filip.gpstracker.helpers.firebase;

import com.example.filip.gpstracker.api.RequestResponseListener;
import com.example.filip.gpstracker.api.ResponseListener;
import com.example.filip.gpstracker.pojo.LocationWrapper;
import com.example.filip.gpstracker.pojo.Stats;
import com.firebase.client.DataSnapshot;

/**
 * Created by Filip on 03/03/2016.
 */
public interface FirebaseHelper {
    void pushLocationToFirebase(double latitude, double longitude);

    void pushStatsToFirebase(long timeElapsed, float distanceTraveled);

    void requestLocations(ResponseListener<LocationWrapper> listener);

    void authenticateUserForLogin(String email, String password, ResponseListener<String> listener);

    void getUsernameByUID(String uid, ResponseListener<String> listener);

    void authenticateUserForRegistration(String username, String email, String password, RequestResponseListener listener);

    void createANewUserAfterRegistration(String uid, String username, RequestResponseListener listener);

    void createNewSessionListItemAfterTrackingStarted(String sessionName);

    void getListOfTakenUsernames(String username, ResponseListener<DataSnapshot> listener);

    void getListOfSessions(ResponseListener<DataSnapshot> listener);

    void deleteASessionFromFirebase(String sessionName);

    void getCurrentTrackingSessionStats(ResponseListener<Stats> listener);

    void requestStatsForCurrentUser(ResponseListener<DataSnapshot> listener);
}
