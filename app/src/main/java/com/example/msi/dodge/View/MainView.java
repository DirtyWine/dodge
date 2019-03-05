package com.example.msi.dodge.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import com.example.msi.dodge.Constant.ConstantWhat;
import com.example.msi.dodge.R;
import com.example.msi.dodge.SoundPool.GameSoundPool;

/**
 * @Author: Cross
 * @Description:
 * @Date: 2018/6/17
 * @Modified by
 */
public class MainView extends BaseView {

    private float button_x;
    private float button_x2;
    private float button_y;
    private float button_y2;
    private float button_y3;
    private float button_y4;

    private Bitmap background;                // 背景图
    private Bitmap btn_start;
    private Bitmap btn_option;
    private Bitmap btn_help;
    private Bitmap btn_quit;
    private Rect rect;

    public MainView(Context context, GameSoundPool sounds) {
        super(context, sounds);
        rect = new Rect();
        thread = new Thread(this);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        super.surfaceChanged(holder, format, width, height);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        super.surfaceDestroyed(holder);
        release();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        super.surfaceCreated(holder);
        initBitmap();
        if (thread.isAlive()) {
            thread.start();
        } else {
            thread = new Thread(this);
            thread.start();
        }
    }

    @Override
    public void initBitmap() {
        background = BitmapFactory.decodeResource(getResources(),
                R.drawable.bg);
        btn_start = BitmapFactory.decodeResource(getResources(),
                R.drawable.start);

        btn_option = BitmapFactory.decodeResource(getResources(),
                R.drawable.option);

        btn_help = BitmapFactory.decodeResource(getResources(),
                R.drawable.help);
        btn_quit = BitmapFactory.decodeResource(getResources(),
                R.drawable.quit);

        scaleX = screen_width / background.getWidth();
        scaleY = screen_height / background.getHeight();


        button_x = screen_width / 2 - btn_start.getWidth() / 2;
        button_x2 = screen_width / 2 - btn_help.getWidth() / 2;
        button_y = screen_height / 2 -15;
        button_y2 = button_y + btn_start.getHeight() + 100;
        button_y3 = button_y2 + btn_option.getHeight() + 80;
        button_y4 = button_y3 + btn_help.getHeight() + 80;

    }

    @Override
    public void release() {
        if (!btn_start.isRecycled()) {
            btn_start.recycle();
        }
        if (!background.isRecycled()) {
            background.recycle();
        }
    }

    @Override
    public void drawSelf() {
        try {
            canvas = sfh.lockCanvas();
            canvas.drawColor(Color.BLACK);
            canvas.save();
            canvas.scale(scaleX, scaleY, 0, 0);
            canvas.drawBitmap(background, 0, 0, paint);
            canvas.restore();
            canvas.drawBitmap(btn_start, button_x, button_y, paint);
            canvas.drawBitmap(btn_option, button_x, button_y2, paint);
            canvas.drawBitmap(btn_help, button_x2, button_y3, paint);
            canvas.drawBitmap(btn_quit, button_x2, button_y4, paint);
            canvas.restore();
        } catch (Exception err) {
            err.printStackTrace();
        } finally {
            if (canvas != null)
                sfh.unlockCanvasAndPost(canvas);
        }
    }

    @Override
    public void run() {
        drawSelf();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN
                && event.getPointerCount() == 1) {
            float x = event.getX();
            float y = event.getY();
            if (x > button_x && x < button_x + btn_start.getWidth()
                    && y > button_y && y < button_y + btn_start.getHeight()) {
                gameActivity.getHandler().sendEmptyMessage(ConstantWhat.TO_PLAY_VIEW);
            }
            return true;
        }
        return false;
    }

}
