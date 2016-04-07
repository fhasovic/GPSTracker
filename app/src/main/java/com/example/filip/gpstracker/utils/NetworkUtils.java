package com.example.filip.gpstracker.utils;

import android.content.Context;
import android.provider.Settings;

/**
 * Created by Filip on 05/04/2016.
 */
public class NetworkUtils {
    public static boolean checkIfLocationServiceIsActivated(Context context) {
        try {
            if (Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE) == 0) {
                return false;
            }
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        return true;
    }
}
