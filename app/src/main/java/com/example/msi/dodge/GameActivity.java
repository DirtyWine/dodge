package com.example.msi.dodge;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import com.example.msi.dodge.Constant.ConstantGame;
import com.example.msi.dodge.Constant.ConstantWhat;
import com.example.msi.dodge.SoundPool.GameSoundPool;
import com.example.msi.dodge.View.MainView;
import com.example.msi.dodge.View.PlayView;

public class GameActivity extends BaseActivity {

    private PlayView playView;
    private GameSoundPool sounds;



    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == ConstantWhat.TO_END_VIEW) {
                goEndView(msg.arg1);
            }
            return false;
        }
    });

    public Handler getHandler() {
        return handler;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        sounds = new GameSoundPool(this);

        Intent intent = getIntent();
        final Bundle bundle = intent.getBundleExtra("Bundle");
        setHasBgm(bundle.getBoolean("bgm"));
        setHasSound(bundle.getBoolean("sound"));
        setMode(bundle.getInt("mode"));
    }

    @Override
    protected void onResume() {
        super.onResume();
        playView = new PlayView(this,sounds,getMode(),isHasBgm(),isHasSound());
        setContentView(playView);
    }

    public void goEndView(int result) {
        Intent intent = new Intent(this,EndActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("mode",getMode());
        bundle.putBoolean("bgm",isHasBgm());
        bundle.putBoolean("sound",isHasSound());
        bundle.putInt("result",result);
        intent.putExtra("Bundle",bundle);
        startActivity(intent);
    }
}
