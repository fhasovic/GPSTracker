package com.example.filip.gpstracker.ui.tracking.view.map;

import android.location.Location;

/**
 * Created by Filip on 05/03/2016.
 */
public interface TrackingFragmentView {
    void addMarkerToGoogleMaps(Location location, Location lastLocation);

    void sendLocationToPresenter(Location location);

    void showStatsDialog(String timeElapsed, String distanceTraversed, String averageSpeed);
}
