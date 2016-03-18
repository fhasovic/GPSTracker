package com.example.filip.gpstracker.ui.login.view;

/**
 * Created by Filip on 08/03/2016.
 */
public interface LoginView {
    void onSuccessfulLogin(String username);

    void onFailedLogin();

    void showProgressBar();

    void hideProgressBar();
}
