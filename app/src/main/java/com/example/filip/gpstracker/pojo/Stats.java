package com.example.filip.gpstracker.pojo;

/**
 * Created by Filip on 03/03/2016.
 */
public class Stats {
    private String sessionName;
    private float distanceTraversed;
    private int timeElapsed;

    public float getDistanceTraversed() {
        return distanceTraversed;
    }

    public int getTimeElapsed() {
        return timeElapsed; //seconds
    }

    public String getSessionName() {
        return sessionName;
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }

    public void addMoreTimeSpentTracking(int timeElapsed) {
        this.timeElapsed += timeElapsed;
    }

    public void addMoreDistanceTraversed(float distanceTraversed) {
        this.distanceTraversed += distanceTraversed;
    }
}
