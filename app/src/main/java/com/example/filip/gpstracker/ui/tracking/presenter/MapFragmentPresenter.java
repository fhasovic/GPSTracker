package com.example.filip.gpstracker.ui.tracking.presenter;


import android.location.Location;

/**
 * Created by Filip on 05/03/2016.
 */
public interface MapFragmentPresenter {
    void sendLocationToView(Location location);

    void sendLocationToFirebase(Location location);

    void requestLocationsFromFirebase();

    void sendStatsToFirebase(long startTime, long endTime);

    void requestStatsForTrackingSession();

    void removeLocationListener();
}
