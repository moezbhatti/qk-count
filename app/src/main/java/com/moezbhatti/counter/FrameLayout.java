package com.moezbhatti.counter;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Custom FrameLayout with support for the xFraction property, used when animating a fragment
 * http://stackoverflow.com/a/4936159
 */
public class FrameLayout extends android.widget.FrameLayout {

    public FrameLayout(Context context) {
        super(context);
    }

    public FrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public float getXFraction() {
        return getX() / getWidth(); // TODO: guard divide-by-zero
    }

    public void setXFraction(float xFraction) {
        // TODO: cache width
        final int width = getWidth();
        setX((width > 0) ? (xFraction * width) : -9999);
    }
}
