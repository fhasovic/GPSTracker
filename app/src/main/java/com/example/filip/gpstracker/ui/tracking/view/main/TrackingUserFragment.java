package com.example.filip.gpstracker.ui.tracking.view.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.filip.gpstracker.App;
import com.example.filip.gpstracker.R;
import com.example.filip.gpstracker.constants.Constants;
import com.example.filip.gpstracker.ui.tracking.presenter.main.UserFragmentPresenter;
import com.example.filip.gpstracker.ui.tracking.presenter.main.UserFragmentPresenterImpl;
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
        if (v == mStartNewSessionButton)
            handleButtonClick();//checks if there already is a session with the same name, if not starts a new one, if it does, toast
    }

    private void handleButtonClick() {
        if (mNewSessionEditText.getText().toString().equals(""))
            mNewSessionEditText.setError(getActivity().getString(R.string.session_name_empty_error_message));
        else
            presenter.checkIfTheSessionAlreadyExists(mNewSessionEditText.getText().toString());

    }

    private void initUI(View view) {
        mNewSessionEditText = (EditText) view.findViewById(R.id.user_new_session_fragment_edit_text);
        mStartNewSessionButton = (Button) view.findViewById(R.id.user_new_session_fragment_start_button);
        mStartNewSessionButton.setOnClickListener(this);
    }

    private Bundle createDataBundle() {
        Bundle data = new Bundle();
        data.putString(Constants.SESSION_KEY, mNewSessionEditText.getText().toString());
        return data;
    }

    private void initPresenter() {
        presenter = new UserFragmentPresenterImpl(this, App.getInstance().getRequestManager());
    }

    @Override
    public void onSuccess() {
        getActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.tracking_activity_frame_layout, TrackingMapFragment.newInstance(createDataBundle()), Constants.MAP_FRAGMENT)
                .commit();
    }

    @Override
    public void onFailure() {
        mNewSessionEditText.setError(getActivity().getString(R.string.session_already_exists_error_message));
    }
}
