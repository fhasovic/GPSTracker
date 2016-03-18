package com.example.filip.gpstracker.api;

import com.example.filip.gpstracker.constants.StringConstants;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Filip on 03/03/2016.
 */
public class FirebaseObjectHelper {

    public static Map<String, Object> createLocationObject(double latitude, double longitude) {
        Map<String, Object> locationPing = new HashMap<>();
        locationPing.put(StringConstants.LATITUDE_KEY, latitude);
        locationPing.put(StringConstants.LONGITUDE_KEY, longitude);
        return locationPing;
    }

    public static Map<String, Object> createUserObject(String username) {
        Map<String, Object> locationPing = new HashMap<>();
        locationPing.put(StringConstants.USERNAME_KEY, username);
        return locationPing;
    }

    public static Map<String, Object> createTrackingStatsObject(long timeElapsed, float distanceTraversed, String sessionName) {
        Map<String, Object> trackingStats = new HashMap<>();
        trackingStats.put(StringConstants.SESSION_KEY, sessionName);
        trackingStats.put(StringConstants.TIME_ELAPSED_KEY, timeElapsed);
        trackingStats.put(StringConstants.DISTANCE_TRAVERSED_KEY, distanceTraversed);
        return trackingStats;
    }

    public static Map<String, Object> createSessionsListItemObject(String sessionName) {
        Map<String, Object> sessionsListItem = new HashMap<>();
        sessionsListItem.put(StringConstants.SESSION_KEY, sessionName);
        return sessionsListItem;
    }
}
