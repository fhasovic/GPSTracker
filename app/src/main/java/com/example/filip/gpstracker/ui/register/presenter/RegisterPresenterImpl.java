package com.example.filip.gpstracker.ui.register.presenter;

import com.example.filip.gpstracker.api.RequestManager;
import com.example.filip.gpstracker.api.RequestResponseListener;
import com.example.filip.gpstracker.ui.register.view.RegisterView;

/**
 * Created by Filip on 08/03/2016.
 */
public class RegisterPresenterImpl implements RegisterPresenter {
    private final RegisterView registerView;
    private final RequestManager requestManager;

    public RegisterPresenterImpl(RegisterView registerView, RequestManager requestManager) {
        this.registerView = registerView;
        this.requestManager = requestManager;
    }

    @Override
    public void sendRegistrationAttemptToFirebase(String username, String email, String password) {
        registerView.showProgressBar();
        requestManager.attemptToRegisterTheUser(username, email, password, new RequestResponseListener() {
            @Override
            public void onSuccess() {
                registerView.hideProgressBar();
                registerView.onSuccess();
            }

            @Override
            public void onFailure() {
                registerView.hideProgressBar();
                registerView.onFailure();
            }
        });
    }
}
