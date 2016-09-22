package com.utshelps.utshelpsapp;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by peerliss on 21/09/2016.
 */

public class FireApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
