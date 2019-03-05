package com.example.msi.dodge.Object;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * @Author: Cross
 * @Description:
 * @Date: 2018/6/18
 * @Modified by
 */

abstract class GameObject {
    private boolean isOut;
    private boolean isCrash;
    protected float object_x;        // 物品的x坐标
    protected float object_y;        // 物品的y坐标
    protected float object_width;    // 物体宽度
    protected float object_height;    // 物体高度
    protected float screen_width;    // 屏幕宽度
    protected float screen_height;  // 屏幕高度
    protected Paint paint;            // 画笔
    protected Resources resources;  // 资源文件

    public GameObject(Resources resources) {
        this.resources = resources;
        this.paint = new Paint();
        isCrash = false;
        isOut = false;
    }

    public void setScreenWH(float screen_width, float screen_height) {
        this.screen_width = screen_width;
        this.screen_height = screen_height;
    }

    //碰撞检测
    public void crashCheck(Player player) {
        float player_x = player.getObject_x();
        float player_y = player.getObject_y();
        if ( (object_y + object_height) < player_y || object_x > (player_x + player.getObject_width()) || player_x > (object_x + object_width)) {
            isCrash = false;
        } else {
            isCrash = true;
        }
    }

    //是否移出屏幕
    public void outCheck() {
        if (object_y > screen_height) {
            isOut = true;
        } else {
            isOut = false;
        }
    }

    //资源初始化
    protected abstract void initBitmap();

    //绘制函数
    public abstract void drawSelf(Canvas canvas);

    public abstract void release();

    public boolean isOut() {
        return isOut;
    }

    public boolean isCrash() {
        return isCrash;
    }

    public void setObject_x(float object_x) {
        this.object_x = object_x;
    }

    public void setObject_y(float object_y) {
        this.object_y = object_y;
    }

    public float getObject_x() {

        return object_x;
    }

    public float getObject_y() {
        return object_y;
    }

    public float getObject_width() {
        return object_width;
    }

    public float getObject_height() {
        return object_height;
    }

}
