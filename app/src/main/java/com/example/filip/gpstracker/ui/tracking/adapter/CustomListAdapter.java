package com.example.filip.gpstracker.ui.tracking.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.filip.gpstracker.R;
import com.example.filip.gpstracker.pojo.Stats;

import java.util.ArrayList;

/**
 * Created by Filip on 17/03/2016.
 */
public class CustomListAdapter extends BaseAdapter {
    private final ArrayList<Stats> mItemList = new ArrayList<>();

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
        final TextView mSessionNameTextView;
        final TextView mSessionTimeElapsedTextView;
        final TextView mSessionDistanceTraversedTextView;
        Stats stats = mItemList.get(position);

        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.stats_fragment_session_list_item, parent, false);

        mSessionNameTextView = (TextView) convertView.findViewById(R.id.stats_fragment_session_name_text_view);
        mSessionTimeElapsedTextView = (TextView) convertView.findViewById(R.id.stats_fragment_session_time_text_view);
        mSessionDistanceTraversedTextView = (TextView) convertView.findViewById(R.id.stats_fragment_session_distance_text_view);

        String sessionName = parent.getContext().getString(R.string.stats_session_name_text_view_message, stats.getSessionName());
        String timeElapsed = parent.getContext().getString(R.string.stats_time_elapsed_text_view_message, stats.getTimeElapsed());
        String distanceTraversed = parent.getContext().getString(R.string.stats_distance_traversed_text_view_message, stats.getDistanceTraversed());

        mSessionNameTextView.setText(sessionName);
        mSessionTimeElapsedTextView.setText(timeElapsed);
        mSessionDistanceTraversedTextView.setText(distanceTraversed);
        return convertView;
    }

    public void fillAdapter(ArrayList<Stats> dataSource) {
        mItemList.clear();
        mItemList.addAll(dataSource);
        notifyDataSetChanged();
    }
}
