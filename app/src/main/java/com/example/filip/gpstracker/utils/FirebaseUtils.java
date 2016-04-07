package com.example.filip.gpstracker.utils;

import com.example.filip.gpstracker.constants.Constants;
import com.example.filip.gpstracker.pojo.Session;
import com.example.filip.gpstracker.pojo.Stats;
import com.firebase.client.DataSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Filip on 03/03/2016.
 */
public class FirebaseUtils {

    public static Map<String, Object> createLocationObject(double latitude, double longitude) {
        Map<String, Object> locationPing = new HashMap<>();
        locationPing.put(Constants.LATITUDE_KEY, latitude);
        locationPing.put(Constants.LONGITUDE_KEY, longitude);
        return locationPing;
    }

    public static Map<String, Object> createUserObject(String username) {
        Map<String, Object> locationPing = new HashMap<>();
        locationPing.put(Constants.USERNAME_KEY, username);
        return locationPing;
    }

    public static Map<String, Object> createTrackingStatsObject(long timeElapsed, float distanceTraversed, String sessionName) {
        Map<String, Object> trackingStats = new HashMap<>();
        trackingStats.put(Constants.SESSION_KEY, sessionName);
        trackingStats.put(Constants.TIME_ELAPSED_KEY, timeElapsed);
        trackingStats.put(Constants.DISTANCE_TRAVERSED_KEY, distanceTraversed);
        return trackingStats;
    }

    public static Map<String, Object> createSessionsListItemObject(String sessionName) {
        Map<String, Object> sessionsListItem = new HashMap<>();
        sessionsListItem.put(Constants.SESSION_KEY, sessionName);
        return sessionsListItem;
    }

    public static ArrayList<Stats> getListOfStats(DataSnapshot dataSnapshot) {
        ArrayList<Stats> itemList = new ArrayList<>();
        for (DataSnapshot x : dataSnapshot.getChildren()) {
            itemList.add(x.getValue(Stats.class));
        }
        return itemList;
    }

    public static ArrayList<Session> getListOfSessions(DataSnapshot dataSnapshot) {
        ArrayList<Session> mSessionsList = new ArrayList<>();
        for (DataSnapshot x : dataSnapshot.getChildren()) {
            mSessionsList.add(x.getValue(Session.class));
        }
        return mSessionsList;
    }
}
