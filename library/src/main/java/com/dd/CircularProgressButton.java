package com.dd;

import com.dd.circular.progress.button.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.Button;

public class CircularProgressButton extends Button {

    public static final int IDLE_STATE_PROGRESS = 0;
    public static final int ERROR_STATE_PROGRESS = -1;

    private StrokeGradientDrawable background;
    private CircularAnimatedDrawable mAnimatedDrawable;
    private CircularProgressDrawable mProgressDrawable;

    private State mState;
    private String mIdleText;
    private String mCompleteText;
    private String mErrorText;

    private int mColorIdle;
    private int mColorError;
    private int mColorProgress;
    private int mColorComplete;
    private int mColorIndicator;
    private int mColorIndicatorBackground;
    private int mIconComplete;
    private int mIconError;
    private int mStrokeWidth;
    private int mPaddingProgress;
    private float mCornerRadius;
    private boolean mIndeterminateProgressMode;

    private enum State {
        PROGRESS, IDLE, COMPLETE, ERROR
    }

    private int mMaxProgress;
    private int mProgress;

    private boolean mMorphingInProgress;

    public CircularProgressButton(Context context) {
        super(context);
        init(context, null);
    }

    public CircularProgressButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CircularProgressButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attributeSet) {
        mStrokeWidth = (int) getContext().getResources().getDimension(R.dimen.stroke_width);

        initAttributes(context, attributeSet);

        mMaxProgress = 100;
        mState = State.IDLE;

        setText(mIdleText);

        GradientDrawable gradientDrawable =
                (GradientDrawable) context.getResources().getDrawable(R.drawable.background).mutate();
        gradientDrawable.setColor(mColorIdle);
        gradientDrawable.setCornerRadius(mCornerRadius);
        background = new StrokeGradientDrawable(gradientDrawable);
        background.setStrokeColor(mColorIdle);
        background.setStrokeWidth(mStrokeWidth);
        setBackgroundCompat(gradientDrawable);
    }

    private void initAttributes(Context context, AttributeSet attributeSet) {
        TypedArray attr = getTypedArray(context, attributeSet, R.styleable.CircularProgressButton);

        if (attr == null) {
            return;
        }

        try {
            mIdleText = attr.getString(R.styleable.CircularProgressButton_textIdle);
            mCompleteText = attr.getString(R.styleable.CircularProgressButton_textComplete);
            mErrorText = attr.getString(R.styleable.CircularProgressButton_textError);

            mIconComplete = attr.getResourceId(R.styleable.CircularProgressButton_iconComplete, 0);
            mIconError = attr.getResourceId(R.styleable.CircularProgressButton_iconError, 0);
            mCornerRadius = attr.getDimension(R.styleable.CircularProgressButton_cornerRadius, 0);
            mPaddingProgress = attr.getDimensionPixelSize(R.styleable.CircularProgressButton_paddingProgress, 0);

            int blue = getColor(R.color.blue);
            int red = getColor(R.color.red);
            int green = getColor(R.color.green);
            int white = getColor(R.color.white);
            int grey = getColor(R.color.grey);

            mColorIdle = attr.getColor(R.styleable.CircularProgressButton_colorIdle, blue);
            mColorError = attr.getColor(R.styleable.CircularProgressButton_colorError, red);
            mColorComplete = attr.getColor(R.styleable.CircularProgressButton_colorComplete, green);
            mColorProgress = attr.getColor(R.styleable.CircularProgressButton_colorProgress, white);
            mColorIndicator = attr.getColor(R.styleable.CircularProgressButton_colorIndicator, blue);
            mColorIndicatorBackground =
                    attr.getColor(R.styleable.CircularProgressButton_colorIndicatorBackground, grey);
        } finally {
            attr.recycle();
        }
    }

    protected int getColor(int id) {
        return getResources().getColor(id);
    }

    protected TypedArray getTypedArray(Context context, AttributeSet attributeSet, int[] attr) {
        return context.obtainStyledAttributes(attributeSet, attr, 0, 0);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mProgress > 0 && mState == State.PROGRESS && !mMorphingInProgress) {
            if (mIndeterminateProgressMode) {
                drawIndeterminateProgress(canvas);
            } else {
                drawProgress(canvas);
            }
        }
    }

    private void drawIndeterminateProgress(Canvas canvas) {
        if (mAnimatedDrawable == null) {
            int offset = (getWidth() - getHeight()) / 2;
            mAnimatedDrawable = new CircularAnimatedDrawable(mColorIndicator, mStrokeWidth);
            mAnimatedDrawable.setBounds(offset + mPaddingProgress, mPaddingProgress, getWidth() - offset - mPaddingProgress, getHeight() - mPaddingProgress);
            mAnimatedDrawable.setCallback(this);
            mAnimatedDrawable.start();
        } else {
            mAnimatedDrawable.draw(canvas);
        }
    }

    private void drawProgress(Canvas canvas) {
        if (mProgressDrawable == null) {
            int offset = (getWidth() - getHeight()) / 2;
            mProgressDrawable = new CircularProgressDrawable(getHeight() - mPaddingProgress * 2, mStrokeWidth, mColorIndicator);
            mProgressDrawable.setBounds(offset + mPaddingProgress, mPaddingProgress, offset + mPaddingProgress, mPaddingProgress);
        }
        float sweepAngle = (360f / mMaxProgress) * mProgress;
        mProgressDrawable.setSweepAngle(sweepAngle);
        mProgressDrawable.draw(canvas);
    }

    public boolean isIndeterminateProgressMode() {
        return mIndeterminateProgressMode;
    }

    public void setIndeterminateProgressMode(boolean indeterminateProgressMode) {
        this.mIndeterminateProgressMode = indeterminateProgressMode;
    }

    @Override
    protected boolean verifyDrawable(Drawable who) {
        return who == mAnimatedDrawable || super.verifyDrawable(who);
    }

    private MorphingAnimation createMorphing() {
        mMorphingInProgress = true;

        MorphingAnimation animation = new MorphingAnimation(this, background);
        animation.setFromCornerRadius(mCornerRadius);
        animation.setToCornerRadius(mCornerRadius);

        animation.setFromWidth(getWidth());
        animation.setToWidth(getWidth());
        return animation;
    }

    private MorphingAnimation createProgressMorphing(float fromCorner, float toCorner, int fromWidth, int toWidth) {
        mMorphingInProgress = true;

        MorphingAnimation animation = new MorphingAnimation(this, background);
        animation.setFromCornerRadius(fromCorner);
        animation.setToCornerRadius(toCorner);

        animation.setPadding(mPaddingProgress);

        animation.setFromWidth(fromWidth);
        animation.setToWidth(toWidth);
        return animation;
    }

    private void morphToProgress() {
        setWidth(getWidth());
        setText(null);

        MorphingAnimation animation = createProgressMorphing(mCornerRadius, getHeight(), getWidth(), getHeight());

        animation.setFromColor(mColorIdle);
        animation.setToColor(mColorProgress);

        animation.setFromStrokeColor(mColorIdle);
        animation.setToStrokeColor(mColorIndicatorBackground);

        animation.setListener(mProgressStateListener);

        animation.start();
    }

    private OnAnimationEndListener mProgressStateListener = new OnAnimationEndListener() {
        @Override
        public void onAnimationEnd() {
            mMorphingInProgress = false;
            mState = State.PROGRESS;
        }
    };

    private void morphProgressToComplete() {
        MorphingAnimation animation = createProgressMorphing(getHeight(), mCornerRadius, getHeight(), getWidth());

        animation.setFromColor(mColorProgress);
        animation.setToColor(mColorComplete);

        animation.setFromStrokeColor(mColorIndicator);
        animation.setToStrokeColor(mColorComplete);

        animation.setListener(mCompleteStateListener);

        animation.start();

    }

    private void morphIdleToComplete() {
        MorphingAnimation animation = createMorphing();

        animation.setFromColor(mColorIdle);
        animation.setToColor(mColorComplete);

        animation.setFromStrokeColor(mColorIdle);
        animation.setToStrokeColor(mColorComplete);

        animation.setListener(mCompleteStateListener);

        animation.start();

    }

    private OnAnimationEndListener mCompleteStateListener = new OnAnimationEndListener() {
        @Override
        public void onAnimationEnd() {
            if (mIconComplete != 0) {
                setIcon(mIconComplete);
            } else {
                setText(mCompleteText);
            }
            mMorphingInProgress = false;
            mState = State.COMPLETE;
        }
    };

    private void morphCompleteToIdle() {
        MorphingAnimation animation = createMorphing();

        animation.setFromColor(mColorComplete);
        animation.setToColor(mColorIdle);

        animation.setFromStrokeColor(mColorComplete);
        animation.setToStrokeColor(mColorIdle);

        animation.setListener(mIdleStateListener);

        animation.start();

    }

    private void morphErrorToIdle() {
        MorphingAnimation animation = createMorphing();

        animation.setFromColor(mColorError);
        animation.setToColor(mColorIdle);

        animation.setFromStrokeColor(mColorError);
        animation.setToStrokeColor(mColorIdle);

        animation.setListener(mIdleStateListener);

        animation.start();

    }

    private OnAnimationEndListener mIdleStateListener = new OnAnimationEndListener() {
        @Override
        public void onAnimationEnd() {
            removeIcon();
            setText(mIdleText);
            mMorphingInProgress = false;
            mState = State.IDLE;
        }
    };

    private void morphIdleToError() {
        MorphingAnimation animation = createMorphing();

        animation.setFromColor(mColorIdle);
        animation.setToColor(mColorError);

        animation.setFromStrokeColor(mColorIdle);
        animation.setToStrokeColor(mColorError);

        animation.setListener(mErrorStateListener);

        animation.start();

    }

    private void morphProgressToError() {
        MorphingAnimation animation = createProgressMorphing(getHeight(), mCornerRadius, getHeight(), getWidth());

        animation.setFromColor(mColorProgress);
        animation.setToColor(mColorError);

        animation.setFromStrokeColor(mColorIndicator);
        animation.setToStrokeColor(mColorError);
        animation.setListener(mErrorStateListener);

        animation.start();
    }

    private OnAnimationEndListener mErrorStateListener = new OnAnimationEndListener() {
        @Override
        public void onAnimationEnd() {
            if (mIconComplete != 0) {
                setIcon(mIconError);
            } else {
                setText(mErrorText);
            }
            mMorphingInProgress = false;
            mState = State.ERROR;
        }
    };

    private void morphProgressToIdle() {
        background.getGradientDrawable().setStroke(mStrokeWidth, mColorIdle);
        background.getGradientDrawable().setColor(mColorIdle);

        removeIcon();
        setText(mIdleText);
        mMorphingInProgress = false;
        mState = State.IDLE;
    }

    private void setIcon(int icon) {
        Drawable drawable = getResources().getDrawable(icon);
        if (drawable != null) {
            int padding = (getWidth() / 2) - (drawable.getIntrinsicWidth() / 2);
            setCompoundDrawablesWithIntrinsicBounds(icon, 0, 0, 0);
            setPadding(padding, 0, 0, 0);
        }
    }

    protected void removeIcon() {
        setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        setPadding(0, 0, 0, 0);
    }

    /**
     * Set the View's background. Masks the API changes made in Jelly Bean.
     */
    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    public void setBackgroundCompat(Drawable drawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            setBackground(drawable);
        } else {
            setBackgroundDrawable(drawable);
        }
    }

    public void setProgress(int progress) {
        mProgress = progress;

        if (mMorphingInProgress) {
            return;
        }

        if (mProgress >= mMaxProgress) {
            if (mState == State.PROGRESS) {
                morphProgressToComplete();
            } else if (mState == State.IDLE) {
                morphIdleToComplete();
            }
        } else if (mProgress > IDLE_STATE_PROGRESS) {
            if (mState == State.IDLE) {
                morphToProgress();
            } else if (mState == State.PROGRESS) {
                invalidate();
            }
        } else if (mProgress == ERROR_STATE_PROGRESS) {
            if (mState == State.PROGRESS) {
                morphProgressToError();
            } else if (mState == State.IDLE) {
                morphIdleToError();
            }
        } else if (mProgress == IDLE_STATE_PROGRESS) {
            if (mState == State.COMPLETE) {
                morphCompleteToIdle();
            } else if (mState == State.PROGRESS) {
                morphProgressToIdle();
            } else if (mState == State.ERROR) {
                morphErrorToIdle();
            }
        }
    }

    public int getProgress() {
        return mProgress;
    }
}
