package com.example.filip.gpstracker.ui.register.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.filip.gpstracker.App;
import com.example.filip.gpstracker.R;
import com.example.filip.gpstracker.constants.Constants;
import com.example.filip.gpstracker.ui.register.presenter.UsernamePresenter;
import com.example.filip.gpstracker.ui.register.presenter.UsernamePresenterImpl;

/**
 * Created by Filip on 08/03/2016.
 */
public class UsernameFragment extends Fragment implements UsernameFragmentView, View.OnClickListener {
    private TextView mUsernameTextView;
    private Button mContinueWithRegistrationButton;
    private Toolbar mToolbar;
    private UsernamePresenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_username, container, false);
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

    @Override
    public void onSuccess() {
        Bundle data = new Bundle();
        data.putString(Constants.USERNAME_KEY, mUsernameTextView.getText().toString());
        getActivity().
                getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.register_activity_frame_layout, AccountFragment.newInstance(data))
                .addToBackStack(Constants.REGISTER_FRAGMENT)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }

    @Override
    public void onFailure() {
        mUsernameTextView.setError(getActivity().getString(R.string.username_taken_error_message));
    }

    private void initUI(View view) {
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        mToolbar.setTitle(R.string.choose_a_username_toolbar_title);
        mUsernameTextView = (EditText) view.findViewById(R.id.register_fragment_username_edit_text);
        mContinueWithRegistrationButton = (Button) view.findViewById(R.id.register_fragment_continue_registration_button);
        mContinueWithRegistrationButton.setOnClickListener(this);
    }

    private void initPresenter() {
        presenter = new UsernamePresenterImpl(this, App.getInstance().getRequestManager());
    }

    @Override
    public void onClick(View v) {
        if (v == mContinueWithRegistrationButton)
            handleButtonClick();
    }

    private void handleButtonClick() {
        if (mUsernameTextView.getText().toString().equals(""))
            mUsernameTextView.setError(getActivity().getString(R.string.username_fragment_empty_field_error_message));
        else
            presenter.checkIfUsernameIsAvailable(mUsernameTextView.getText().toString());
    }
}
