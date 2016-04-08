package com.example.filip.gpstracker.ui.tracking.view.sessions;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.filip.gpstracker.App;
import com.example.filip.gpstracker.R;
import com.example.filip.gpstracker.constants.Constants;
import com.example.filip.gpstracker.pojo.Session;
import com.example.filip.gpstracker.ui.tracking.adapter.CustomRecyclerAdapter;
import com.example.filip.gpstracker.ui.tracking.presenter.sessions.TrackingUserSessionsPresenter;
import com.example.filip.gpstracker.ui.tracking.presenter.sessions.TrackingUserSessionsPresenterImpl;
import com.example.filip.gpstracker.ui.tracking.view.map.TrackingMapFragment;

import java.util.ArrayList;

/**
 * Created by Filip on 09/03/2016.
 */
public class TrackingUserSessionsFragment extends Fragment implements ItemListener, UserSessionView {
    private RecyclerView mSessionsRecyclerView;
    private CustomRecyclerAdapter adapter;
    private TrackingUserSessionsPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_trackings_list_of_sessions, container, false);
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
        initAdapter();
        requestSessionsForAdapter();
    }

    private void initUI(View view) {
        mSessionsRecyclerView = (RecyclerView) view.findViewById(R.id.user_previous_sessions_fragment_recycler_view);
        mSessionsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mSessionsRecyclerView.setHasFixedSize(true);
    }

    private void initAdapter() {
        adapter = new CustomRecyclerAdapter(this);
        mSessionsRecyclerView.setAdapter(adapter);
    }

    private void initPresenter() {
        presenter = new TrackingUserSessionsPresenterImpl(App.getInstance().getRequestManager(), this);
    }

    private Bundle createDataBundle(String sessionName) {
        Bundle data = new Bundle();
        data.putString(Constants.USERNAME_KEY, getActivity().getIntent().getStringExtra(Constants.USERNAME_KEY));
        data.putString(Constants.SESSION_KEY, sessionName);
        return data;
    }

    @Override
    public void onItemClick(String sessionName) {
        resumeATrackingSession(sessionName);
    }

    @Override
    public void onLongItemClick(String sessionName) {
        showDeleteDialog(sessionName);
    }

    private void resumeATrackingSession(String sessionName) {
        getActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.tracking_activity_frame_layout, TrackingMapFragment.newInstance(createDataBundle(sessionName)), Constants.MAP_FRAGMENT)
                .commit();
    }

    private void showDeleteDialog(final String sessionName) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(getActivity().getString(R.string.dialog_message, sessionName));
        builder.setPositiveButton(getActivity().getString(R.string.dialog_positive_button_text), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                presenter.deleteSession(sessionName);
                presenter.requestSessionsFromFirebase(); //updates the adapter
            }
        });

        builder.setNegativeButton(getActivity().getString(R.string.dialog_negative_button_text), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void requestSessionsForAdapter() {
        presenter.requestSessionsFromFirebase();
    }

    @Override
    public void fillAdapterWithItems(ArrayList<Session> sessionArrayList) {
        adapter.addAllItems(sessionArrayList);
    }
}
