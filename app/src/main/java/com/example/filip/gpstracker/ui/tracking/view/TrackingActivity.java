package com.example.filip.gpstracker.ui.tracking.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.filip.gpstracker.App;
import com.example.filip.gpstracker.R;
import com.example.filip.gpstracker.constants.Constants;
import com.example.filip.gpstracker.ui.tracking.view.main.TrackingUserFragment;
import com.example.filip.gpstracker.ui.tracking.view.sessions.TrackingUserSessionsFragment;
import com.example.filip.gpstracker.ui.tracking.view.stats.TrackingUserStatsFragment;

/**
 * Created by Filip on 05/03/2016.
 */
public class TrackingActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private NavigationView navigationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking);
        initToolbarAndDrawer();
        if (savedInstanceState == null)
            addNewSessionFragment();
    }

    @Override
    protected void onStart() {
        super.onStart();
        initNavBar();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.getInstance().setCurrentUser(null);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case (android.R.id.home):
                mDrawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }

    private void initToolbarAndDrawer() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.tracking_activity_drawer_layout);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setTitle(R.string.start_a_new_session_fragment_title);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            setHomeButtonsAsNavBarOpening(actionBar);
    }

    private void initNavBar() {
        navigationView = (NavigationView) findViewById(R.id.tracking_activity_navigation_view);
        if (navigationView != null) {
            TextView usernameTextView = (TextView) navigationView.getHeaderView(0).findViewById(R.id.nav_drawer_header_username_text_view); //get the text view from the first header(only one header atm)
            usernameTextView.setText(getIntent().getStringExtra(Constants.USERNAME_KEY));
        }

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                handleItemSelectedClick(item.getItemId());
                mToolbar.setTitle(item.getTitle());
                mDrawerLayout.closeDrawers();
                return false;
            }
        });
    }

    private void setHomeButtonsAsNavBarOpening(@NonNull ActionBar actionBar) {
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    private void handleItemSelectedClick(int itemID) {
        switch (itemID) {
            case R.id.nav_drawer_new_session_item: {
                addNewSessionFragment();
                break;
            }
            case R.id.nav_drawer_previous_sessions_item: {
                addPreviousSessionsFragment();
                break;
            }
            case R.id.nav_drawer_stats_item: {
                addStatsFragment();
                break;
            }
        }
    }

    private void addNewSessionFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.tracking_activity_frame_layout, new TrackingUserFragment())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }

    private void addPreviousSessionsFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.tracking_activity_frame_layout, new TrackingUserSessionsFragment())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }

    private void addStatsFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.tracking_activity_frame_layout, new TrackingUserStatsFragment())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }
}
