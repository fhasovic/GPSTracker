package com.example.filip.gpstracker.ui.register.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.filip.gpstracker.App;
import com.example.filip.gpstracker.R;
import com.example.filip.gpstracker.constants.Constants;
import com.example.filip.gpstracker.ui.register.presenter.RegisterPresenter;
import com.example.filip.gpstracker.ui.register.presenter.RegisterPresenterImpl;

/**
 * Created by Filip on 08/03/2016.
 */
public class AccountFragment extends Fragment implements RegisterView, View.OnClickListener {
    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private Button mFinishRegistrationButton;
    private ProgressBar mSpinningProgressBar;
    private Toolbar mToolbar;
    private RegisterPresenter presenter;

    public static AccountFragment newInstance(Bundle data) {
        AccountFragment f = new AccountFragment();
        f.setArguments(data);
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI(view);
    }

    @Override
    public void onStart() {
        super.onStart();
        initPresenter();
    }

    private void initUI(View view) {
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        mToolbar.setTitle(R.string.choose_your_account_details_toolbar_title);

        mEmailEditText = (EditText) view.findViewById(R.id.register_fragment_email_edit_text);
        mPasswordEditText = (EditText) view.findViewById(R.id.register_fragment_password_edit_text);

        mFinishRegistrationButton = (Button) view.findViewById(R.id.register_fragment_finish_registration_button);
        mFinishRegistrationButton.setOnClickListener(this);

        mSpinningProgressBar = (ProgressBar) view.findViewById(R.id.fragment_progress_bar);
        mSpinningProgressBar.setVisibility(View.GONE);
    }

    private void initPresenter() {
        presenter = new RegisterPresenterImpl(this, App.getInstance().getRequestManager());
    }

    @Override
    public void onSuccess() {
        Toast.makeText(getActivity().getApplicationContext(), R.string.successful_registration_toast, Toast.LENGTH_SHORT).show();
        getActivity().finish();
    }

    @Override
    public void onFailure() {
        mEmailEditText.setError(getActivity().getString(R.string.registration_failed_error_message));
    }

    @Override
    public void hideProgressBar() {
        mSpinningProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showProgressBar() {
        mSpinningProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        presenter.sendRegistrationAttemptToFirebase(this.getArguments().getString(Constants.USERNAME_KEY), mEmailEditText.getText().toString(), mPasswordEditText.getText().toString());
    }
}
