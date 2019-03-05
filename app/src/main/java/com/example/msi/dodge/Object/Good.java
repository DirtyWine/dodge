package com.example.msi.dodge.Object;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;
import com.example.msi.dodge.R;


/**
 * @Author: Cross
 * @Description:
 * @Date: 2018/6/17
 * @Modified by
 */
public class Good extends GameObject {

    private static final String TAG = "Good" ;



    private Bitmap good;



    public Good(Resources resources) {
        super(resources);
        initBitmap();
    }

    @Override
    protected void initBitmap() {
        good = BitmapFactory.decodeResource(resources,
                R.drawable.good);

        object_width = good.getWidth();
        object_height = good.getHeight();
    }


    @Override
    public void crashCheck(Player player) {
        super.crashCheck(player);
        if (isCrash()) {
            player.setGood(true);
        }
    }

    @Override
    public void drawSelf(Canvas canvas) {
        if(isOut() || isCrash()) return;
        canvas.save();
        canvas.drawBitmap(good,object_x,object_y,paint);
        Log.e(TAG,"GOOD DRAW");
        canvas.restore();
    }

    @Override
    public void release() {
        if (!good.isRecycled())
            good.recycle();
    }

}
