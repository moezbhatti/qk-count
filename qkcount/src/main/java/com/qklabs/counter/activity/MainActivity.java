package com.qklabs.counter.activity;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import com.qklabs.counter.R;
import com.qklabs.counter.fragment.CounterFragment;
import com.qklabs.counter.fragment.PreferenceFragment;

/**
 * @author Moez Bhatti
 */
public class MainActivity extends ActionBarActivity {
    private final String TAG = "MainActivity";

    private CounterFragment mCounterFragment;
    private TextView mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mTitle = (TextView) toolbar.findViewById(R.id.title);

        FragmentManager fm = getFragmentManager();

        mCounterFragment = (CounterFragment) fm.findFragmentById(R.id.fragment_frame);
        if (mCounterFragment == null) {
            mCounterFragment = new CounterFragment();
        }
        fm.beginTransaction()
                .replace(R.id.fragment_frame, mCounterFragment)
                .show(mCounterFragment)
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
                    .show(preferenceFragment)
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

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        mTitle.setText(title);
    }
}
