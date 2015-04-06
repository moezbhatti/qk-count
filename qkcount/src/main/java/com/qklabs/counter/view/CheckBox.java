package com.qklabs.counter.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import com.qklabs.counter.R;

/**
 * Custom CheckBox, made to support animated boxes on versions prior to lollipop
 *
 * Copied from QKSMS
 *
 * @author Moez Bhatti
 */
public class CheckBox extends android.widget.CheckBox {
    public static final String TAG = "CheckBox";

    private static final int ALPHA_ENABLED = 255;
    private static final int ALPHA_DISABLED = 128;

    private static final int ANIMATION_FRAME_START = 0;
    private static final int ANIMATION_FRAME_COMPLETE = 15;

    private static final int ANIMATION_DURATION = 200; // ms

    private Drawable mButtonDrawable;

    public CheckBox(Context context) {
        this(context, null);
    }

    public CheckBox(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.checkboxStyle);
    }

    public CheckBox(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setDrawable(ANIMATION_FRAME_COMPLETE);

        // This disables an annoying double ripple effect on Lollipop+ android versions.
        // But, we can't just always disable the background, because that makes the CheckBox
        // invisible for older Android versions (i.e. 4.1.2, perhaps older)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setBackground(null);
        }
    }

    /**
     * Sets the button drawable, updating the color of it according to the theme color.
     *
     * @param drawable
     */
    @Override
    public void setButtonDrawable(Drawable drawable) {
        mButtonDrawable = drawable;
        mButtonDrawable.setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.MULTIPLY);
        super.setButtonDrawable(drawable);
    }

    @Override
    public void setChecked(boolean checked) {
        boolean animate = checked != isChecked();
        super.setChecked(checked);

        if (animate) {
            ObjectAnimator checkBoxAnimator = ObjectAnimator.ofInt(
                    this, "drawable", ANIMATION_FRAME_START, ANIMATION_FRAME_COMPLETE
            );
            checkBoxAnimator.setDuration(ANIMATION_DURATION);
            checkBoxAnimator.start();
        }
    }

    private void setDrawable(int number) {
        Resources res = getContext().getResources();
        // i.e. btn_check_to_off_mtrl_003, or btn_check_to_on_mtrl_013.
        // the "%03d" part just means "insert this number, padding it so that it's always three
        // digits"
        String name = String.format("btn_check_to_%s_mtrl_%03d", isChecked() ? "on" : "off", number);
        int id = res.getIdentifier(name, "drawable", getContext().getPackageName());
        setButtonDrawable(res.getDrawable(id));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mButtonDrawable.mutate().setAlpha(isEnabled() ? ALPHA_ENABLED : ALPHA_DISABLED);
        super.onDraw(canvas);
    }
}
