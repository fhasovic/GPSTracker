package com.example.filip.gpstracker.ui.tracking.view.user;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.filip.gpstracker.ItemListener;
import com.example.filip.gpstracker.R;
import com.example.filip.gpstracker.constants.StringConstants;
import com.example.filip.gpstracker.ui.tracking.adapter.CustomRecyclerRecyclerAdapter;
import com.example.filip.gpstracker.ui.tracking.view.map.TrackingMapFragment;

/**
 * Created by Filip on 09/03/2016.
 */
public class TrackingUserSessionsFragment extends Fragment implements ItemListener {
    private RecyclerView mSessionsRecyclerView;
    private CustomRecyclerRecyclerAdapter adapter;

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
        initAdapter();
    }

    private void initUI(View view) {
        mSessionsRecyclerView = (RecyclerView) view.findViewById(R.id.user_previous_sessions_fragment_recycler_view);
        mSessionsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mSessionsRecyclerView.setHasFixedSize(true);
    }

    private void initAdapter() {
        adapter = new CustomRecyclerRecyclerAdapter(getActivity().getIntent().getStringExtra(StringConstants.USERNAME_KEY), this);
        mSessionsRecyclerView.setAdapter(adapter);
        adapter.fillAdapterWithItems();
    }


    private Bundle createDataBundle(String sessionName) {
        Bundle data = new Bundle();
        data.putString(StringConstants.USERNAME_KEY, getActivity().getIntent().getStringExtra(StringConstants.USERNAME_KEY));
        data.putString(StringConstants.SESSION_KEY, sessionName);
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
                .replace(R.id.tracking_activity_frame_layout, TrackingMapFragment.newInstance(createDataBundle(sessionName)), StringConstants.MAP_FRAGMENT)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }

    private void showDeleteDialog(final String sessionName) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(getActivity().getString(R.string.dialog_message) + sessionName);
        builder.setPositiveButton(getActivity().getString(R.string.dialog_positive_button_text), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                adapter.deleteItem(sessionName);
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
}
