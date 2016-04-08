package com.example.filip.gpstracker;

import android.location.Location;

import com.example.filip.gpstracker.api.RequestManager;
import com.example.filip.gpstracker.api.RequestManagerImpl;
import com.example.filip.gpstracker.api.RequestResponseListener;
import com.example.filip.gpstracker.api.ResponseListener;
import com.example.filip.gpstracker.helpers.firebase.FirebaseHelper;
import com.example.filip.gpstracker.pojo.LocationWrapper;
import com.example.filip.gpstracker.pojo.Stats;
import com.firebase.client.DataSnapshot;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

/**
 * Created by Filip on 08/04/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class RequestManagerTest {
    private RequestManager requestManager;

    @Mock
    private FirebaseHelper mockedFirebaseHelper;

    @Mock
    private Location location;

    @Mock
    private ResponseListener<LocationWrapper> mockedLocationListener;
    @Mock
    private ResponseListener<String> mockedLogInListener;
    @Mock
    private RequestResponseListener mockedRequestResponseListener;
    @Mock
    private ResponseListener<DataSnapshot> mockedDataSnapshotResponseListener;
    @Mock
    private ResponseListener<Stats> mockedStatsResponseListener;

    @Mock
    private Stats mockedStats;

    private final String mockedUsername = "username";

    private final String mockedPassword = "password";

    private final String mockedEmail = "email@net.com";

    private final String mockedSession = "session";

    @Before
    public void setUp() throws Exception {
        requestManager = new RequestManagerImpl(mockedFirebaseHelper);
    }

    @Test
    public void testShouldCallSentLocationToFirebase() throws Exception {
        requestManager.sendLocationToFirebase(location);

        verify(mockedFirebaseHelper).pushLocationToFirebase(location.getLatitude(), location.getLongitude());
    }

    @Test
    public void testShouldCallRequestLocationsFromFirebase() throws Exception {
        requestManager.requestLocations(mockedLocationListener);

        verify(mockedFirebaseHelper).requestLocations(mockedLocationListener);
    }

    @Test
    public void testShouldCallAttemptToLogTheUserIn() throws Exception {
        requestManager.attemptToLogTheUserIn(mockedEmail, mockedPassword, mockedLogInListener);

        verify(mockedFirebaseHelper).authenticateUserForLogin(mockedEmail, mockedPassword, mockedLogInListener);
    }

    @Test
    public void testShouldCallAttemptToRegisterTheUser() throws Exception {
        requestManager.attemptToRegisterTheUser(mockedUsername, mockedEmail, mockedPassword, mockedRequestResponseListener);

        verify(mockedFirebaseHelper).authenticateUserForRegistration(mockedUsername, mockedEmail, mockedPassword, mockedRequestResponseListener);
    }

    @Test
    public void testShouldCallGetListOfSessions() throws Exception {
        requestManager.requestListOfSessions(mockedDataSnapshotResponseListener);

        verify(mockedFirebaseHelper).getListOfSessions(mockedDataSnapshotResponseListener);
    }

    @Test
    public void testShouldCallDeleteSessionFromFirebase() throws Exception {
        requestManager.deleteSessionFromFirebase(mockedSession);

        verify(mockedFirebaseHelper).deleteASessionFromFirebase(mockedSession);
    }

    @Test
    public void testShouldCallSendStatsToFirebase() throws Exception {
        requestManager.sendStatsToFirebase(mockedStats);

        verify(mockedFirebaseHelper).pushStatsToFirebase(mockedStats.getTimeElapsed(), mockedStats.getDistanceTraversed());
    }

    @Test
    public void testShouldCallRequestCurrentTrackingSessionStats() throws Exception {
        requestManager.requestCurrentTrackingSessionStats(mockedStatsResponseListener);

        verify(mockedFirebaseHelper).getCurrentTrackingSessionStats(mockedStatsResponseListener);
    }

    @Test
    public void testShouldCallRequestListOfSessionStatsForCurrentUserSessions() throws Exception {
        requestManager.requestListOfStatsForCurrentUserSessions(mockedDataSnapshotResponseListener);

        verify(mockedFirebaseHelper).requestStatsForCurrentUser(mockedDataSnapshotResponseListener);
    }
}
