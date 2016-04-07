package com.example.filip.gpstracker.utils;

import android.location.Location;

import com.example.filip.gpstracker.pojo.LocationWrapper;

/**
 * Created by Filip on 05/04/2016.
 */
public class LocationUtils {
    public static Location createLocationFromLocationWrapper(LocationWrapper locationWrapper) {
        Location location = new Location(""); //empty provider
        location.setLongitude(locationWrapper.getLongitude());
        location.setLatitude(locationWrapper.getLatitude());
        return location;
    }
}
