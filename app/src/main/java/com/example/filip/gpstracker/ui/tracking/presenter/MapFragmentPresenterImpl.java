package com.example.filip.gpstracker.ui.tracking.presenter;

import android.location.Location;

import com.example.filip.gpstracker.api.DataManager;
import com.example.filip.gpstracker.api.DataManagerImpl;
import com.example.filip.gpstracker.api.DatabaseHelperImpl;
import com.example.filip.gpstracker.api.FirebaseHelperImpl;
import com.example.filip.gpstracker.api.ResponseListener;
import com.example.filip.gpstracker.model.LocationWrapper;
import com.example.filip.gpstracker.model.Stats;
import com.example.filip.gpstracker.ui.tracking.view.map.TrackingFragmentView;

/**
 * Created by Filip on 05/03/2016.
 */
public class MapFragmentPresenterImpl implements MapFragmentPresenter {
    private final TrackingFragmentView trackingFragmentView;
    private final DataManager dataManager;

    private Location mLastLocation; //saves the last location to set markers, calculate distance passed

    public MapFragmentPresenterImpl(TrackingFragmentView trackingFragmentView, String sessionName, String username) {
        this.dataManager = new DataManagerImpl(new DatabaseHelperImpl(), new FirebaseHelperImpl(sessionName, username));
        this.trackingFragmentView = trackingFragmentView;
    }

    @Override
    public void sendLocationToView(Location location) {
        trackingFragmentView.addMarkerToGoogleMaps(location, mLastLocation);
        mLastLocation = location;
    }

    @Override
    public void sendLocationToFirebase(Location location) {
        dataManager.sendLocationToFirebase(location);
        if (mLastLocation != null && location != null) { //always skips the first location
            dataManager.addDistanceToNewLocationToDatabase(location, mLastLocation);
        }
    }

    @Override
    public void requestLocationsFromFirebase() {
        dataManager.requestLocations(new ResponseListener<LocationWrapper>() {
            @Override
            public void onSuccess(LocationWrapper callback) {
                sendLocationToView(createLocation(callback));
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    public void sendStatsToFirebase(long startTime, long endTime) { //sends the stats, and alerts the user of the stats
        dataManager.sendStatsToFirebase(startTime, endTime);
        createDialogDataForTheViewToDisplay(dataManager.getCurrentSessionTrackingStatsFromDatabaseHelper());
    }

    @Override
    public void requestStatsForTrackingSession() {
        dataManager.requestCurrentTrackingSessionStats(new ResponseListener<Stats>() {
            @Override
            public void onSuccess(Stats callback) {
                if (callback != null)
                    dataManager.saveCurrentStatsInDatabaseHelper(callback);
                else dataManager.saveCurrentStatsInDatabaseHelper(new Stats());
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });

    }

    @Override
    public void removeLocationListener() {
        dataManager.removeLocationListener();
    }

    private void createDialogDataForTheViewToDisplay(Stats currentSessionStats) { //sends all the data in the form of strings to the View, for it to display in a fragment
        int timeElapsed = (int) currentSessionStats.getTimeElapsed();
        float distanceTraversed = currentSessionStats.getDistanceTraversed();
        trackingFragmentView.showStatsDialog(createTimeElapsedString(timeElapsed), createDistanceTraversedString(distanceTraversed), createAverageSpeedString(timeElapsed, distanceTraversed));
    }

    private String createTimeElapsedString(int timeElapsed) {
        return "Total tracking time: " + String.valueOf(timeElapsed) + " seconds.";
    }

    private String createDistanceTraversedString(float distanceTraversed) {
        int distanceRoundedUp = (int) distanceTraversed;
        return "Total distance traversed: " + String.valueOf(distanceRoundedUp) + " meters.";
    }

    private String createAverageSpeedString(int timeElapsed, float distanceTraversed) {
        return "Average speed: " + String.valueOf(calculateAverageSpeed(timeElapsed, distanceTraversed)) + " m/s .";
    }


    private Location createLocation(LocationWrapper wrapper) {
        Location location = new Location(""); //empty provider
        location.setLongitude(wrapper.getLongitude());
        location.setLatitude(wrapper.getLatitude());
        return location;
    }

    private float calculateAverageSpeed(int timeElapsed, float distanceTraversed) {
        return distanceTraversed / timeElapsed;
    }
}
