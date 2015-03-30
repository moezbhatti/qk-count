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
import android.widget.TextView;
import android.widget.Toast;
import com.moezbhatti.counter.R;

public class CounterFragment extends Fragment implements View.OnClickListener {

    private Activity mActivity;
    private Vibrator mVibrator;
    private SharedPreferences mPrefs;

    private int mCount = 0;
    private int mIncrement = 1;

    private View mRootView;
    private TextView mCounter;
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

        mCounter = (TextView) view.findViewById(R.id.counter);
        mCounter.setText(Integer.toString(mCount));

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
            mIncrement = 1;
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
                if (mCount + mIncrement >= 0) {
                    mCount += mIncrement;
                    mCounter.setText(Integer.toString(mCount));
                } else {
                    Toast.makeText(mActivity, R.string.number_bottom_warning, Toast.LENGTH_SHORT).show();
                }

                if (mPrefs.getBoolean(PreferenceFragment.KEY_VIBRATION, true)) {
                    mVibrator.vibrate(50);
                }
                break;

            case R.id.direction_up:
                mIncrement = 1;
                mDirectionUp.setImageResource(R.drawable.ic_arrow_circle);
                mDirectionDown.setImageResource(R.drawable.ic_arrow);
                break;

            case R.id.direction_down:
                mIncrement = -1;
                mDirectionUp.setImageResource(R.drawable.ic_arrow);
                mDirectionDown.setImageResource(R.drawable.ic_arrow_circle);
                break;
        }
    }
}
