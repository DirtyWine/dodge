package com.example.msi.dodge.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Message;
import android.telecom.Connection;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import com.example.msi.dodge.Constant.ConstantGame;
import com.example.msi.dodge.Constant.ConstantWhat;
import com.example.msi.dodge.Object.Bad;
import com.example.msi.dodge.Object.Good;
import com.example.msi.dodge.Object.Player;
import com.example.msi.dodge.R;
import com.example.msi.dodge.SoundPool.GameSoundPool;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @Author: Cross
 * @Description:
 * @Date: 2018/6/17
 * @Modified by
 */
public class PlayView extends BaseView {

    private static final String TAG = "TAG";
    Random random = new Random();

    private int timer1;
    private int timer2;

    private int score; // 游戏总得分
    private float bg_y; // 图片的坐标
    private float bg_y2;

    private boolean isTouchPlane; // 判断玩家是否按下屏幕

    private Bitmap background; // 背景图片
    private Bitmap background2; // 背景图片


    private Player player; // 玩家

    private ArrayList<Bad> bads; //障碍物数组
    private int badCount;

    private ArrayList<Good> goods; //目标数组

    private MediaPlayer backgroundMusic; //背景音乐

    private MediaPlayer goodCrash;
    private MediaPlayer badCrash;

    private int moveSpeed; // 物品移动速度
    private int modeType; // Bad出现的概率

    private boolean hasSound;


