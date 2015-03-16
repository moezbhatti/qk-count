package com.moezbhatti.counter;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


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
                .replace(R.id.fragment_frame, mCounterFragment)
                /*.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_left,
                        R.animator.slide_in_right, R.animator.slide_out_left)
                .show(mCounterFragment)*/
                .addToBackStack("counter")
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
                    .replace(R.id.fragment_frame, preferenceFragment)
                    /*.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_left,
                            R.animator.slide_in_left, R.animator.slide_out_left)
                    .show(preferenceFragment)*/
                    .addToBackStack("settings")
                    .commit();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() == 1) {
            finish();
        } else {
            getFragmentManager().popBackStack();
        }
    }
}
