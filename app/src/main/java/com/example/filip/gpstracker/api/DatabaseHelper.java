package com.example.filip.gpstracker.api;

import android.location.Location;

import com.example.filip.gpstracker.model.Session;
import com.example.filip.gpstracker.model.Stats;
import com.firebase.client.DataSnapshot;

import java.util.ArrayList;

/**
 * Created by Filip on 03/03/2016.
 */
public interface DatabaseHelper {
    long calculateTimeElapsed(long startTime, long endTime);

    void addDistanceFromNewLocationsToTotalDistance(Location currentLocation, Location lastLocation);

    boolean userAlreadyExistsInDatabase(String username, DataSnapshot snapshot);

    boolean sessionAlreadyExistsInDatabase(String sessionName, DataSnapshot snapshot);

    ArrayList<Session> createListOfSessionsFromSnapshot(DataSnapshot snapshot);

    void setCurrentSessionStats(Stats stats);

    Stats getCurrentSessionStats();

    void addTimeSpentWhileTrackingLastStarted(long timeElapsed);

    ArrayList<Stats> createListOfStatsFromSnapshot(DataSnapshot snapshot);

}
