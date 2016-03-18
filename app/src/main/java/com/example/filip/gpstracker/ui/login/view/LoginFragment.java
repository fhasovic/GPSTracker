package com.example.filip.gpstracker.ui.login.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.filip.gpstracker.R;
import com.example.filip.gpstracker.constants.StringConstants;
import com.example.filip.gpstracker.ui.login.presenter.LoginPresenter;
import com.example.filip.gpstracker.ui.login.presenter.LoginPresenterImpl;
import com.example.filip.gpstracker.ui.tracking.view.TrackingActivity;

/**
 * Created by Filip on 08/03/2016.
 */
public class LoginFragment extends Fragment implements View.OnClickListener, LoginView {
    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private Button mLoginButton;
    private ProgressBar mSpinningProgressBar;
    private LoginPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI(view);
    }

    private void initUI(View view) {
        mEmailEditText = (EditText) view.findViewById(R.id.login_fragment_email_edit_text);
        mPasswordEditText = (EditText) view.findViewById(R.id.login_fragment_password_edit_text);
        mLoginButton = (Button) view.findViewById(R.id.login_fragment_attempt_to_login_button);
        mLoginButton.setOnClickListener(this);
        mSpinningProgressBar = (ProgressBar) view.findViewById(R.id.fragment_progress_bar);
        mSpinningProgressBar.setVisibility(View.GONE);
    }

    private void initPresenter() {
        presenter = new LoginPresenterImpl(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        initPresenter();
    }

    @Override
    public void onClick(View v) {
        presenter.sendLoginRequestToFirebase(mEmailEditText.getText().toString(), mPasswordEditText.getText().toString());
    }

    @Override
    public void onSuccessfulLogin(String username) {
        Intent i = new Intent(getActivity(), TrackingActivity.class);
        i.putExtra(StringConstants.USERNAME_KEY, username);
        startActivity(i);
    }

    @Override
    public void onFailedLogin() {
        Toast.makeText(getActivity().getApplicationContext(), R.string.failed_login_error_message, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showProgressBar() {
        mSpinningProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        mSpinningProgressBar.setVisibility(View.GONE);
    }
}
