package com.example.filip.gpstracker;

import com.example.filip.gpstracker.helpers.tracking.TrackingHelper;
import com.example.filip.gpstracker.helpers.tracking.TrackingHelperImpl;
import com.google.android.gms.common.api.GoogleApiClient;

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
public class TrackingHelperTest {
    private TrackingHelper trackingHelper;
    @Mock
    private GoogleApiClient mockedGoogleApiClient;

    @Before
    public void setUp() throws Exception {
        trackingHelper = new TrackingHelperImpl(mockedGoogleApiClient);
    }

    @Test
    public void testShouldCallConnectClient() throws Exception {
        trackingHelper.connectClient();

        verify(mockedGoogleApiClient).connect();
    }
    @Test
    public void testShouldCallDisconnectClient() throws Exception {
        trackingHelper.disconnectClient();

        verify(mockedGoogleApiClient).disconnect();
    }

    @Test
    public void testShouldCallGetTrackingStatus() throws Exception {
        assertEquals(trackingHelper.getTrackingStatus(), false);
    }
    @Test
    public void testShouldCallGetStartTime() throws Exception {
        assertEquals(trackingHelper.getStartTime(), 0);
    }

    @Test
    public void testShouldSetStartTimeToMockedValue() throws Exception {
        long mockedStartTime = 5000;
        trackingHelper.setStartTime(5000);
        assertEquals(trackingHelper.getStartTime(), mockedStartTime);
    }

    @Test
    public void testShouldSetTrackingStatusToTrue() throws Exception {
        trackingHelper.setTrackingStatus(true);
        assertEquals(true, trackingHelper.getTrackingStatus());
    }
}
