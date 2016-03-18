package com.example.filip.gpstracker.ui.tracking.presenter;

import com.example.filip.gpstracker.api.DataManager;
import com.example.filip.gpstracker.api.DataManagerImpl;
import com.example.filip.gpstracker.api.DatabaseHelperImpl;
import com.example.filip.gpstracker.api.FirebaseHelperImpl;
import com.example.filip.gpstracker.api.ResponseListener;
import com.example.filip.gpstracker.ui.tracking.adapter.CustomRecyclerAdapterView;
import com.firebase.client.DataSnapshot;

/**
 * Created by Filip on 10/03/2016.
 */
public class SessionsAdapterPresenterImpl implements SessionsFragmentAdapterPresenter {
    private final CustomRecyclerAdapterView adapterView;
    private final DataManager dataManager;

    public SessionsAdapterPresenterImpl(CustomRecyclerAdapterView adapterView, String username) {
        this.adapterView = adapterView;
        this.dataManager = new DataManagerImpl(new DatabaseHelperImpl(), new FirebaseHelperImpl(null, username));
    }

    @Override
    public void requestListOfSessions() {
        dataManager.requestListOfSessions(new ResponseListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot callback) {
                adapterView.addAllItems(dataManager.createListOfSessions(callback));
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    public void sendDeleteRequest(String sessionName) {
        dataManager.deleteSessionFromFirebase(sessionName);
    }
}
