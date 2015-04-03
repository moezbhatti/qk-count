package com.qklabs.counter;

import android.app.Application;
import android.preference.PreferenceManager;

/**
 * @author Moez Bhatti
 */
public class CounterApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
    }
}
