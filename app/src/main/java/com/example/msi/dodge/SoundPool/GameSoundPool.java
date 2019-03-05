package com.example.msi.dodge.SoundPool;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import com.example.msi.dodge.GameActivity;
import com.example.msi.dodge.MainActivity;
import com.example.msi.dodge.R;

import java.util.HashMap;

/**
 * @Author: Cross
 * @Description:
 * @Date: 2018/6/17
 * @Modified by
 */

public class GameSoundPool {
    private GameActivity gameActivity;
    private SoundPool soundPool;
    private HashMap<Integer, Integer> map;

    @SuppressLint("UseSparseArrays")
    public GameSoundPool(GameActivity gameActivity) {
        this.gameActivity = gameActivity;
        map = new HashMap<Integer, Integer>();
        soundPool = new SoundPool(8, AudioManager.STREAM_MUSIC, 0);
    }

    /**
     * 初始化游戏音乐
     */
    public void initGameSound() {
        map.put(1, soundPool.load(gameActivity, R.raw.good, 1));
    }

    /**
     * 播放游戏背音乐
     */
    public void playSound(int sound, int loop) {
        AudioManager am = (AudioManager) gameActivity.getSystemService(Context.AUDIO_SERVICE);
        float stramVolumeCurrent = am.getStreamVolume(AudioManager.STREAM_MUSIC);
        float stramMaxVolumeCurrent = am.getStreamVolume(AudioManager.STREAM_MUSIC);
        float volume = stramVolumeCurrent / stramMaxVolumeCurrent;
        soundPool.play(map.get(sound), volume, volume, 1, loop, 1.0f);
    }
}
