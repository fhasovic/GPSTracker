package com.example.filip.gpstracker.ui.register.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.filip.gpstracker.R;
import com.firebase.client.Firebase;


/**
 * Created by Filip on 08/03/2016.
 */
public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Firebase.setAndroidContext(this);
        if (savedInstanceState == null)
            initFragment();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        FragmentManager manager = getSupportFragmentManager();
        if (manager.getBackStackEntryCount() != 0) manager.popBackStack();
    }

    private void initFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.register_activity_frame_layout, new UsernameFragment())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }
}
