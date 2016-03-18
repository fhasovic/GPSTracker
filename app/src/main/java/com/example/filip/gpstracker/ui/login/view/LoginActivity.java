package com.example.filip.gpstracker.ui.login.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.filip.gpstracker.R;
import com.firebase.client.Firebase;

/**
 * Created by Filip on 08/03/2016.
 */
public class LoginActivity extends AppCompatActivity {
    private Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Firebase.setAndroidContext(this);
        initToolbar();
    }

    @Override
    protected void onStart() {
        super.onStart();
        initFragment();
    }

    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(R.string.login_activity_toolbar_title);
    }

    private void initFragment() {
        if (getSupportFragmentManager().findFragmentById(R.id.login_activity_frame_layout) == null)
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.login_activity_frame_layout, new LoginFragment())
                    .commit();
    }
}
