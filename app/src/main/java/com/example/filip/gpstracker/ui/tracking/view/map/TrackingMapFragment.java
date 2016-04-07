package com.example.filip.gpstracker.ui.tracking.view.map;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.filip.gpstracker.App;
import com.example.filip.gpstracker.R;
import com.example.filip.gpstracker.helpers.data.TrackingStatsHelperImpl;
import com.example.filip.gpstracker.helpers.tracking.TrackingHelper;
import com.example.filip.gpstracker.helpers.tracking.TrackingHelperImpl;
import com.example.filip.gpstracker.constants.Constants;
import com.example.filip.gpstracker.pojo.Stats;
import com.example.filip.gpstracker.ui.tracking.presenter.map.MapFragmentPresenter;
import com.example.filip.gpstracker.ui.tracking.presenter.map.MapFragmentPresenterImpl;
import com.example.filip.gpstracker.utils.NetworkUtils;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

/**
 * Created by Filip on 05/03/2016.
 */
public class TrackingMapFragment extends Fragment implements OnMapReadyCallback, TrackingFragmentView, View.OnClickListener {
    private GoogleMap mGoogleMap;
    private FloatingActionButton floatingActionButton;

    private TrackingHelper helper;
    private MapFragmentPresenter presenter;

    private Handler trackingHandler;
    private Runnable locationRunnable;

    public static TrackingMapFragment newInstance(Bundle data) {
        TrackingMapFragment f = new TrackingMapFragment();
        f.setArguments(data);
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tracking_map, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        checkIfLocationIsAvailable();
        initUI(view);
    }

    @Override
    public void onStart() {
        super.onStart();
        initMap();
        initTrackingHelper();
        initPresenter();
        presenter.requestStatsForTrackingSession(); //gets the stats for the current session
        presenter.requestLocationsFromFirebase(); //loads all the previous markers for display
        initLocationServiceThread(); //preps the service
    }

    @Override
    public void onPause() {
        super.onPause();
        if (helper.getTrackingStatus())
            stopTracking();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mGoogleMap.setMyLocationEnabled(true);
    }

    private void initUI(View view) {
        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.map_fragment_floating_action_button);
        floatingActionButton.setImageResource(R.drawable.ic_location_on_black_24dp);
        floatingActionButton.setOnClickListener(this);
    }

    private void initMap() {
        SupportMapFragment fragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.google_maps_fragment);
        fragment.getMapAsync(this);
    }

    private void initTrackingHelper() {
        GoogleApiClient client = new GoogleApiClient.Builder(getActivity()).addApi(LocationServices.API).build();
        helper = new TrackingHelperImpl(client);
    }

    private void initPresenter() {
        Bundle data = this.getArguments();
        App.getInstance().setCurrentSession(data.getString(Constants.SESSION_KEY));
        presenter = new MapFragmentPresenterImpl(this, App.getInstance().getRequestManager(), new TrackingStatsHelperImpl(new Stats(), null));
    }

    private void initLocationServiceThread() {
        trackingHandler = new Handler();
        locationRunnable = new Runnable() {
            @Override
            public void run() {
                sendLocationToPresenter(helper.getLocation());
                trackingHandler.postDelayed(locationRunnable, 5000);
            }
        };
    }

    private void startLocationRunnable() {
        locationRunnable.run();
    }

    private void stopLocationRunnable() {
        trackingHandler.removeCallbacks(locationRunnable);
    }

    @Override
    public void addMarkerToGoogleMaps(Location location, Location lastLocation) {
        LatLng currentLocationMarkerCoordinates = new LatLng(location.getLatitude(), location.getLongitude());
        mGoogleMap.addMarker(new MarkerOptions().position(currentLocationMarkerCoordinates)); //adds a marker
        if (lastLocation != null) { //if this isn't the first location, links the current location with the last one
            LatLng lastLocationMarkerCoordinates = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
            mGoogleMap.addPolyline(new PolylineOptions().add(lastLocationMarkerCoordinates).add(currentLocationMarkerCoordinates).color(Color.BLUE).width(5));
        }
    }

    @Override
    public void sendLocationToPresenter(Location location) {
        presenter.sendLocationToFirebase(location);
    }

    @Override
    public void showStatsDialog(int timeElapsed, float distanceTraversed, float averageSpeed) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(getActivity().getString(R.string.tracking_stopped_dialog_message, distanceTraversed, timeElapsed, averageSpeed));
        builder.setNeutralButton(getActivity().getString(R.string.stats_alert_dialog_neutral_button_message), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onClick(View v) {
        if (v == floatingActionButton)
            handleFloatingActionButtonClick();
    }

    private void handleFloatingActionButtonClick() {
        if (!helper.getTrackingStatus()) { //if the status is TRACKING OFF
            startTracking();
            floatingActionButton.setImageResource(R.drawable.ic_location_off_black_24dp);
            Toast.makeText(getActivity().getApplicationContext(), R.string.tracking_started_toast_message, Toast.LENGTH_SHORT).show();
        } else if (helper.getTrackingStatus()) { //if the status is TRACKING ON
            stopTracking();
            floatingActionButton.setImageResource(R.drawable.ic_location_on_black_24dp);
            Toast.makeText(getActivity().getApplicationContext(), R.string.tracking_stopped_toast_message, Toast.LENGTH_SHORT).show();
        }
    }

    private void startTracking() {
        helper.connectClient(); //establishes an internet(gps) connection
        startLocationRunnable(); //starts the tracking
        helper.setTrackingStatus(true); //TRACKING ON
        helper.setStartTime(System.currentTimeMillis());//time tracking first started/last resumed
    }

    private void stopTracking() {
        removeCallbacks();
        helper.setTrackingStatus(false); //TRACKING OFF
        presenter.sendStatsToFirebase(helper.getStartTime(), System.currentTimeMillis()); //push the results
    }

    private void removeCallbacks() {
        helper.disconnectClient();
        stopLocationRunnable();
    }

    private void checkIfLocationIsAvailable() {
        if (!NetworkUtils.checkIfLocationServiceIsActivated(getActivity().getApplicationContext()))
            Toast.makeText(getActivity().getApplicationContext(), getActivity().getString(R.string.turn_location_on_toast), Toast.LENGTH_LONG).show();
    }
}