package com.example.filip.gpstracker.ui.register.presenter;

import com.example.filip.gpstracker.api.DataManager;
import com.example.filip.gpstracker.api.DataManagerImpl;
import com.example.filip.gpstracker.api.DatabaseHelperImpl;
import com.example.filip.gpstracker.api.FirebaseHelperImpl;
import com.example.filip.gpstracker.api.RequestResponseListener;
import com.example.filip.gpstracker.ui.register.view.RegisterView;

/**
 * Created by Filip on 08/03/2016.
 */
public class RegisterPresenterImpl implements RegisterPresenter {
    private final RegisterView registerView;
    private final DataManager dataManager;

    public RegisterPresenterImpl(RegisterView registerView) {
        this.registerView = registerView;
        this.dataManager = new DataManagerImpl(new DatabaseHelperImpl(), new FirebaseHelperImpl(null, null));
    }

    @Override
    public void sendRegistrationAttemptToFirebase(String username, String email, String password) {
        registerView.showProgressBar();
        dataManager.sendRegistrationAttemptToFirebase(username, email, password, new RequestResponseListener() {
            @Override
            public void onSuccess() {
                registerView.hideProgressBar();
                registerView.onSuccess();
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
                registerView.hideProgressBar();
                registerView.onFailure();
            }
        });
    }
}
