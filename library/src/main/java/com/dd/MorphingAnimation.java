package com.dd;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.graphics.drawable.GradientDrawable;
import android.view.ViewGroup;
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

    private TextView mViewGroup;
    private GradientDrawable mDrawable;

    public MorphingAnimation(TextView viewGroup, GradientDrawable drawable) {
        mViewGroup = viewGroup;
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
        widthAnimation.setDuration(ANIMATION_DURATION);
        widthAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer value = (Integer) animation.getAnimatedValue();
                ViewGroup.LayoutParams params = mViewGroup.getLayoutParams();
                params.width = value;
                mViewGroup.setLayoutParams(params);
            }
        });

        ValueAnimator bgColorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), mFromColor, mToColor);
        bgColorAnimation.setDuration(ANIMATION_DURATION);
        bgColorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(final ValueAnimator animator) {
                mDrawable.setColor((Integer) animator.getAnimatedValue());
            }

        });

        final int strokeWidth = (int) mViewGroup.getContext().getResources().getDimension(R.dimen.stroke_width);
        ValueAnimator strokeColorAnimation =
                ValueAnimator.ofObject(new ArgbEvaluator(), mFromStrokeColor, mToStrokeColor);
        strokeColorAnimation.setDuration(ANIMATION_DURATION);
        strokeColorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(final ValueAnimator animator) {
                mDrawable.setStroke(strokeWidth, (Integer) animator.getAnimatedValue());
            }

        });

        ValueAnimator cornerAnimation = ValueAnimator.ofFloat(mFromCornerRadius, mToCornerRadius);
        cornerAnimation.setDuration(ANIMATION_DURATION);
        cornerAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Float value = (Float) animation.getAnimatedValue();
                mDrawable.setCornerRadius(value);
            }
        });

        AnimatorSet animatorSet = new AnimatorSet();
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