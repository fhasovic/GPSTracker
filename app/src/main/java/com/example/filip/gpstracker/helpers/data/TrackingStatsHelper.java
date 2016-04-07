package com.example.filip.gpstracker.helpers.data;

import android.location.Location;

import com.example.filip.gpstracker.pojo.Stats;

/**
 * Created by Filip on 03/03/2016.
 */
public interface TrackingStatsHelper {
    void addDistanceFromNewLocationsToTotalDistance(Location currentLocation);

    void setCurrentSessionStats(Stats stats);

    Stats getCurrentSessionStats();

    void setLastLocation(Location location);

    Location getLastLocation();

    void addTimeSpentWhileTrackingLastStarted(long startTime, long endTime);
}
