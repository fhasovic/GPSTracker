package com.example.filip.gpstracker.api;

/**
 * Created by Filip on 03/03/2016.
 */
public interface ResponseListener<T> {
    void onSuccess(T callback);

    void onFailure(Throwable t);
}
