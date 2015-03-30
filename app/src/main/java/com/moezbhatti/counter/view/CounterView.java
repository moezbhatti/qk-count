package com.moezbhatti.counter.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import com.github.adnansm.timelytextview.TimelyView;
import com.moezbhatti.counter.R;

/**
 * @author Moez Bhatti
 */
public class CounterView extends LinearLayout {
    private final String TAG = "CounterView";

    private TimelyView mTimelyView;

    private int mCount = 0;

    public CounterView(Context context) {
        super(context);

        if (!isInEditMode()) {
            init(context);
        }
    }

    public CounterView(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (!isInEditMode()) {
            init(context);
        }
    }

    private void init(Context context) {
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER);
        addDigit();
    }

    public void increment() {
        int start = mCount;
        mCount++;
        if (mCount > 9) {
            mCount = 0;
        }

        mTimelyView.animate(start, mCount).start();
    }

    private void addDigit() {
        mTimelyView = (TimelyView) inflate(getContext(), R.layout.digit, null);
        mTimelyView.animate(0).setDuration(0).start();

        addView(mTimelyView);
    }

    public int getCount() {
        return mCount;
    }
}