package com.dd.sample;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.dd.CircularProgressButton;

/**
 * Indeterminate Progress Sample
 */
public class Sample6Activity extends Activity {

    public static void startThisActivity(Activity activity) {
        activity.startActivity(new Intent(activity, Sample6Activity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_sample_6);

        ActionBar actionBar = getActionBar();
        if(actionBar != null) {
            actionBar.setTitle(R.string.IconsAndTextSample);
        }

        final CircularProgressButton circularButton1 = (CircularProgressButton) findViewById(R.id.circularButton1);
        circularButton1.setIndeterminateProgressMode(true);
        circularButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (circularButton1.getProgress() == 0) {
                    circularButton1.setProgress(50);
                } else if (circularButton1.getProgress() == 100) {
                    circularButton1.setProgress(0);
                } else {
                    circularButton1.setProgress(100);
                }
            }
        });

        final CircularProgressButton circularButton2 = (CircularProgressButton) findViewById(R.id.circularButton2);
        circularButton2.setIndeterminateProgressMode(true);
        circularButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (circularButton2.getProgress() == 0) {
                    circularButton2.setProgress(50);
                } else if (circularButton2.getProgress() == -1) {
                    circularButton2.setProgress(0);
                } else {
                    circularButton2.setProgress(-1);
                }
            }
        });
    }
}
