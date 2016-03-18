package com.example.filip.gpstracker.ui.register.presenter;

/**
 * Created by Filip on 08/03/2016.
 */
public interface RegisterPresenter {
    void sendRegistrationAttemptToFirebase(String username, String email, String password);
}
