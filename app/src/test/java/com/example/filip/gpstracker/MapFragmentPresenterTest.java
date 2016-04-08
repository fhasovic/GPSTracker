package com.example.filip.gpstracker;

import android.location.Location;

import com.example.filip.gpstracker.api.RequestManager;
import com.example.filip.gpstracker.api.ResponseListener;
import com.example.filip.gpstracker.helpers.data.TrackingStatsHelper;
import com.example.filip.gpstracker.pojo.LocationWrapper;
import com.example.filip.gpstracker.pojo.Stats;
import com.example.filip.gpstracker.ui.tracking.presenter.map.MapFragmentPresenter;
import com.example.filip.gpstracker.ui.tracking.presenter.map.MapFragmentPresenterImpl;
import com.example.filip.gpstracker.ui.tracking.view.map.TrackingFragmentView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

/**
 * Created by Filip on 08/04/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class MapFragmentPresenterTest {
    private MapFragmentPresenter mapFragmentPresenter;

    @Mock
    private TrackingFragmentView trackingFragmentView;
    @Mock
    private RequestManager requestManager;
    @Mock
    private TrackingStatsHelper trackingStatsHelper;
    @Mock
    private Location location;
    @Mock
    private Stats mockedStats;

    @Mock
    private ResponseListener<LocationWrapper> mockedLocationWrapperListener;

    @Before
    public void setUp() throws Exception {
        mapFragmentPresenter = new MapFragmentPresenterImpl(trackingFragmentView, requestManager, trackingStatsHelper);
    }

    @Test
    public void testShouldCallSendLocationToView() throws Exception {
        mapFragmentPresenter.sendLocationToView(location);
        verify(trackingFragmentView).addMarkerToGoogleMaps(location, trackingStatsHelper.getLastLocation());
        verify(trackingStatsHelper).setLastLocation(location);
    }

    @Test
    public void testShouldCallSendLocationToFirebase() throws Exception {
        mapFragmentPresenter.sendLocationToFirebase(location);

        verify(requestManager).sendLocationToFirebase(location);
        verify(trackingStatsHelper).addDistanceFromNewLocationToTotalDistance(location);
    }

    @Test
    public void testShouldCallSendStatsToFirebase() throws Exception {
        long mockedStartTime = 5000;
        long mockedEndTime = 10000;
        when(trackingStatsHelper.getCurrentSessionStats()).thenReturn(mockedStats);

        mapFragmentPresenter.sendStatsToFirebase(mockedStartTime, mockedEndTime);

        verify(trackingStatsHelper).addTimeSpentWhileTrackingLastStarted(mockedStartTime, mockedEndTime);
        verify(trackingStatsHelper).getCurrentSessionStats();
        verify(requestManager).sendStatsToFirebase(mockedStats);
        verify(trackingFragmentView).showStatsDialog(mockedStats.getTimeElapsed(), mockedStats.getDistanceTraversed(), 0);
    }
}
