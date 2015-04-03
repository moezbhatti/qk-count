package com.qklabs.counter.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.view.*;
import android.widget.ImageButton;
import android.widget.Toast;
import com.qklabs.counter.R;
import com.qklabs.counter.view.CounterView;

/**
 * @author Moez Bhatti
 */
public class CounterFragment extends Fragment implements View.OnClickListener, View.OnLongClickListener {
    private final String TAG = "CounterFragment";

    private Activity mActivity;
    private Vibrator mVibrator;
    private SharedPreferences mPrefs;

    private View mRootView;
    private CounterView mCounter;
    private ImageButton mDirectionUp;
    private ImageButton mDirectionDown;
    private ImageButton mReset;

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
        mDirectionUp.setOnLongClickListener(this);

        mDirectionDown = (ImageButton) view.findViewById(R.id.direction_down);
        mDirectionDown.setOnClickListener(this);
        mDirectionDown.setOnLongClickListener(this);

        mReset = (ImageButton) view.findViewById(R.id.reset);
        mReset.setOnClickListener(this);
        mReset.setOnLongClickListener(this);

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
                if (mPrefs.getBoolean(PreferenceFragment.KEY_VIBRATION, true)) {
                    mVibrator.vibrate(50);
                }
                break;

            case R.id.direction_down:
                mCounter.setDirection(CounterView.Direction.DOWN);
                mDirectionUp.setImageResource(R.drawable.ic_arrow);
                mDirectionDown.setImageResource(R.drawable.ic_arrow_circle);
                if (mPrefs.getBoolean(PreferenceFragment.KEY_VIBRATION, true)) {
                    mVibrator.vibrate(50);
                }
                break;

            case R.id.reset:
                mCounter.setCount(0, true);
                if (mPrefs.getBoolean(PreferenceFragment.KEY_VIBRATION, true)) {
                    mVibrator.vibrate(50);
                }
                break;
        }
    }

    @Override
    public boolean onLongClick(View v) {

        switch (v.getId()) {

            case R.id.direction_up:
            case R.id.direction_down:
            case R.id.reset:
                mVibrator.vibrate(50); // This isn't necessarily haptic feedback, so we should ignore the setting
                Toast.makeText(mActivity, v.getContentDescription(), Toast.LENGTH_SHORT).show();
                return true;
        }

        return false;
    }
}
