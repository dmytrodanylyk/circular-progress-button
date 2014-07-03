package com.dd.sample;

import com.dd.CircularProgressButton;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_main);

        final CircularProgressButton btnWithIcons1 = (CircularProgressButton) findViewById(R.id.btnWithIcons1);
        btnWithIcons1.setIndeterminateProgressMode(true);
        btnWithIcons1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnWithIcons1.getProgress() == 0) {
                    btnWithIcons1.setProgress(50);
                } else if (btnWithIcons1.getProgress() == 100) {
                    btnWithIcons1.setProgress(0);
                } else {
                    btnWithIcons1.setProgress(100);
                }
            }
        });

        final CircularProgressButton btnWithIcons2 = (CircularProgressButton) findViewById(R.id.btnWithIcons2);
        btnWithIcons2.setIndeterminateProgressMode(true);
        btnWithIcons2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnWithIcons2.getProgress() == 0) {
                    btnWithIcons2.setProgress(50);
                } else if (btnWithIcons2.getProgress() == -1) {
                    btnWithIcons2.setProgress(0);
                } else {
                    btnWithIcons2.setProgress(-1);
                }
            }
        });

        final CircularProgressButton btnWithText1 = (CircularProgressButton) findViewById(R.id.btnWithText1);
        btnWithText1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnWithText1.getProgress() == 0) {
                    simulateSuccessProgress(btnWithText1);
                } else {
                    btnWithText1.setProgress(0);
                }
            }
        });

        final CircularProgressButton btnWithText2= (CircularProgressButton) findViewById(R.id.btnWithText2);
        btnWithText2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnWithText2.getProgress() == 0) {
                    simulateErrorProgress(btnWithText2);
                } else {
                    btnWithText2.setProgress(0);
                }
            }
        });

        final CircularProgressButton btnWithPadding1 = (CircularProgressButton) findViewById(R.id.btnWithPadding1);
        btnWithPadding1.setIndeterminateProgressMode(true);
        btnWithPadding1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnWithPadding1.getProgress() == 0) {
                    btnWithPadding1.setProgress(50);
                } else if (btnWithPadding1.getProgress() == 100) {
                    btnWithPadding1.setProgress(0);
                } else {
                    btnWithPadding1.setProgress(100);
                }
            }
        });

        final CircularProgressButton btnWithPadding2= (CircularProgressButton) findViewById(R.id.btnWithPadding2);
        btnWithPadding2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnWithPadding2.getProgress() == 0) {
                    simulateSuccessProgress(btnWithPadding2);
                } else {
                    btnWithPadding2.setProgress(0);
                }
            }
        });
    }

    private void simulateSuccessProgress(final CircularProgressButton button) {
        ValueAnimator widthAnimation = ValueAnimator.ofInt(1, 100);
        widthAnimation.setDuration(1500);
        widthAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        widthAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer value = (Integer) animation.getAnimatedValue();
                button.setProgress(value);
            }
        });
        widthAnimation.start();
    }

    private void simulateErrorProgress(final CircularProgressButton button) {
        ValueAnimator widthAnimation = ValueAnimator.ofInt(1, 99);
        widthAnimation.setDuration(1500);
        widthAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        widthAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer value = (Integer) animation.getAnimatedValue();
                button.setProgress(value);
                if (value == 99) {
                    button.setProgress(-1);
                }
            }
        });
        widthAnimation.start();
    }
}
