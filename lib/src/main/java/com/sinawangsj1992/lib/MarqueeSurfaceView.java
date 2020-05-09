package com.sinawangsj1992.lib;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


public class MarqueeSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    private SurfaceHolder mHolder;
    private Paint mPaint;
    private int mWidth, mHeight;

    private String mText = "";
    private float textBottomY;
    private float textLength;

    /**
     * 文字的横坐标偏移量
     */
    private float offX = 0f;
    // view + textview
    private float viewTextWidth;


    private boolean isRun = true;
    private volatile boolean isRunning = false;
    private float mStep = 4;
    private float mFps = 30;

    public MarqueeSurfaceView(Context context) {
        this(context, null);
    }

    public MarqueeSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setZOrderOnTop(true);
        init();
    }

    public MarqueeSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mHolder = getHolder();
        mHolder.setFormat(PixelFormat.TRANSLUCENT);
        mHolder.addCallback(this);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextSize(350);
        mPaint.setColor(Color.WHITE);
    }

    public void setText(String text) {
        this.mText = text;
        resetTextLength();
    }

    public void setTextSize(int size) {
        this.mPaint.setTextSize(size);
        resetY();
        resetTextLength();
    }

    public void setTextColor(String color) {
        this.mPaint.setColor(Color.parseColor(color));
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mWidth = getWidth();
        mHeight = getHeight();

        resetY();
        resetTextLength();
    }

    private void resetY() {
        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        textBottomY = mHeight / 2 + (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
    }

    private void resetTextLength() {
        textLength = mPaint.measureText(mText);
        if (textLength < mWidth) {
            isRun = false;
            isRunning = false;
        } else {
            isRun = true;
            // 滚动文本的长度: view 长度 + 文本滑出屏幕的长度
            viewTextWidth = mWidth + textLength;
            offX = mWidth;
        }
        start();
    }

    private void start() {
        if (isRun) {
            if (isRunning) {

            } else {
                isRunning = isRun;
                new Thread(() -> {
                    while (isRunning) {
                        draw2();
                        try {
                            Thread.sleep((long) mFps);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        } else {
            new Thread(() -> {
                draw();
            }).start();
        }

    }

    private void draw2() {
        Canvas canvas = mHolder.lockCanvas();
        if (canvas == null) {
            return;
        }
        if (offX <= viewTextWidth) {
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            // viewWidth - offX： x 坐标逐渐变小,往左移动
            canvas.drawText(mText, mWidth - offX, textBottomY, mPaint);
        } else {
            offX = 0;
        }
        mHolder.unlockCanvasAndPost(canvas);
        // 滚动文本的偏移量
        offX += mStep;
    }

    private void draw() {
        Canvas canvas = mHolder.lockCanvas();
        if (canvas == null) {
            return;
        }
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        canvas.drawText(mText, mWidth / 2 - textLength / 2, textBottomY, mPaint);
        mHolder.unlockCanvasAndPost(canvas);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isRun = false;
    }
}
