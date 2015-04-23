package com.github.wihlu.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by lujun on 2015/4/22.
 */
public class GifImageView extends ImageView {

    private int mWidth, mHeight;
    private int mStrokeColor, mFillColor;
    private int mRingRadius;
    private int mInterpolatorType = 0;//默认加速
    private float mDegress = 0;
    private float mStartAngle = 0;
    private boolean isDraw = false;
    private Context mContext;
    private Paint mPaint;
    private Timer mTimer;
    private TimerTask mTask;
    private RectF mRect;
    private final static int CIRCLE_RADIUS = 37;
    private final static float STROKE_WIDTH = 7;
    private final static float TSZ_FINAL = 18.97f;
    private Interpolator mAccelerateInterpolator;

    public GifImageView(Context context){
        super(context);
    }

    public GifImageView(Context context, AttributeSet attrs){
        super(context, attrs);
        init(context, attrs);
    }

    public GifImageView(Context context, AttributeSet attrs, int defStyleAttr){
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        mRingRadius = CIRCLE_RADIUS;
        mRect = new RectF(mWidth / 2 - mRingRadius, mHeight / 2 - mRingRadius,
                mWidth / 2 + mRingRadius, mHeight / 2 + mRingRadius);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!isDraw || mPaint == null){
            return;
        }
        //绘制圆环
        mPaint.setColor(mStrokeColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(STROKE_WIDTH);
        canvas.drawCircle(mWidth / 2, mHeight / 2, mRingRadius, mPaint);
        //绘制圆弧
        mPaint.setColor(mFillColor);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawArc(mRect, mStartAngle, mAccelerateInterpolator.getInterpolation(mDegress), false, mPaint);
    }

    //init
    private void init(final Context context, final AttributeSet attrs){
        final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.EUIProgressBar);
        mStrokeColor = typedArray.getColor(R.styleable.EUIProgressBar_strokecolor,
                getResources().getColor(R.color.strokecolor));
        mFillColor = typedArray.getColor(R.styleable.EUIProgressBar_fillcolor,
                getResources().getColor(R.color.fillcolor));
        mInterpolatorType = typedArray.getInt(R.styleable.EUIProgressBar_interpolator, mInterpolatorType);
        typedArray.recycle();
        mContext = context;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        if (mInterpolatorType == 0){
            mAccelerateInterpolator = new AccelerateInterpolator();
        }else if (mInterpolatorType == 1){
//            mAccelerateInterpolator = new DecelerateInterpolator();
            mAccelerateInterpolator = new AccelerateInterpolator();
        }else if (mInterpolatorType == 2){
//            mAccelerateInterpolator = new LinearInterpolator();
            mAccelerateInterpolator = new AccelerateInterpolator();
        }else if (mInterpolatorType == 3){
//            mAccelerateInterpolator = new AccelerateDecelerateInterpolator();
            mAccelerateInterpolator = new AccelerateInterpolator();
        }else {
//            mAccelerateInterpolator = new AccelerateDecelerateInterpolator();
            mAccelerateInterpolator = new AccelerateInterpolator();
        }
    }

    //stop draw
    private void stopDraw(){
        isDraw = false;
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
        mDegress = 0;
        isDraw = true;
        mTask = new TimerTask() {
            @Override
            public void run() {
                if (GifImageView.this.getDrawable() != null){
                    stopDraw();
                    return;
                }
                if (mDegress >= TSZ_FINAL){
                    exchangeColor();
                    mStartAngle = mStartAngle >= 360 ?
                            (float)(Math.random() * 10) : mStartAngle + (float)(Math.random() * 10);
                }
                mDegress = mDegress >= TSZ_FINAL ? 0 : (float)(mDegress + 0.1);
                postInvalidate();
            }
        };
        mTimer = new Timer();
        mTimer.schedule(mTask, 0, 7);
    }

    //exchange color
    private void exchangeColor(){
        int tmpColor = mFillColor;
        mFillColor = mStrokeColor;
        mStrokeColor = tmpColor;
    }

    //set the image url and show
    public void showImage(String url){
        this.setVisibility(View.VISIBLE);
        Glide.with(mContext).load(url).centerCrop().into(this);
        startDraw();
    }

    //hide the view
    public void hide(){
        stopDraw();
        this.setVisibility(View.GONE);
    }
}
