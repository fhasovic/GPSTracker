package com.example.filip.gpstracker.ui.tracking.presenter;

import com.example.filip.gpstracker.api.DataManager;
import com.example.filip.gpstracker.api.DataManagerImpl;
import com.example.filip.gpstracker.api.DatabaseHelperImpl;
import com.example.filip.gpstracker.api.FirebaseHelperImpl;
import com.example.filip.gpstracker.api.ResponseListener;
import com.example.filip.gpstracker.ui.tracking.adapter.CustomListAdapterView;
import com.firebase.client.DataSnapshot;

/**
 * Created by Filip on 18/03/2016.
 */
public class StatsAdapterPresenterImpl implements StatsAdapterPresenter {
    private final DataManager dataManager;
    private final CustomListAdapterView listAdapterView;

    public StatsAdapterPresenterImpl(String username, CustomListAdapterView listAdapterView) {
        this.listAdapterView = listAdapterView;
        this.dataManager = new DataManagerImpl(new DatabaseHelperImpl(), new FirebaseHelperImpl(null, username));
    }

    @Override
    public void requestStatsFromFirebase() {
        dataManager.requestListOfStatsForCurrentUserSessions(new ResponseListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot callback) {
                listAdapterView.fillAdapter(dataManager.createListOfStats(callback));
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
