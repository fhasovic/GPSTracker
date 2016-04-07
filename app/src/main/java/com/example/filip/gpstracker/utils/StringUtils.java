package com.example.filip.gpstracker.utils;

import com.example.filip.gpstracker.BuildConfig;

/**
 * Created by Filip on 05/04/2016.
 */
public class StringUtils {

    public static void logError(Throwable throwable) {
        if (BuildConfig.DEBUG) {
            if (throwable != null)
                logError(throwable);
        }
    }
}
