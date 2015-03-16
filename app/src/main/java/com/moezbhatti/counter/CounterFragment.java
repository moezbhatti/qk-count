package com.moezbhatti.counter;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CounterFragment extends Fragment implements View.OnClickListener {

    private Activity mActivity;
    private View mRootView;
    private TextView mCounter;
    private int mCount = 0;
    private Vibrator mVibrator;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivity = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.counter, container, false);

        mRootView = view.findViewById(R.id.root_view);
        mRootView.setOnClickListener(this);

        mCounter = (TextView) view.findViewById(R.id.counter);
        mCounter.setText(Integer.toString(mCount));

        mVibrator = (Vibrator) mActivity.getSystemService(Context.VIBRATOR_SERVICE);

        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.root_view) {
            mCount++;
            mCounter.setText(Integer.toString(mCount));
            mVibrator.vibrate(50);
        }
    }
}