    public PlayView(Context context, GameSoundPool sounds, int mode, boolean hasBgm, boolean hasSound) {
        super(context, sounds);

        thread = new Thread(this);
        badCount = 0;
        bads = new ArrayList<Bad>();
        goods = new ArrayList<Good>();
        score = 0;
        timer1 = 0;
        timer2 = 0;

        this.hasSound = hasSound;
        // 背景音乐
        backgroundMusic = MediaPlayer.create(gameActivity, R.raw.phantom);
        //音效
        goodCrash = MediaPlayer.create(gameActivity,R.raw.good);
        badCrash = MediaPlayer.create(gameActivity,R.raw.bad);
        backgroundMusic.setLooping(true);
        //背景音乐播放
        if (hasBgm) {
            if (! backgroundMusic.isPlaying()) {
                backgroundMusic.start();
            }
        }
        //设置难度
        switch (mode) {
            case ConstantGame.MODE_EASY:
                moveSpeed = ConstantGame.SLOW_SPEED;
                modeType = ConstantGame.Low_CHANCE;
                break;
            case ConstantGame.MODE_MID:
                moveSpeed = ConstantGame.SLOW_SPEED;
                modeType = ConstantGame.HIGH_CHANCE;
                break;
            case ConstantGame.MODE_HARD:
                moveSpeed = ConstantGame.FAST_SPEED;
                modeType = ConstantGame.HIGH_CHANCE;
                break;
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        super.surfaceChanged(holder, format, width, height);
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
    public void surfaceDestroyed(SurfaceHolder arg0) {
        super.surfaceDestroyed(arg0);
        backgroundMusic.release();
        goodCrash.release();
        badCrash.release();
        release();// 释放资源
    }

    // 初始化图片资源方法
    @Override
    public void initBitmap() {
        background = BitmapFactory.decodeResource(getResources(),
                R.drawable.bb1);
        background2 = BitmapFactory.decodeResource(getResources(),
                R.drawable.bb2);

        scaleX = screen_width / background.getWidth();
        scaleY = screen_height / background.getHeight();

        bg_y = 0;
        bg_y2 = bg_y - screen_height;

        player = new Player(getResources());
        player.setScreenWH(screen_width,screen_height);
        player.setObject_x(screen_width/2 - player.getObject_width()/2);
        player.setObject_y(screen_height - player.getObject_height()-50);
    }

    // 释放图片资源的方法
    @Override
    public void release() {

        if (!background.isRecycled()) {
            background.recycle();
        }
        if (!background2.isRecycled()) {
            background2.recycle();
        }

        for(Bad obj : bads) {
            obj.release();
        }

        for (Good obj : goods) {
            obj.release();
        }
    }


    //创建障碍物
    public void initBad() {
        timer1 += 100;
        int chanceBad = random.nextInt(100);
        Log.e(TAG, "BadChance: " + chanceBad);
        if (  modeType < chanceBad && timer1 > 1000 ) {
            badCount ++;
            Log.e(TAG,"bad created");
            Bad bad = new Bad(getResources());
            bad.setScreenWH(screen_width,screen_height);
            bad.setObject_x((float) Math.random() * (screen_width - bad.getObject_width()));
            bad.setObject_y(0 - bad.getObject_height());
            bads.add(bad);
            timer1 = timer2 % 1000;
        }
    }

    //创建加分物品
    public void initGood() {
        timer2 += 100;
        int chanceGood = random.nextInt(100);
        Log.e(TAG,", GoodChance: "+chanceGood);

        if ( 80 < chanceGood && timer2 > 2000 ) {
            Log.e(TAG,"good created");
            Good good = new Good(getResources());
            good.setScreenWH(screen_width,screen_height);
            good.setObject_x((float) Math.random() * (screen_width - good.getObject_width()));
            good.setObject_y(0 - good.getObject_height());
            goods.add(good);
            timer2 = timer2 % 2000;
        }
    }

    //移出已被碰撞或者跑出屏幕外的物品
    public void removeObject() {
        for (Bad obj : bads) {
            if (obj.isCrash() || obj.isOut()) {
                badCount--;
                bads.remove(obj);
            }
        }

        for (Good obj : goods) {
            if (obj.isOut() || obj.isCrash()) {
                goods.remove(obj);
            }
        }
    }

    // 绘图方法
    @Override
    public void drawSelf() {
        try {
            canvas = sfh.lockCanvas();
            canvas.drawColor(Color.BLACK); // 绘制背景色
            canvas.save();
            // 计算背景图片与屏幕的比例
            canvas.scale(scaleX, scaleY, 0, 0);
            canvas.drawBitmap(background, 0, bg_y, paint); // 绘制背景图
            canvas.drawBitmap(background2, 0, bg_y2, paint); // 绘制背景图
            canvas.restore();

            //绘制玩家
            player.drawSelf(canvas);

            //绘制障碍物
            for (Bad obj : bads) {
                //碰撞检测
                obj.crashCheck(player);
                obj.outCheck();
                if (obj.isCrash()) {
                    //碰撞则减分
                    score -= 1;
                    if( hasSound ) {
                        badCrash.start();
                    }
                }
                obj.drawSelf(canvas);
            }

            //绘制加分物品
            for (Good obj : goods) {
                //碰撞检测
                obj.crashCheck(player);
                obj.outCheck();
                if (obj.isCrash()) {
                    if ( hasSound ) {
                        goodCrash.start();
                    }
                    //加分
                    score += 2;
                }
                obj.drawSelf(canvas);
            }
            canvas.save();
            //绘制得分
            paint.setTextSize(60);
            paint.setColor(Color.rgb(235, 161, 1));
            canvas.drawText("Score:" + String.valueOf(score), 30 , 50, paint);
            canvas.restore();

            removeObject();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (canvas != null)
                sfh.unlockCanvasAndPost(canvas);
        }
    }

    // 背景及物品移动的逻辑函数
    public void viewLogic() {
        if (bg_y > bg_y2) {
            bg_y += moveSpeed;
            bg_y2 = bg_y - background.getHeight();
        } else {
            bg_y2 += moveSpeed;
            bg_y = bg_y2 - background.getHeight();
        }
        if (bg_y >= background.getHeight()) {
            bg_y = bg_y2 - background.getHeight();
        } else if (bg_y2 >= background.getHeight()) {
            bg_y2 = bg_y - background.getHeight();
        }
        for (Bad obj : bads) {
            obj.setObject_y(obj.getObject_y() + moveSpeed );
        }
        for (Good obj : goods) {
            obj.setObject_y(obj.getObject_y() + moveSpeed );
        }
    }

    // 游戏更新的线程
    @Override
    public void run() {
        while (threadFlag) {
            long startTime = System.currentTimeMillis();
            initBad(); //更新障碍物
            initGood(); //更新加分物品
            drawSelf(); //更新场景
            viewLogic(); // 背景移动的逻辑
            long endTime = System.currentTimeMillis();
            try {
                if (endTime - startTime < 100)
                    Thread.sleep(100 - (endTime - startTime));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //根据得分判断游戏是否结束
            if (score < 0 || score >= 10) {
                //游戏结束
                threadFlag = false;
            }

        }
        if (backgroundMusic.isPlaying()) {
            backgroundMusic.stop();
        }
        Message message = new Message();
        message.what = ConstantWhat.TO_END_VIEW;
        if ( score >= 10 ) {
            message.arg1 = ConstantGame.WIN;
        } else {
            message.arg1 = ConstantGame.LOSE;
        }
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        gameActivity.getHandler().sendMessage(message);
    }

    // 响应触屏事件的方法
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            isTouchPlane = false;
            return true;
        } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
            float x = event.getX();
            float y = event.getY();
            // 判断玩家是否被按下
            if (x > player.getObject_x()
                    && x < player.getObject_x() + player.getObject_width()
                    && y > player.getObject_y()
                    && y < player.getObject_y() + player.getObject_height()) {
                isTouchPlane = true;
                return true;
            }
        }
        // 响应手指在屏幕移动的事件
        else if (event.getAction() == MotionEvent.ACTION_MOVE
                && event.getPointerCount() == 1) {
            // 判断触摸点是否为玩家
            if (isTouchPlane) {
                float x = event.getX();
                if (x > player.getObject_x() + player.getObject_width()/2+20) {
                    if (player.getObject_x() + player.getObject_width() + 20 <= screen_width) {
                        player.setObject_x(player.getObject_x() + 20);
                    }
                } else if (x < player.getObject_x() + player.getObject_width()/2-20) {
                    if (player.getObject_x() -20 >= 0) {
                        player.setObject_x(player.getObject_x() - 20);
                    }
                }
                return true;
            }
        }
        return false;
    }

    public void playSound(int key) {
        sounds.playSound(key, -1);
    }

    public int getBadCount() {
        return badCount;
    }

    public int getScore() {
        return score;
    }
}
