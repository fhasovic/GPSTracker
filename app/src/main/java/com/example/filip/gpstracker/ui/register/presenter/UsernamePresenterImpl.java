package com.example.filip.gpstracker.ui.register.presenter;

import com.example.filip.gpstracker.api.RequestManager;
import com.example.filip.gpstracker.api.RequestResponseListener;
import com.example.filip.gpstracker.ui.register.view.UsernameFragmentView;

/**
 * Created by Filip on 08/03/2016.
 */
public class UsernamePresenterImpl implements UsernamePresenter {
    private final RequestManager requestManager;
    private final UsernameFragmentView usernameFragmentView;

    public UsernamePresenterImpl(UsernameFragmentView usernameFragmentView, RequestManager requestManager) {
        this.usernameFragmentView = usernameFragmentView;
        this.requestManager = requestManager;
    }

    @Override
    public void checkIfUsernameIsAvailable(String username) {
        requestManager.checkIfUsernameIsAlreadyTaken(username, new RequestResponseListener() {
            @Override
            public void onSuccess() {
                usernameFragmentView.onSuccess();
            }

            @Override
            public void onFailure() {
                usernameFragmentView.onFailure();
            }
        });
    }
}
