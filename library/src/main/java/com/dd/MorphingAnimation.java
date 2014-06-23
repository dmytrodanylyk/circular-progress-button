package com.dd;

import android.animation.*;
import android.graphics.drawable.GradientDrawable;
import android.widget.TextView;
import com.dd.circular.progress.button.R;

class MorphingAnimation {

    private static final int ANIMATION_DURATION = 400;

    private OnAnimationEndListener mListener;

    private int mFromWidth;
    private int mToWidth;

    private int mFromColor;
    private int mToColor;

    private int mFromStrokeColor;
    private int mToStrokeColor;

    private float mFromCornerRadius;
    private float mToCornerRadius;

    private TextView mView;
    private GradientDrawable mDrawable;

    public MorphingAnimation(TextView viewGroup, GradientDrawable drawable) {
        mView = viewGroup;
        mDrawable = drawable;
    }

    public void setListener(OnAnimationEndListener listener) {
        mListener = listener;
    }

    public void setFromWidth(int fromWidth) {
        mFromWidth = fromWidth;
    }

    public void setToWidth(int toWidth) {
        mToWidth = toWidth;
    }

    public void setFromColor(int fromColor) {
        mFromColor = fromColor;
    }

    public void setToColor(int toColor) {
        mToColor = toColor;
    }

    public void setFromStrokeColor(int fromStrokeColor) {
        mFromStrokeColor = fromStrokeColor;
    }

    public void setToStrokeColor(int toStrokeColor) {
        mToStrokeColor = toStrokeColor;
    }

    public void setFromCornerRadius(float fromCornerRadius) {
        mFromCornerRadius = fromCornerRadius;
    }

    public void setToCornerRadius(float toCornerRadius) {
        mToCornerRadius = toCornerRadius;
    }

    public void start() {
        ValueAnimator widthAnimation = ValueAnimator.ofInt(mFromWidth, mToWidth);
        widthAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer value = (Integer) animation.getAnimatedValue();
                int leftOffset;
                int rightOffset;

                if(mFromWidth > mToWidth) {
                    leftOffset = (mFromWidth - value) / 2;
                    rightOffset = mFromWidth - leftOffset;
                } else {
                    leftOffset = (mToWidth - value) / 2;
                    rightOffset = mToWidth - leftOffset;
                }

                mDrawable.setBounds(leftOffset, 0, rightOffset, mView.getHeight());
            }
        });

        ObjectAnimator bgColorAnimation = ObjectAnimator.ofInt(mDrawable, "color", mFromColor, mToColor);
        bgColorAnimation.setEvaluator(new ArgbEvaluator());


        final int strokeWidth = (int) mView.getContext().getResources().getDimension(R.dimen.stroke_width);
        ValueAnimator strokeColorAnimation =
                ValueAnimator.ofObject(new ArgbEvaluator(), mFromStrokeColor, mToStrokeColor);
        strokeColorAnimation.setEvaluator(new ArgbEvaluator());
        strokeColorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(final ValueAnimator animator) {
                mDrawable.setStroke(strokeWidth, (Integer) animator.getAnimatedValue());
            }
        });

        ObjectAnimator cornerAnimation = ObjectAnimator.ofFloat(mDrawable, "cornerRadius", mFromCornerRadius, mToCornerRadius);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(ANIMATION_DURATION);
        animatorSet.playTogether(widthAnimation, bgColorAnimation, strokeColorAnimation, cornerAnimation);
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (mListener != null) {
                    mListener.onAnimationEnd();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animatorSet.start();
    }
}