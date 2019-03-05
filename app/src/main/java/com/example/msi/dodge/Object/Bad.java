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
public class Bad extends GameObject {

    private static final String TAG = "BAD";


    private Bitmap bad;



    public Bad(Resources resources) {
        super(resources);
        initBitmap();
    }

    @Override
    protected void initBitmap() {
        bad = BitmapFactory.decodeResource(resources,
                R.drawable.bad);

        object_width = bad.getWidth();
        object_height = bad.getHeight();
    }

    @Override
    public void crashCheck(Player player) {
        super.crashCheck(player);
        if (isCrash()) {
            player.setBad(true);
        }
    }

    @Override
    public void drawSelf(Canvas canvas) {
        if(isOut() || isCrash()) return;
        canvas.save();
        canvas.drawBitmap(bad,object_x,object_y,paint);
        Log.e(TAG,"BAD DRAW");
        canvas.restore();
    }

    @Override
    public void release() {
        if (!bad.isRecycled())
            bad.recycle();
    }


}
