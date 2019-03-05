package com.example.msi.dodge;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import com.example.msi.dodge.Constant.ConstantGame;

/**
 * @Author: Cross
 * @Description:
 * @Date: 2018/6/18
 * @Modified by
 */
public class BaseActivity extends Activity {

    private boolean hasSound = true;
    private boolean hasBgm = true;
    private int mode = ConstantGame.MODE_EASY;

    public boolean isHasSound() {
        return hasSound;
    }

    public void setHasSound(boolean hasSound) {
        this.hasSound = hasSound;
    }

    public boolean isHasBgm() {
        return hasBgm;
    }

    public int getMode() {
        return mode;
    }

    public void setHasBgm(boolean hasBgm) {
        this.hasBgm = hasBgm;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.removeActivity(this);
    }

    /**
     * @Description: 显示游戏规则
     */
    public void showRules(int game) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Rules of game");
        String rule = getResources().getString(game);
        builder.setMessage(rule);
        builder.setPositiveButton("Ok",null);
        builder.create().show();
    }

    /**
     * @Description: 退出应用
     */
    public void exit() {
        ActivityManager.finishAll();
    }
}
