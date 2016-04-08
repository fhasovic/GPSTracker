package com.example.filip.gpstracker.helpers.data;

import android.location.Location;

import com.example.filip.gpstracker.pojo.Stats;

/**
 * Created by Filip on 03/03/2016.
 */
public class TrackingStatsHelperImpl implements TrackingStatsHelper {
    private Stats currentSessionStats;
    private Location lastLocation;

    public TrackingStatsHelperImpl(Stats currentSessionStats, Location location) {
        this.currentSessionStats = currentSessionStats;
        this.lastLocation = location;
    }

    @Override
    public void addDistanceFromNewLocationToTotalDistance(Location currentLocation) {
        if (currentSessionStats != null && lastLocation != null)
            currentSessionStats.addMoreDistanceTraversed(lastLocation.distanceTo(currentLocation));
    }

    @Override
    public void addTimeSpentWhileTrackingLastStarted(long startTime, long endTime) {
        if (currentSessionStats != null)
            currentSessionStats.addMoreTimeSpentTracking(calculateTimeElapsed(startTime, endTime));
    }

    @Override
    public void setCurrentSessionStats(Stats stats) {
        if (stats != null)
            currentSessionStats = stats;
    }

    @Override
    public Stats getCurrentSessionStats() {
        return currentSessionStats;
    }

    @Override
    public Location getLastLocation() {
        return lastLocation;
    }

    @Override
    public void setLastLocation(Location lastLocation) {
        this.lastLocation = lastLocation;
    }

    private int calculateTimeElapsed(long startTime, long endTime) {
        return (int) ((endTime - startTime) / 1000);
    }
}