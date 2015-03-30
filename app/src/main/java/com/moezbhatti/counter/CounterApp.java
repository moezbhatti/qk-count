package com.moezbhatti.counter;

import android.app.Application;
import android.preference.PreferenceManager;

/**
 * Created by moez.bhatti on 15-03-30.
 */
public class CounterApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
    }
}
