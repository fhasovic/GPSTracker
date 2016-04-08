package com.example.filip.gpstracker.ui.tracking.presenter.map;


import android.location.Location;

import com.example.filip.gpstracker.pojo.Stats;

/**
 * Created by Filip on 05/03/2016.
 */
public interface MapFragmentPresenter {
    void sendLocationToView(Location location);

    void sendLocationToFirebase(Location location);

    void sendStatsToFirebase(long startTime, long endTime);

    void requestLocationsFromFirebase();

    void requestStatsForTrackingSession();

    void createDialogDataForViewOnTrackingStopped(Stats statsToDisplay);
}
