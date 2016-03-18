package com.example.filip.gpstracker.api;


import android.location.Location;


/**
 * Created by Filip on 03/03/2016.
 */
public interface TrackingHelper {
    Location getLocation();

    void connectClient();

    void disconnectClient();

    void setTrackingStatus(boolean trackingStatus);

    boolean getTrackingStatus();

    void setStartTime(long startTime);

    long getStartTime();
}
