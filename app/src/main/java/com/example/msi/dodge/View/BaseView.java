package com.example.msi.dodge.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import com.example.msi.dodge.GameActivity;
import com.example.msi.dodge.MainActivity;
import com.example.msi.dodge.SoundPool.GameSoundPool;

/**
 * @Author: Cross
 * @Description:
 * @Date: 2018/6/17
 * @Modified by
 */
public class BaseView extends SurfaceView implements SurfaceHolder.Callback,Runnable {
    protected int currentFrame;
    protected float scaleX;
    protected float scaleY;
    protected float screen_width;
    protected float screen_height;
    protected boolean threadFlag;
    protected Paint paint;
    protected Canvas canvas;
    protected Thread thread;
    protected SurfaceHolder sfh;
    public GameSoundPool sounds;
    protected GameActivity gameActivity;

    public BaseView(Context context, GameSoundPool sounds) {
        super(context);
        this.sounds = sounds;
        this.gameActivity = (GameActivity) context;
        sfh = this.getHolder();
        sfh.addCallback(this);
        paint = new Paint();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        screen_width = this.getWidth();
        screen_height = this.getHeight();
        threadFlag = true;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        threadFlag = false;
    }


    public void initBitmap() { }

    /**
     * 释放资源
     */
    public void release() { }

    /**
     * 绘制
     */
    public void drawSelf() { }

    @Override
    public void run() {
    }

    public void setThreadFlag(boolean threadFlag) {
        this.threadFlag = threadFlag;
    }


}
