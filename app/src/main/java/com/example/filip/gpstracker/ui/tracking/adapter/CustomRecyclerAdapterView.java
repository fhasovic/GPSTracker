package com.example.filip.gpstracker.ui.tracking.adapter;

import com.example.filip.gpstracker.model.Session;

import java.util.ArrayList;

/**
 * Created by Filip on 10/03/2016.
 */
public interface CustomRecyclerAdapterView {
    void addAllItems(ArrayList<Session> sessionsList);

    void fillAdapterWithItems();

    void deleteItem(String sessionName);
}
