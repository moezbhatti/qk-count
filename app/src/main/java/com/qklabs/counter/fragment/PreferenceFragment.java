package com.qklabs.counter.fragment;

import android.animation.Animator;
import android.os.Bundle;
import android.view.*;
import android.view.animation.DecelerateInterpolator;
import com.qklabs.counter.R;

/**
 * @author Moez Bhatti
 */
public class PreferenceFragment extends android.preference.PreferenceFragment {

    public static final String KEY_DIRECTIONS = "directions";
    public static final String KEY_VOLUME_CONTROLS = "volume_controls";
    public static final String KEY_VIBRATION = "vibration";
    public static final String KEY_COUNT = "count";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
        addPreferencesFromResource(R.xml.preferences);
    }

    @Override
    public void onResume() {
        super.onResume();

        getActivity().setTitle(R.string.action_settings);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.findItem(R.id.action_settings).setVisible(false);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);

        // Circular reveal for the fragment
        if (rootView != null) {
            rootView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                    v.removeOnLayoutChangeListener(this);

                    int radius = (int) Math.hypot(right, bottom);

                    Animator reveal = ViewAnimationUtils.createCircularReveal(v, right, top, 0, radius);
                    reveal.setInterpolator(new DecelerateInterpolator(2f));
                    reveal.setDuration(1000);
                    reveal.start();
                }
            });
        }

        return rootView;
    }
}
