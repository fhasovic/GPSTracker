package com.example.filip.gpstracker.ui.tracking.view.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.filip.gpstracker.R;
import com.example.filip.gpstracker.constants.StringConstants;
import com.example.filip.gpstracker.ui.tracking.presenter.UserFragmentPresenter;
import com.example.filip.gpstracker.ui.tracking.presenter.UserFragmentPresenterImpl;
import com.example.filip.gpstracker.ui.tracking.view.map.TrackingMapFragment;

/**
 * Created by Filip on 09/03/2016.
 */
public class TrackingUserFragment extends Fragment implements View.OnClickListener, UserFragmentView {
    private EditText mNewSessionEditText;
    private Button mStartNewSessionButton;
    private UserFragmentPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tracking_user, container, false);
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
    public void onClick(View v) {
        if (v == mStartNewSessionButton && !mNewSessionEditText.getText().toString().equals("")) //if the sessionName isn't empty
            presenter.checkIfTheSessionAlreadyExists(mNewSessionEditText.getText().toString()); //checks if there already is a session with the same name, if not starts a new one, if it does, toast
    }

    private void initUI(View view) {
        mNewSessionEditText = (EditText) view.findViewById(R.id.user_new_session_fragment_edit_text);
        mStartNewSessionButton = (Button) view.findViewById(R.id.user_new_session_fragment_start_button);
        mStartNewSessionButton.setOnClickListener(this);
    }

    private Bundle createDataBundle() {
        Bundle data = new Bundle();
        data.putString(StringConstants.USERNAME_KEY, getActivity().getIntent().getStringExtra(StringConstants.USERNAME_KEY));
        data.putString(StringConstants.SESSION_KEY, mNewSessionEditText.getText().toString());
        return data;
    }

    private void initPresenter() {
        presenter = new UserFragmentPresenterImpl(this, getActivity().getIntent().getStringExtra(StringConstants.USERNAME_KEY));
    }

    @Override
    public void onSuccess() {
        getActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.tracking_activity_frame_layout, TrackingMapFragment.newInstance(createDataBundle()), StringConstants.MAP_FRAGMENT)
                .commit();
    }

    @Override
    public void onFailure() {
        Toast.makeText(getActivity().getApplicationContext(), R.string.session_already_exists_toast_message, Toast.LENGTH_SHORT).show();
    }
}
