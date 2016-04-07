package com.example.filip.gpstracker.helpers.firebase;

import com.example.filip.gpstracker.App;
import com.example.filip.gpstracker.api.RequestResponseListener;
import com.example.filip.gpstracker.api.ResponseListener;
import com.example.filip.gpstracker.constants.Constants;
import com.example.filip.gpstracker.pojo.LocationWrapper;
import com.example.filip.gpstracker.pojo.Stats;
import com.example.filip.gpstracker.pojo.User;
import com.example.filip.gpstracker.utils.FirebaseUtils;
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
    private final Firebase firebase;

    public FirebaseHelperImpl(Firebase firebase) {
        this.firebase = firebase;
    }

    @Override
    public void pushLocationToFirebase(double latitude, double longitude) {
        String username = App.getInstance().getCurrentUser();
        String currentSession = App.getInstance().getCurrentSession();
        firebase.child(Constants.USERS_PATH).child(username).child(Constants.SESSIONS_PATH).child(currentSession).push().setValue(FirebaseUtils.createLocationObject(latitude, longitude));
    }

    @Override
    public void pushStatsToFirebase(long timeElapsed, float distanceTraveled) {
        String username = App.getInstance().getCurrentUser();
        String currentSession = App.getInstance().getCurrentSession();
        firebase.child(Constants.USERS_PATH).child(username).child(Constants.STATS_PATH).child(currentSession).setValue(FirebaseUtils.createTrackingStatsObject(timeElapsed, distanceTraveled, currentSession));
    }

    @Override
    public void requestLocations(final ResponseListener<LocationWrapper> listener) {
        String username = App.getInstance().getCurrentUser();
        String currentSession = App.getInstance().getCurrentSession();
        Query mLocationsQuery = firebase.child(Constants.USERS_PATH).child(username).child(Constants.SESSIONS_PATH).child(currentSession);
        mLocationsQuery.addChildEventListener(new ChildEventListener() {
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
        });
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
        firebase.child(Constants.REGISTERED_USERS_PATH).child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
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
                createANewUserAfterRegistration(stringObjectMap.get(Constants.UID).toString(), username, listener);
            }

            @Override
            public void onError(FirebaseError firebaseError) {
                listener.onFailure();
            }
        });
    }

    @Override
    public void createANewUserAfterRegistration(String uid, String username, RequestResponseListener listener) {
        firebase.child(Constants.REGISTERED_USERS_PATH).child(uid).setValue(FirebaseUtils.createUserObject(username));
        listener.onSuccess();
    }

    @Override
    public void createNewSessionListItemAfterTrackingStarted(String sessionName) {
        String username = App.getInstance().getCurrentUser();
        firebase.child(Constants.USERS_PATH).child(username).child(Constants.LIST_OF_SESSIONS_PATH).child(sessionName).setValue(FirebaseUtils.createSessionsListItemObject(sessionName));
    }

    @Override
    public void getListOfTakenUsernames(String username, final ResponseListener<DataSnapshot> listener) {
        firebase.child(Constants.REGISTERED_USERS_PATH).addListenerForSingleValueEvent(new ValueEventListener() {
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
        String username = App.getInstance().getCurrentUser();
        firebase.child(Constants.USERS_PATH).child(username).child(Constants.LIST_OF_SESSIONS_PATH).addListenerForSingleValueEvent(new ValueEventListener() {
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
        String username = App.getInstance().getCurrentUser();
        firebase.child(Constants.USERS_PATH).child(username).child(Constants.SESSIONS_PATH).child(sessionName).removeValue();
        firebase.child(Constants.USERS_PATH).child(username).child(Constants.LIST_OF_SESSIONS_PATH).child(sessionName).removeValue();
        firebase.child(Constants.USERS_PATH).child(username).child(Constants.STATS_PATH).child(sessionName).removeValue();
    }

    @Override
    public void getCurrentTrackingSessionStats(final ResponseListener<Stats> listener) {
        String username = App.getInstance().getCurrentUser();
        String currentSession = App.getInstance().getCurrentSession();
        firebase.child(Constants.USERS_PATH).child(username).child(Constants.STATS_PATH).child(currentSession).addListenerForSingleValueEvent(new ValueEventListener() {
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
        String username = App.getInstance().getCurrentUser();
        firebase.child(Constants.USERS_PATH).child(username).child(Constants.STATS_PATH).addListenerForSingleValueEvent(new ValueEventListener() {
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
}