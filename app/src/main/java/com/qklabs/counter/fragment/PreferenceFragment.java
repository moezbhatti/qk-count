package com.qklabs.counter.fragment;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import com.qklabs.counter.R;

/**
 * @author Moez Bhatti
 */
public class PreferenceFragment extends android.preference.PreferenceFragment {

    public static final String KEY_DIRECTIONS = "directions";
    public static final String KEY_VOLUME_CONTROLS = "volume_controls";
    public static final String KEY_VIBRATION = "vibration";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
        addPreferencesFromResource(R.xml.preferences);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.findItem(R.id.action_settings).setVisible(false);
    }
}
