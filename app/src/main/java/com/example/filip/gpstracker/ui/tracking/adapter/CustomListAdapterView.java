package com.example.filip.gpstracker.ui.tracking.adapter;

import com.example.filip.gpstracker.model.Stats;

import java.util.ArrayList;

/**
 * Created by Filip on 18/03/2016.
 */
public interface CustomListAdapterView {
    void fillAdapter(ArrayList<Stats> dataSource);

    void requestStatsFromFirebase();
}
