package com.moezbhatti.counter.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.github.adnansm.timelytextview.TimelyView;
import com.moezbhatti.counter.R;

/**
 * @author Moez Bhatti
 */
public class CounterView extends LinearLayout {
    private final String TAG = "CounterView";

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
        int[] digitsStart = getDigitsFromNumber(mCount);
        mCount++;
        int[] digits = getDigitsFromNumber(mCount);

        if (digits.length > digitsStart.length) {
            addDigit();
        }

        for (int i = 0; i < digits.length; i++) {
            int from;
            int to;

            if (digits.length > digitsStart.length) {
                if (i == 0) {
                    from = 0;
                    to = digits[i];
                } else {
                    from = digitsStart[i - 1];
                    to = digits[i];
                }
            } else {
                from = digitsStart[i];
                to = digits[i];
            }

            ((TimelyView) getChildAt(i)).animate(from, to).start();
        }
    }

    public void decrement() {
        int[] digitsStart = getDigitsFromNumber(mCount);
        mCount--;
        int[] digits = getDigitsFromNumber(mCount);

        if (digits.length < digitsStart.length) {
            removeViewAt(digits.length);
        }

        for (int i = 0; i < digits.length; i++) {
            int from = digitsStart[i];
            int to = digits[i];
    
            ((TimelyView) getChildAt(i)).animate(from, to).start();
        }
    }

    private int[] getDigitsFromNumber(int count) {
        String s = Integer.toString(count);

        int[] digits = new int[s.length()];
        for (int i = 0; i < digits.length; i++) {
            digits[i] = Integer.parseInt(Character.toString(s.charAt(i)));
        }

        return digits;
    }

    private void addDigit() {
        LayoutParams lp = new LayoutParams(192, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);

        TimelyView timelyView = (TimelyView) inflate(getContext(), R.layout.digit, null);
        timelyView.animate(0).setDuration(0).start();
        timelyView.setLayoutParams(lp);

        addView(timelyView);
    }

    public int getCount() {
        return mCount;
    }
}