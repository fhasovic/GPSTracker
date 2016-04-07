package com.example.filip.gpstracker.ui.tracking.presenter.stats;

import com.example.filip.gpstracker.api.RequestManager;
import com.example.filip.gpstracker.api.ResponseListener;
import com.example.filip.gpstracker.pojo.Stats;
import com.example.filip.gpstracker.ui.tracking.view.stats.TrackingUserStatsView;
import com.example.filip.gpstracker.utils.FirebaseUtils;
import com.example.filip.gpstracker.utils.StringUtils;
import com.firebase.client.DataSnapshot;

import java.util.ArrayList;

/**
 * Created by Filip on 06/04/2016.
 */
public class TrackingUserStatsPresenterImpl implements TrackingUserStatsPresenter {
    private final RequestManager requestManager;
    private final TrackingUserStatsView trackingUserStatsView;

    public TrackingUserStatsPresenterImpl(RequestManager requestManager, TrackingUserStatsView trackingUserStatsView) {
        this.requestManager = requestManager;
        this.trackingUserStatsView = trackingUserStatsView;
    }

    @Override
    public void requestStatsFromFirebase() {
        requestManager.requestListOfStatsForCurrentUserSessions(new ResponseListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot callback) {
                if (callback != null) {
                    ArrayList<Stats> arrayList = FirebaseUtils.getListOfStats(callback);
                    trackingUserStatsView.fillAdapterWithItems(arrayList);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                StringUtils.logError(t);
            }
        });
    }
}
