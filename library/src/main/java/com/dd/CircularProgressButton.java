package com.dd;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.Button;
import com.dd.circular.progress.button.R;

public class CircularProgressButton extends Button {

    public static final int IDLE_STATE_PROGRESS = 0;
    public static final int ERROR_STATE_PROGRESS = -1;

    private GradientDrawable background;

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
    private float mCornerRadius;

    private enum State {
        PROGRESS, IDLE, COMPLETE, ERROR;
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
        initAttributes(context, attributeSet);

        mMaxProgress = 100;
        mState = State.IDLE;

        setText(mIdleText);

        background = (GradientDrawable) context.getResources().getDrawable(R.drawable.background).mutate();
        background.setCornerRadius(mCornerRadius);
        setBackgroundCompat(background);
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
            mColorIndicatorBackground = attr.getColor(R.styleable.CircularProgressButton_colorIndicatorBackground, grey);
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
            doDraw(canvas);
        }
    }

    private RectF mRectF;
    private Paint mPaint;

    private void doDraw(Canvas canvas) {
        float sweepAngle = (360f / mMaxProgress) * mProgress;

        int offset = (getWidth() - getHeight()) / 2;

        Path path = new Path();
        path.addArc(getRect(), -90, sweepAngle);
        path.offset(offset, 0);
        canvas.drawPath(path, createPaint());
    }

    private RectF getRect() {
        if (mRectF == null) {
            int strokeWidth = (int) getResources().getDimension(R.dimen.stroke_width);
            int index = strokeWidth / 2;
            mRectF = new RectF(index, index, getHeight() - index, getHeight() - index);
        }
        return mRectF;
    }

    private Paint createPaint() {
        if (mPaint == null) {
            int strokeWidth = (int) getResources().getDimension(R.dimen.stroke_width);

            mPaint = new Paint();
            mPaint.setAntiAlias(true);
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeWidth(strokeWidth);
            mPaint.setColor(mColorIndicator);
        }

        return mPaint;
    }

    private void morphToProgress() {
        mMorphingInProgress = true;

        setText(null);

        MorphingAnimation animation = new MorphingAnimation(this, background);
        animation.setFromCornerRadius(mCornerRadius);
        animation.setToCornerRadius(getHeight());

        animation.setFromWidth(getWidth());
        animation.setToWidth(getHeight());

        animation.setFromColor(mColorIdle);
        animation.setToColor(mColorProgress);

        animation.setFromStrokeColor(mColorIdle);
        animation.setToStrokeColor(mColorIndicatorBackground);

        animation.setListener(new OnAnimationEndListener() {
            @Override
            public void onAnimationEnd() {
                mMorphingInProgress = false;
                mState = State.PROGRESS;
            }
        });

        animation.start();
    }

    private void morphToToComplete() {
        mMorphingInProgress = true;

        MorphingAnimation animation = new MorphingAnimation(this, background);
        animation.setFromCornerRadius(getHeight());
        animation.setToCornerRadius(mCornerRadius);

        animation.setFromWidth(getHeight());
        animation.setToWidth(getWidth());

        animation.setFromColor(mColorProgress);
        animation.setToColor(mColorComplete);

        animation.setFromStrokeColor(mColorIndicator);
        animation.setToStrokeColor(mColorComplete);

        animation.setListener(new OnAnimationEndListener() {
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
        });

        animation.start();

    }

    private void morphToToError() {
        mMorphingInProgress = true;

        MorphingAnimation animation = new MorphingAnimation(this, background);
        animation.setFromCornerRadius(getHeight());
        animation.setToCornerRadius(mCornerRadius);

        animation.setFromWidth(getHeight());
        animation.setToWidth(getWidth());

        animation.setFromColor(mColorProgress);
        animation.setToColor(mColorError);

        animation.setFromStrokeColor(mColorIndicator);
        animation.setToStrokeColor(mColorError);

        animation.setListener(new OnAnimationEndListener() {
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
        });

        animation.start();
    }

    private void morphToToIdle() {
        int strokeWidth = (int) getContext().getResources().getDimension(R.dimen.stroke_width);
        background.setStroke(strokeWidth, mColorIdle);
        background.setColor(mColorIdle);

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

        if (!mMorphingInProgress) {
            if (mProgress >= mMaxProgress) {
                if (mState == State.PROGRESS) {
                    morphToToComplete();
                }
            } else if (mProgress > IDLE_STATE_PROGRESS) {
                if (mState == State.IDLE) {
                    morphToProgress();
                } else if (mState == State.PROGRESS) {
                    invalidate();
                }
            } else if (mProgress == ERROR_STATE_PROGRESS) {
                if (mState == State.PROGRESS) {
                    morphToToError();
                }
            } else if (mProgress == IDLE_STATE_PROGRESS) {
                morphToToIdle();
            }
        }
    }

    public int getProgress() {
        return mProgress;
    }
}
