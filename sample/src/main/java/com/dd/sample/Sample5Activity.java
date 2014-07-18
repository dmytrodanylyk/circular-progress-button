package com.dd.sample;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dd.CircularProgressButton;

public class Sample5Activity extends Activity implements TaskFragment.TaskCallbacks {

    private static final String KEY_CURRENT_PROGRESS = "current_progress";
    private static final String KEY_PERCENT_PROGRESS = "percent_progress";
    private static final String TAG_TASK_FRAGMENT = "task_fragment";

    private TaskFragment mTaskFragment;
    private TextView mPercent;
    private CircularProgressButton circularButton1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_sample_5);

        // Initialize views.
        mPercent = (TextView) findViewById(R.id.percent_progress);
        circularButton1 = (CircularProgressButton) findViewById(R.id.circularButton1);
        circularButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTaskFragment.isRunning()) {
                    mTaskFragment.cancel();
                } else {
                    mTaskFragment.start();
                }
            }
        });

        // Restore saved state.
        if (savedInstanceState != null) {
            circularButton1.setProgress(savedInstanceState.getInt(KEY_CURRENT_PROGRESS));
            mPercent.setText(savedInstanceState.getString(KEY_PERCENT_PROGRESS));
        }

        FragmentManager fm = getFragmentManager();
        mTaskFragment = (TaskFragment) fm.findFragmentByTag(TAG_TASK_FRAGMENT);

        // If the Fragment is non-null, then it is being retained
        // over a configuration change.
        if (mTaskFragment == null) {
            mTaskFragment = new TaskFragment();
            fm.beginTransaction().add(mTaskFragment, TAG_TASK_FRAGMENT).commit();
        }
    }

    public static void startThisActivity(Activity activity) {
        activity.startActivity(new Intent(activity, Sample5Activity.class));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_PERCENT_PROGRESS, mPercent.getText().toString());
    }

    @Override
    public void onPreExecute() {
        Toast.makeText(this, R.string.task_started_msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProgressUpdate(int percent) {
        circularButton1.setProgress(percent);
        mPercent.setText(percent + "%");
    }

    @Override
    public void onCancelled() {
        circularButton1.setProgress(0);
        mPercent.setText(getString(R.string.zero_percent));
        Toast.makeText(this, R.string.task_cancelled_msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPostExecute() {
        circularButton1.setProgress(100);
        mPercent.setText(getString(R.string.one_hundred_percent));
        Toast.makeText(this, R.string.task_complete_msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sample5, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_trigger_config_change:
                recreate();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}