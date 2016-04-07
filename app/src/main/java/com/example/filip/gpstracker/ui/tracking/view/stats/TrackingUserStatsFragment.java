package com.example.filip.gpstracker.ui.tracking.view.stats;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.filip.gpstracker.App;
import com.example.filip.gpstracker.R;
import com.example.filip.gpstracker.pojo.Stats;
import com.example.filip.gpstracker.ui.tracking.adapter.CustomListAdapter;
import com.example.filip.gpstracker.ui.tracking.presenter.stats.TrackingUserStatsPresenter;
import com.example.filip.gpstracker.ui.tracking.presenter.stats.TrackingUserStatsPresenterImpl;

import java.util.ArrayList;

/**
 * Created by Filip on 16/03/2016.
 */
public class TrackingUserStatsFragment extends Fragment implements TrackingUserStatsView {
    private ListView mItemsListView;
    private CustomListAdapter mCustomListAdapter;
    private TrackingUserStatsPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tracking_list_of_stats, container, false);
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
    }

    private void initUI(View view) {
        mItemsListView = (ListView) view.findViewById(R.id.stats_fragment_list_view);
    }

    private void initAdapter() {
        mCustomListAdapter = new CustomListAdapter();
        mItemsListView.setAdapter(mCustomListAdapter);
        requestStatsForCurrentUser();
    }

    private void initPresenter() {
        presenter = new TrackingUserStatsPresenterImpl(App.getInstance().getRequestManager(), this);
    }

    @Override
    public void requestStatsForCurrentUser() {
        presenter.requestStatsFromFirebase();
    }

    @Override
    public void fillAdapterWithItems(ArrayList<Stats> statsArrayList) {
        mCustomListAdapter.fillAdapter(statsArrayList);
    }
}
