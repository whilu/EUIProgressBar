package com.github.wihlu.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by lujun on 2015/4/22.
 */
public class EUIProgressBar extends ImageView {

    private double mAngle = 0;
    private float pX, pY;
    private int mWidth, mHeight;
    private int mRingColor, mCircleColor;
    private int mRingRadius;
    private boolean isDraw = false;
    private Paint mPaint;
    private Timer mTimer;
    private TimerTask mTask;
    private final static int CIRCLE_RADIUS = 17;
    private final static int CIRCLE_ALPHHA = 212;

    public EUIProgressBar(Context context){
        super(context);
    }

    public EUIProgressBar(Context context, AttributeSet attrs){
        super(context, attrs);
        init(context, attrs);
    }

    public EUIProgressBar(Context context, AttributeSet attrs, int defStyleAttr){
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        mRingRadius = mWidth / 2 - CIRCLE_RADIUS;
        pX = mWidth / 2;
        pY = mHeight / 2 - mRingRadius;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!isDraw || mPaint == null){
            return;
        }
        //绘制圆环
        mPaint.setColor(mRingColor);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(mWidth / 2, mHeight / 2, mRingRadius, mPaint);
        //绘制圆球
        mPaint.setColor(mCircleColor);
        mPaint.setAlpha(CIRCLE_ALPHHA);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(pX, pY, CIRCLE_RADIUS, mPaint);
    }

    //init
    private void init(final Context context, final AttributeSet attrs){
        final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.EUIProgressBar);
        mRingColor = typedArray.getColor(R.styleable.EUIProgressBar_ringcolor,
                getResources().getColor(R.color.ringcolor));
        mCircleColor = typedArray.getColor(R.styleable.EUIProgressBar_circlecolor,
                getResources().getColor(R.color.circlecolor));
        typedArray.recycle();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    //stop draw
    private void stopDraw(){
        isDraw = false;
        mAngle = 0;
        pX = mWidth / 2;
        pY = mHeight / 2 - mRingRadius;
        if (mTimer != null){
            if (mTask != null){
                mTask.cancel();
            }
            mTimer.cancel();
        }
    }

    //start draw
    private void startDraw(){
        stopDraw();
        isDraw = true;
        mTask = new TimerTask() {
            @Override
            public void run() {
                mAngle = mAngle >= 360 ? 0 : ++mAngle;
                pX = mWidth / 2 + (float)(Math.sin(Math.toRadians(mAngle)) * mRingRadius);
                pY = mHeight / 2 - mRingRadius + (float)(mRingRadius - Math.cos(Math.toRadians(mAngle)) * mRingRadius);
                postInvalidate();
            }
        };
        mTimer = new Timer();
        mTimer.schedule(mTask, 0, 7);
    }

    //show the view
    public void show(){
        this.setVisibility(View.VISIBLE);
        startDraw();
    }

    //hide the view
    public void hide(){
        stopDraw();
        this.setVisibility(View.GONE);
    }
}
