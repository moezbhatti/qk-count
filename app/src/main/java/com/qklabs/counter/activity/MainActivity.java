package com.qklabs.counter.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import com.qklabs.counter.fragment.CounterFragment;
import com.qklabs.counter.fragment.PreferenceFragment;
import com.qklabs.counter.R;

/**
 * @author Moez Bhatti
 */
public class MainActivity extends ActionBarActivity {
    private final String TAG = "MainActivity";

    private CounterFragment mCounterFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mCounterFragment = new CounterFragment();

        getFragmentManager().beginTransaction()
                .add(R.id.fragment_frame, mCounterFragment)
                /*.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_left,
                        R.animator.slide_in_right, R.animator.slide_out_left)
                .show(mCounterFragment)*/
                .commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            PreferenceFragment preferenceFragment = new PreferenceFragment();
            getFragmentManager().beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.fragment_frame, preferenceFragment)
                    /*.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_left,
                            R.animator.slide_in_left, R.animator.slide_out_left)
                    .show(preferenceFragment)*/
                    .commit();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() != 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return mCounterFragment.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }
}
