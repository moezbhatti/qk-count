package com.moezbhatti.counter.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.view.*;
import android.widget.ImageButton;
import com.moezbhatti.counter.R;
import com.moezbhatti.counter.view.CounterView;

/**
 * @author Moez Bhatti
 */
public class CounterFragment extends Fragment implements View.OnClickListener {

    private Activity mActivity;
    private Vibrator mVibrator;
    private SharedPreferences mPrefs;

    private View mRootView;
    private CounterView mCounter;
    private ImageButton mDirectionUp;
    private ImageButton mDirectionDown;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
        mActivity = getActivity();
        mVibrator = (Vibrator) mActivity.getSystemService(Context.VIBRATOR_SERVICE);
        mPrefs = PreferenceManager.getDefaultSharedPreferences(mActivity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.counter, container, false);

        mRootView = view.findViewById(R.id.root_view);
        mRootView.setOnClickListener(this);

        mCounter = (CounterView) view.findViewById(R.id.counter);

        mDirectionUp = (ImageButton) view.findViewById(R.id.direction_up);
        mDirectionUp.setOnClickListener(this);

        mDirectionDown = (ImageButton) view.findViewById(R.id.direction_down);
        mDirectionDown.setOnClickListener(this);

        if (mPrefs.getBoolean(PreferenceFragment.KEY_DIRECTIONS, false)) {
            mDirectionUp.setVisibility(View.VISIBLE);
            mDirectionDown.setVisibility(View.VISIBLE);
        } else {
            mDirectionUp.setVisibility(View.GONE);
            mDirectionDown.setVisibility(View.GONE);
            mCounter.setDirection(CounterView.Direction.UP);
        }

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.findItem(R.id.action_settings).setVisible(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.root_view:
                mCounter.handleTouch();
                if (mPrefs.getBoolean(PreferenceFragment.KEY_VIBRATION, true)) {
                    mVibrator.vibrate(50);
                }
                break;

            case R.id.direction_up:
                mCounter.setDirection(CounterView.Direction.UP);
                mDirectionUp.setImageResource(R.drawable.ic_arrow_circle);
                mDirectionDown.setImageResource(R.drawable.ic_arrow);
                break;

            case R.id.direction_down:
                mCounter.setDirection(CounterView.Direction.DOWN);
                mDirectionUp.setImageResource(R.drawable.ic_arrow);
                mDirectionDown.setImageResource(R.drawable.ic_arrow_circle);
                break;
        }
    }
}
