package com.example.msi.dodge.Object;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import com.example.msi.dodge.R;

/**
 * @Author: Cross
 * @Description:
 * @Date: 2018/6/17
 * @Modified by
 */
public class Player  extends GameObject{

    private boolean isBad;
    private boolean isGood;

    private Bitmap player;
    private Bitmap nice;
    private Bitmap bad;

    private int goodTimer = 0;
    private int badTimer = 0;

    public Player(Resources resources) {
        super(resources);
        isBad = false;
        isGood = false;
        initBitmap();
    }

    @Override
    protected void initBitmap() {
        player = BitmapFactory.decodeResource(resources,
                R.drawable.player);
        nice = BitmapFactory.decodeResource(resources,
                R.drawable.nice);

        bad = BitmapFactory.decodeResource(resources,
                R.drawable.shit);

        object_width = player.getWidth();
        object_height = player.getHeight();

    }

    @Override
    public void drawSelf(Canvas canvas) {
        canvas.save();
        canvas.drawBitmap(player,object_x,object_y,paint);
        canvas.restore();
        drawCrash(canvas,isGood,isBad);
    }

    public void drawCrash (Canvas canvas, boolean carshGood, boolean crashBad) {

        if (crashBad) {
            badTimer ++;
            canvas.save();
            canvas.drawBitmap(bad,object_x,object_y + 40,paint);
            canvas.restore();
            if (badTimer > 3)  {
                isBad = false;
                badTimer = 0;
            }

        }
        if (carshGood) {
            goodTimer ++;
            canvas.save();
            canvas.drawBitmap(nice,object_x,object_y + 30,paint);
            canvas.restore();
            if (goodTimer > 3)  {
                isGood = false;
                goodTimer = 0;
            }
        }
    }


    @Override
    public void release() {
        if (!player.isRecycled())
            player.recycle();
        if (!nice.isRecycled())
            nice.recycle();
        if (!bad.isRecycled())
            bad.recycle();
    }



    public boolean isBad() {
        return isBad;
    }

    public boolean isGood() {
        return isGood;
    }



    public void setBad(boolean bad) {
        isBad = bad;
    }

    public void setGood(boolean good) {
        isGood = good;
    }
}
