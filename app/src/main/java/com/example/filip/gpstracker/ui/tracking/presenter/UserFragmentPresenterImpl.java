package com.example.filip.gpstracker.ui.tracking.presenter;

import com.example.filip.gpstracker.api.DataManager;
import com.example.filip.gpstracker.api.DataManagerImpl;
import com.example.filip.gpstracker.api.DatabaseHelperImpl;
import com.example.filip.gpstracker.api.FirebaseHelperImpl;
import com.example.filip.gpstracker.api.RequestResponseListener;
import com.example.filip.gpstracker.ui.tracking.view.user.UserFragmentView;

/**
 * Created by Filip on 10/03/2016.
 */
public class UserFragmentPresenterImpl implements UserFragmentPresenter {
    private final UserFragmentView userFragmentView;
    private final DataManager dataManager;

    public UserFragmentPresenterImpl(UserFragmentView userFragmentView, String username) {
        this.userFragmentView = userFragmentView;
        this.dataManager = new DataManagerImpl(new DatabaseHelperImpl(), new FirebaseHelperImpl(null, username));
    }

    @Override
    public void checkIfTheSessionAlreadyExists(final String sessionName) {
        dataManager.checkIfTheUserSessionAlreadyExists(sessionName, new RequestResponseListener() {
            @Override
            public void onSuccess() {
                userFragmentView.onSuccess();
            }

            @Override
            public void onFailure(Throwable t) {
                if (t != null)
                    t.printStackTrace();
                userFragmentView.onFailure();
            }
        });
    }
}
