package com.example.filip.gpstracker.ui.tracking.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.filip.gpstracker.R;
import com.example.filip.gpstracker.model.Stats;
import com.example.filip.gpstracker.ui.tracking.presenter.StatsAdapterPresenter;
import com.example.filip.gpstracker.ui.tracking.presenter.StatsAdapterPresenterImpl;

import java.util.ArrayList;

/**
 * Created by Filip on 17/03/2016.
 */
public class CustomListAdapter extends BaseAdapter implements CustomListAdapterView {
    private final ArrayList<Stats> mItemList = new ArrayList<>();
    private final StatsAdapterPresenter presenter;

    public CustomListAdapter(String username) {
        this.presenter = new StatsAdapterPresenterImpl(username, this);
    }

    @Override
    public int getCount() {
        return mItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return mItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Stats stats = mItemList.get(position);
        TextView mSessionNameTextView;
        TextView mSessionTimeElapsedTextView;
        TextView mSessionDistanceTraversedTextView;
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.stats_fragment_session_list_item, parent, false);

        mSessionNameTextView = (TextView) convertView.findViewById(R.id.stats_fragment_session_name_text_view);
        mSessionTimeElapsedTextView = (TextView) convertView.findViewById(R.id.stats_fragment_session_time_text_view);
        mSessionDistanceTraversedTextView = (TextView) convertView.findViewById(R.id.stats_fragment_session_distance_text_view);
        String sessionName = "Session name: " + stats.getSessionName();
        String timeElapsed = "Time elapsed: " + String.valueOf(stats.getTimeElapsed()) + " seconds.";
        String distanceTraversed = "Distance traversed: " + String.valueOf(stats.getDistanceTraversed()) + " meters.";

        mSessionNameTextView.setText(sessionName);
        mSessionTimeElapsedTextView.setText(timeElapsed);
        mSessionDistanceTraversedTextView.setText(distanceTraversed);
        return convertView;
    }

    @Override
    public void fillAdapter(ArrayList<Stats> dataSource) {
        mItemList.clear();
        mItemList.addAll(dataSource);
        notifyDataSetChanged();
    }

    @Override
    public void requestStatsFromFirebase() {
        presenter.requestStatsFromFirebase();
    }
}
