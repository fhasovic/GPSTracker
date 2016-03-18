package com.example.filip.gpstracker.model;

/**
 * Created by Filip on 03/03/2016.
 */
public class Stats {
    private String sessionName;
    private float distanceTraversed;
    private long timeElapsed;

    public float getDistanceTraversed() {
        return distanceTraversed;
    }

    public void setDistanceTraversed(float distanceTraversed) {
        this.distanceTraversed = distanceTraversed;
    }

    public long getTimeElapsed() {
        return timeElapsed; //seconds
    }

    public void setTimeElapsed(long timeElapsed) {
        this.timeElapsed = timeElapsed;
    }

    public String getSessionName() {
        return sessionName;
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }

    public void addMoreTimeSpentTracking(long timeElapsed) {
        this.timeElapsed += timeElapsed;
    }

    public void addMoreDistanceTraversed(float distanceTraversed) {
        this.distanceTraversed += distanceTraversed;
    }
}
