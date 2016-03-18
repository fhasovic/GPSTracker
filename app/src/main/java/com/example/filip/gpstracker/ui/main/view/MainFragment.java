package com.example.filip.gpstracker.ui.main.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.filip.gpstracker.R;
import com.example.filip.gpstracker.ui.register.view.RegisterActivity;
import com.example.filip.gpstracker.ui.login.view.LoginActivity;

/**
 * Created by Filip on 05/03/2016.
 */
public class MainFragment extends Fragment implements View.OnClickListener {
    private Button mLoginButton;
    private Button mRegisterButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI(view);
    }

    private void initUI(View view) {
        mLoginButton = (Button) view.findViewById(R.id.main_fragment_login_button);
        mRegisterButton = (Button) view.findViewById(R.id.main_fragment_register_button);
        mLoginButton.setOnClickListener(this);
        mRegisterButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == mLoginButton){
            startActivity(new Intent(getActivity(), LoginActivity.class));
        }
        else if(v == mRegisterButton){
            startActivity(new Intent(getActivity(), RegisterActivity.class));
        }
    }
}
