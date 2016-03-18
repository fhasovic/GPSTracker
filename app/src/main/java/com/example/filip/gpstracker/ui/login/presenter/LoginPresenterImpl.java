package com.example.filip.gpstracker.ui.login.presenter;

import com.example.filip.gpstracker.api.DataManager;
import com.example.filip.gpstracker.api.DataManagerImpl;
import com.example.filip.gpstracker.api.DatabaseHelperImpl;
import com.example.filip.gpstracker.api.FirebaseHelperImpl;
import com.example.filip.gpstracker.api.ResponseListener;
import com.example.filip.gpstracker.ui.login.view.LoginView;

/**
 * Created by Filip on 08/03/2016.
 */
public class LoginPresenterImpl implements LoginPresenter {
    private final LoginView loginView;
    private final DataManager dataManager;

    public LoginPresenterImpl(LoginView loginView) {
        this.loginView = loginView;
        this.dataManager = new DataManagerImpl(new DatabaseHelperImpl(), new FirebaseHelperImpl(null, null));
    }

    @Override
    public void sendLoginRequestToFirebase(String email, String password) {
        loginView.showProgressBar();
        dataManager.sendUserLoginAttemptToFirebase(email, password, new ResponseListener<String>() {
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
