package com.example.filip.gpstracker.api;

import android.location.Location;

import com.example.filip.gpstracker.model.Session;
import com.example.filip.gpstracker.model.Stats;
import com.example.filip.gpstracker.model.User;
import com.firebase.client.DataSnapshot;

import java.util.ArrayList;

/**
 * Created by Filip on 03/03/2016.
 */
public class DatabaseHelperImpl implements DatabaseHelper {
    private Stats currentSessionStats;

    @Override
    public long calculateTimeElapsed(long startTime, long endTime) {
        return (endTime - startTime) / 1000;
    }

    @Override
    public void addDistanceFromNewLocationsToTotalDistance(Location currentLocation, Location lastLocation) {
        if (currentSessionStats != null)
            currentSessionStats.addMoreDistanceTraversed(lastLocation.distanceTo(currentLocation));
    }

    @Override
    public void addTimeSpentWhileTrackingLastStarted(long timeElapsed) {
        if (currentSessionStats != null)
            currentSessionStats.addMoreTimeSpentTracking(timeElapsed);
    }

    @Override
    public ArrayList<Stats> createListOfStatsFromSnapshot(DataSnapshot snapshot) {
        ArrayList<Stats> itemList = new ArrayList<>();
        for (DataSnapshot x : snapshot.getChildren()) {
            itemList.add(x.getValue(Stats.class));
        }
        return itemList;
    }

    @Override
    public boolean userAlreadyExistsInDatabase(String username, DataSnapshot snapshot) {
        for (DataSnapshot x : snapshot.getChildren()) {
            User user = x.getValue(User.class);
            if (user.getUsername().equals(username)) return true;
        }
        return false;
    }

    @Override
    public boolean sessionAlreadyExistsInDatabase(String sessionName, DataSnapshot snapshot) {
        for (DataSnapshot x : snapshot.getChildren()) {
            Session session = x.getValue(Session.class);
            if (session.getSessionName().equals(sessionName)) return true;
        }
        return false;
    }

    @Override
    public ArrayList<Session> createListOfSessionsFromSnapshot(DataSnapshot snapshot) {
        ArrayList<Session> mSessionsList = new ArrayList<>();
        for (DataSnapshot x : snapshot.getChildren()) {
            mSessionsList.add(x.getValue(Session.class));
        }
        return mSessionsList;
    }

    @Override
    public void setCurrentSessionStats(Stats stats) {
        if (stats != null) {
            currentSessionStats = stats;
        } else currentSessionStats = new Stats(); //if first started tracking
    }

    @Override
    public Stats getCurrentSessionStats() {
        return currentSessionStats;
    }
}