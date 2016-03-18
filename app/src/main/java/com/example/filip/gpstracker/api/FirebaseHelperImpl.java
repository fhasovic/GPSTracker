package com.example.filip.gpstracker.api;

import com.example.filip.gpstracker.constants.StringConstants;
import com.example.filip.gpstracker.model.LocationWrapper;
import com.example.filip.gpstracker.model.Stats;
import com.example.filip.gpstracker.model.User;
import com.firebase.client.AuthData;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.util.Map;

/**
 * Created by Filip on 03/03/2016.
 */
public class FirebaseHelperImpl implements FirebaseHelper {
    private final String sessionName;
    private final String username;
    private final Firebase firebase;
    private Query mLocationsQuery;
    private ChildEventListener childEventListener;

    public FirebaseHelperImpl(String sessionName, String username) {
        this.firebase = new Firebase(StringConstants.BASE_URL);
        this.sessionName = sessionName;
        this.username = username;
    }

    @Override
    public void pushLocationToFirebase(double latitude, double longitude) {
        firebase.child(StringConstants.USERS_PATH).child(username).child(StringConstants.SESSIONS_PATH).child(sessionName).push().setValue(FirebaseObjectHelper.createLocationObject(latitude, longitude));
    }

    @Override
    public void pushStatsToFirebase(long timeElapsed, float distanceTraveled) {
        firebase.child(StringConstants.USERS_PATH).child(username).child(StringConstants.STATS_PATH).child(sessionName).setValue(FirebaseObjectHelper.createTrackingStatsObject(timeElapsed, distanceTraveled, sessionName));
    }

    @Override
    public void requestLocations(final ResponseListener<LocationWrapper> listener) {
        mLocationsQuery = firebase.child(StringConstants.USERS_PATH).child(username).child(StringConstants.SESSIONS_PATH).child(sessionName);
        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                listener.onSuccess(dataSnapshot.getValue(LocationWrapper.class));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                listener.onFailure(firebaseError.toException());
            }
        };
        mLocationsQuery.addChildEventListener(childEventListener);
    }

    @Override
    public void authenticateUserForLogin(String email, String password, final ResponseListener<String> listener) {
        firebase.authWithPassword(email, password, new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                getUsernameByUID(authData.getUid(), listener);
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                listener.onFailure(firebaseError.toException());
            }
        });
    }

    @Override
    public void getUsernameByUID(String uid, final ResponseListener<String> listener) {
        firebase.child(StringConstants.REGISTERED_USERS_PATH).child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                listener.onSuccess(user.getUsername());
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                listener.onFailure(firebaseError.toException());
            }
        });
    }

    @Override
    public void authenticateUserForRegistration(final String username, String email, String password, final RequestResponseListener listener) {
        firebase.createUser(email, password, new Firebase.ValueResultHandler<Map<String, Object>>() {
            @Override
            public void onSuccess(Map<String, Object> stringObjectMap) {
                createANewUserAfterRegistration(stringObjectMap.get(StringConstants.UID).toString(), username, listener);
            }

            @Override
            public void onError(FirebaseError firebaseError) {
                listener.onFailure(firebaseError.toException());
            }
        });
    }

    @Override
    public void createANewUserAfterRegistration(String uid, String username, RequestResponseListener listener) {
        firebase.child(StringConstants.REGISTERED_USERS_PATH).child(uid).setValue(FirebaseObjectHelper.createUserObject(username));
        listener.onSuccess();
    }

    @Override
    public void createNewSessionListItemAfterTrackingStarted(String sessionName) {
        firebase.child(StringConstants.USERS_PATH).child(username).child(StringConstants.LIST_OF_SESSIONS_PATH).child(sessionName).setValue(FirebaseObjectHelper.createSessionsListItemObject(sessionName));
    }

    @Override
    public void getListOfTakenUsernames(String username, final ResponseListener<DataSnapshot> listener) {
        firebase.child(StringConstants.REGISTERED_USERS_PATH).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listener.onSuccess(dataSnapshot);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                listener.onFailure(firebaseError.toException());
            }
        });
    }

    @Override
    public void getListOfSessions(final ResponseListener<DataSnapshot> listener) {
        firebase.child(StringConstants.USERS_PATH).child(username).child(StringConstants.LIST_OF_SESSIONS_PATH).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listener.onSuccess(dataSnapshot);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                listener.onFailure(firebaseError.toException());
            }
        });
    }

    @Override
    public void deleteASessionFromFirebase(String sessionName) {
        firebase.child(StringConstants.USERS_PATH).child(username).child(StringConstants.SESSIONS_PATH).child(sessionName).removeValue();
        firebase.child(StringConstants.USERS_PATH).child(username).child(StringConstants.LIST_OF_SESSIONS_PATH).child(sessionName).removeValue();
        firebase.child(StringConstants.USERS_PATH).child(username).child(StringConstants.STATS_PATH).child(sessionName).removeValue();
    }

    @Override
    public void getCurrentTrackingSessionStats(final ResponseListener<Stats> listener) {
        firebase.child(StringConstants.USERS_PATH).child(username).child(StringConstants.STATS_PATH).child(sessionName).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listener.onSuccess(dataSnapshot.getValue(Stats.class));
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                listener.onFailure(firebaseError.toException());
            }
        });
    }

    @Override
    public void requestStatsForCurrentUser(final ResponseListener<DataSnapshot> listener) {
        firebase.child(StringConstants.USERS_PATH).child(username).child(StringConstants.STATS_PATH).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listener.onSuccess(dataSnapshot);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                listener.onFailure(firebaseError.toException());
            }
        });
    }

    @Override
    public void removeLocationsListener() {
        mLocationsQuery.removeEventListener(childEventListener);
    }
}