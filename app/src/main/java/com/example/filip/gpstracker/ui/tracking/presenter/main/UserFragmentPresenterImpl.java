package com.example.filip.gpstracker.ui.tracking.presenter.main;

import com.example.filip.gpstracker.api.RequestManager;
import com.example.filip.gpstracker.api.RequestResponseListener;
import com.example.filip.gpstracker.ui.tracking.view.main.UserFragmentView;

/**
 * Created by Filip on 10/03/2016.
 */
public class UserFragmentPresenterImpl implements UserFragmentPresenter {
    private final UserFragmentView userFragmentView;
    private final RequestManager requestManager;

    public UserFragmentPresenterImpl(UserFragmentView userFragmentView, RequestManager requestManager) {
        this.userFragmentView = userFragmentView;
        this.requestManager = requestManager;
    }

    @Override
    public void checkIfTheSessionAlreadyExists(final String sessionName) {
        requestManager.checkIfTheUserSessionAlreadyExists(sessionName, new RequestResponseListener() {
            @Override
            public void onSuccess() {
                userFragmentView.onSuccess();
            }

            @Override
            public void onFailure() {
                userFragmentView.onFailure();
            }
        });
    }
}
