package com.example.filip.gpstracker.ui.tracking.view.stats;

import com.example.filip.gpstracker.pojo.Stats;

import java.util.ArrayList;

/**
 * Created by Filip on 06/04/2016.
 */
public interface TrackingUserStatsView {
    void requestStatsForCurrentUser();

    void fillAdapterWithItems(ArrayList<Stats> statsArrayList);
}
