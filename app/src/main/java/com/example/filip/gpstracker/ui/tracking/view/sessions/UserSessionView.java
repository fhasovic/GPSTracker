package com.example.filip.gpstracker.ui.tracking.view.sessions;

import com.example.filip.gpstracker.pojo.Session;

import java.util.ArrayList;

/**
 * Created by Filip on 05/04/2016.
 */
public interface UserSessionView {
    void requestSessionsForAdapter();

    void fillAdapterWithItems(ArrayList<Session> sessionArrayList);
}
