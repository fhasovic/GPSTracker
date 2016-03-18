package com.example.filip.gpstracker.api;

import android.location.Location;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

/**
 * Created by Filip on 03/03/2016.
 */
public class TrackingHelperImpl implements TrackingHelper {
    private boolean isTracking;
    private long startTime;
    private final GoogleApiClient googleApiClient;

    public TrackingHelperImpl(GoogleApiClient client) {
        googleApiClient = client;
    }

    @Override
    public Location getLocation() {
        try {
            Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
            if (location != null) {
                return location;
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void connectClient() {
        googleApiClient.connect();
    }

    @Override
    public void disconnectClient() {
        googleApiClient.disconnect();
    }

    @Override
    public void setTrackingStatus(boolean trackingStatus) {
        isTracking = trackingStatus;
    }

    @Override
    public boolean getTrackingStatus() {
        return isTracking;
    }

    @Override
    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    @Override
    public long getStartTime() {
        return startTime;
    }
}
