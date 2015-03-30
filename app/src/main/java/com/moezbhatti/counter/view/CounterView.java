package com.moezbhatti.counter.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.github.adnansm.timelytextview.TimelyView;
import com.moezbhatti.counter.Utils;

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

        LinearLayout.LayoutParams digitLayoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 500, 1f);
        int padding = Utils.dpToPx(context, 8);

        mTimelyView = new TimelyView(context);
        mTimelyView.setLayoutParams(digitLayoutParams);
        mTimelyView.setPadding(padding, padding, padding, padding);
        mTimelyView.animate(0).start();

        addView(mTimelyView);
    }

    public void increment() {
        int start = mCount;
        mCount++;
        if (mCount > 9) {
            mCount = 0;
        }

        mTimelyView.animate(start, mCount).start();
    }

    public int getCount() {
        return mCount;
    }
}