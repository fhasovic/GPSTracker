package com.example.filip.gpstracker.ui.login.presenter;

import com.example.filip.gpstracker.api.RequestManager;
import com.example.filip.gpstracker.api.ResponseListener;
import com.example.filip.gpstracker.ui.login.view.LoginView;

/**
 * Created by Filip on 08/03/2016.
 */
public class LoginPresenterImpl implements LoginPresenter {
    private final LoginView loginView;
    private final RequestManager requestManager;

    public LoginPresenterImpl(LoginView loginView, RequestManager requestManager) {
        this.loginView = loginView;
        this.requestManager = requestManager;
    }

    @Override
    public void sendLoginRequestToFirebase(String email, String password) {
        loginView.showProgressBar();
        requestManager.attemptToLogTheUserIn(email, password, new ResponseListener<String>() {
            @Override
            public void onSuccess(String callback) {
                loginView.hideProgressBar();
                loginView.onSuccessfulLogin(callback);
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
                loginView.hideProgressBar();
                loginView.onFailedLogin();
            }
        });
    }
}
