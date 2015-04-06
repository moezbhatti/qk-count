package com.qklabs.counter.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.*;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
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
    private ImageButton mEdit;
    private ImageButton mReset;
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

        mEdit = (ImageButton) view.findViewById(R.id.edit);
        mEdit.setOnClickListener(this);
        mEdit.setOnLongClickListener(this);

        mReset = (ImageButton) view.findViewById(R.id.reset);
        mReset.setOnClickListener(this);
        mReset.setOnLongClickListener(this);

        mDirectionUp = (ImageButton) view.findViewById(R.id.direction_up);
        mDirectionUp.setOnClickListener(this);
        mDirectionUp.setOnLongClickListener(this);

        mDirectionDown = (ImageButton) view.findViewById(R.id.direction_down);
        mDirectionDown.setOnClickListener(this);
        mDirectionDown.setOnLongClickListener(this);

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

            case R.id.edit:
                showEditDialog();
                break;

            case R.id.reset:
                mCounter.setCount(0, true);
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
        }
    }

    private void showEditDialog() {
        final Dialog editDialog = new Dialog(getActivity());
        editDialog.setTitle(R.string.edit_dialog_title);
        editDialog.setContentView(R.layout.dialog_edit);

        final EditText edit = (EditText) editDialog.findViewById(R.id.edit);
        edit.setHint(Integer.toString(mCounter.getCount()));
        edit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    editDialog.dismiss();
                    return true;
                }

                return false;
            }
        });

        editDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                try {
                    mCounter.setCount(Integer.parseInt(edit.getText().toString()), true);
                } catch (NumberFormatException ignored) {
                    mCounter.setCount(0, true);
                }
            }
        });

        editDialog.show();

        // Give focus to the edit field automatically
        // Need to put this in a handler, because we can't give focus until the view is visible
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(edit, InputMethodManager.SHOW_IMPLICIT);
            }
        }, 50);
    }

    @Override
    public boolean onLongClick(View v) {

        switch (v.getId()) {
            case R.id.edit:
            case R.id.reset:
            case R.id.direction_up:
            case R.id.direction_down:
                mVibrator.vibrate(50); // This isn't necessarily haptic feedback, so we should ignore the setting
                Toast.makeText(mActivity, v.getContentDescription(), Toast.LENGTH_SHORT).show();
                return true;
        }

        return false;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_UP:
                if (mPrefs.getBoolean(PreferenceFragment.KEY_VOLUME_CONTROLS, true)) {
                    mCounter.increment();
                }
                return true;

            case KeyEvent.KEYCODE_VOLUME_DOWN:
                if (mPrefs.getBoolean(PreferenceFragment.KEY_VOLUME_CONTROLS, true)) {
                    mCounter.decrement();
                }
                return true;
        }

        return false;
    }
}
