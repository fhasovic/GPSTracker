package com.example.filip.gpstracker;

import android.location.Location;

import com.example.filip.gpstracker.helpers.data.TrackingStatsHelper;
import com.example.filip.gpstracker.helpers.data.TrackingStatsHelperImpl;
import com.example.filip.gpstracker.pojo.Stats;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

/**
 * Created by Filip on 08/04/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class TrackingStatsHelperTest {
    private final double epsilon = 1e-15;

    private TrackingStatsHelper trackingStatsHelper;

    @Mock
    private Stats stats;
    @Mock
    private Location lastLocation;
    @Mock
    private Location currentLocation;

    @Before
    public void setUp() throws Exception {
        trackingStatsHelper = new TrackingStatsHelperImpl(stats, lastLocation);
    }

    @Test
    public void testShouldCallAddDistanceFromNewLocationTo() throws Exception {
        trackingStatsHelper.addDistanceFromNewLocationToTotalDistance(currentLocation);
        float distanceFromFirstToSecondLocation = lastLocation.distanceTo(currentLocation); //can't give distance on mocked locations
        verify(stats).addMoreDistanceTraversed(distanceFromFirstToSecondLocation);

        assertEquals(distanceFromFirstToSecondLocation, stats.getDistanceTraversed(), epsilon);
    }

    @Test
    public void testShouldAddMoreTimeSpentTracking() throws Exception {
        int mockedTimeSpent = 5;
        long mockedStartTime = 5000;
        long mockedEndTime = 10000;

        when(stats.getTimeElapsed()).thenReturn(mockedTimeSpent);
        trackingStatsHelper.addTimeSpentWhileTrackingLastStarted(mockedStartTime, mockedEndTime);

        verify(stats).addMoreTimeSpentTracking(mockedTimeSpent);
        assertEquals(mockedTimeSpent, stats.getTimeElapsed());
    }

    @Test
    public void testShouldSetStatsToNewStats() throws Exception {
        Stats mockedStats = new Stats();
        mockedStats.setSessionName("wee");
        mockedStats.addMoreTimeSpentTracking(5);
        mockedStats.addMoreDistanceTraversed(5);
        trackingStatsHelper.setCurrentSessionStats(mockedStats);

        assertEquals(trackingStatsHelper.getCurrentSessionStats(), mockedStats);
    }
}
