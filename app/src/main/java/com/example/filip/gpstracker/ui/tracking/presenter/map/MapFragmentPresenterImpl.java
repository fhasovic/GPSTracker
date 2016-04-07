package com.example.filip.gpstracker.ui.tracking.presenter.map;

import android.location.Location;

import com.example.filip.gpstracker.helpers.data.TrackingStatsHelper;
import com.example.filip.gpstracker.pojo.LocationWrapper;
import com.example.filip.gpstracker.utils.LocationUtils;
import com.example.filip.gpstracker.utils.StringUtils;
import com.example.filip.gpstracker.api.RequestManager;
import com.example.filip.gpstracker.api.ResponseListener;
import com.example.filip.gpstracker.pojo.Stats;
import com.example.filip.gpstracker.ui.tracking.view.map.TrackingFragmentView;

/**
 * Created by Filip on 05/03/2016.
 */
public class MapFragmentPresenterImpl implements MapFragmentPresenter {
    private final TrackingFragmentView trackingFragmentView;
    private final RequestManager requestManager;
    private final TrackingStatsHelper trackingStatsHelper;

    public MapFragmentPresenterImpl(TrackingFragmentView trackingFragmentView, RequestManager requestManager, TrackingStatsHelper trackingStatsHelper) {
        this.requestManager = requestManager;
        this.trackingFragmentView = trackingFragmentView;
        this.trackingStatsHelper = trackingStatsHelper;
    }

    @Override
    public void sendLocationToView(Location location) {
        trackingFragmentView.addMarkerToGoogleMaps(location, trackingStatsHelper.getLastLocation());
        trackingStatsHelper.setLastLocation(location);
    }

    @Override
    public void sendLocationToFirebase(Location location) {
        if (location != null) {
            requestManager.sendLocationToFirebase(location);
            trackingStatsHelper.addDistanceFromNewLocationsToTotalDistance(location);
        }
    }

    @Override
    public void requestLocationsFromFirebase() {
        requestManager.requestLocations(new ResponseListener<LocationWrapper>() {
            @Override
            public void onSuccess(LocationWrapper callback) {
                sendLocationToView(LocationUtils.createLocationFromLocationWrapper(callback));
            }

            @Override
            public void onFailure(Throwable t) {
                StringUtils.logError(t);
            }
        });

    }

    @Override
    public void sendStatsToFirebase(long startTime, long endTime) {
        trackingStatsHelper.addTimeSpentWhileTrackingLastStarted(startTime, endTime);
        Stats stats = trackingStatsHelper.getCurrentSessionStats();
        requestManager.sendStatsToFirebase(stats);
        createDialogDataForViewOnTrackingStopped(stats);
    }

    @Override
    public void requestStatsForTrackingSession() {
        requestManager.requestCurrentTrackingSessionStats(new ResponseListener<Stats>() {
            @Override
            public void onSuccess(Stats callback) {
                if (callback != null)
                    trackingStatsHelper.setCurrentSessionStats(callback);
            }

            @Override
            public void onFailure(Throwable t) {
                StringUtils.logError(t);
            }
        });
    }

    private float calculateAverageSpeed(int timeElapsed, float distanceTraversed) {
        return distanceTraversed / timeElapsed;
    }

    private void createDialogDataForViewOnTrackingStopped(Stats statsToDisplay) {
        int timeElapsed = statsToDisplay.getTimeElapsed();
        float distanceTraversed = statsToDisplay.getDistanceTraversed();
        trackingFragmentView.showStatsDialog(timeElapsed, distanceTraversed, calculateAverageSpeed(timeElapsed, distanceTraversed));
    }
}