package com.lb.scroll;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

public class OverScrollerView extends View {
    private static final int SF_DURATION = 3000;

    /*public static final int CENTER = 1;
    public static final int LEFT,
    public static final int LEFT_TOP,
    public static final int LEFT_BOTTOM,
    public static final int TOP,
    public static final int RIGHT_TOP,
    public static final int RIGHT,
    public static final int RIGBT_BOTTOM,
    public static final int BOTTOM;*/


    public OverScrollerView(Context context) {
        super(context);
    }

    public OverScrollerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OverScrollerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    private String mDrawingText = "测试框架,在某明星的演出会现场";


    private Paint mPaint;
    private float mBaseLineY;
    private float mFac;
    private float mTxtDrawingLen;
    private ValueAnimator mAnimtor;
    private int mOnePageDuration = SF_DURATION;
    private int mDuration;

    //private DERICTION mDeriction = DERICTION.CENTER;
    private int mCounter = 0;

    private void initLayout() {
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        if (width == 0 || height == 0 || mPaint != null) {
            return;
        }

        if (mCounter > 2) {
            return;
        }

        int totalHeight = (mCounter + 1 ) * height;

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(totalHeight);
        mPaint.setColor(Color.RED);

        Paint.FontMetrics fm = mPaint.getFontMetrics();
        mBaseLineY = Math.abs(fm.ascent + fm.descent) / 2 + totalHeight;

        mTxtDrawingLen = mPaint.measureText(mDrawingText) + width;
        mDuration = (int) (mTxtDrawingLen / width * mOnePageDuration);

        startScroll();
    }

    public void setDrawingText(String text) {
        mDrawingText = text;
    }

    public void setCount(int count) {
        mCounter = count;
    }


    public void startScroll() {
        stopScroll();

        mAnimtor = ValueAnimator.ofFloat(0, 1);
        mAnimtor.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mFac = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        mAnimtor.setRepeatCount(ValueAnimator.INFINITE);
        mAnimtor.setDuration(mDuration);
        mAnimtor.setInterpolator(new LinearInterpolator());
        mAnimtor.start();
    }

    public void stopScroll() {
        if (mAnimtor != null) {
            mAnimtor.cancel();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
        initLayout();
        if (mDrawingText == null || mPaint == null) {
            return;
        }
        canvas.save();
        drawText(canvas);
        canvas.restore();
    }

    private void drawText(Canvas canvas) {
        mPaint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText(mDrawingText, getMeasuredWidth() - mTxtDrawingLen * mFac, mBaseLineY, mPaint);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

    }

}
