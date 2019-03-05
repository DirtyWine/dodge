package com.example.msi.dodge;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.*;
import com.example.msi.dodge.Constant.ConstantGame;
import com.example.msi.dodge.Constant.ConstantWhat;
import com.example.msi.dodge.SoundPool.GameSoundPool;
import com.example.msi.dodge.View.BaseView;
import com.example.msi.dodge.View.MainView;
import com.example.msi.dodge.View.PlayView;

public class MainActivity extends BaseActivity {

    private ImageView start;
    private ImageView option;
    private ImageView help;
    private ImageView quit;

    private RadioGroup options;
    private RadioGroup bgm;
    private RadioGroup sound;

    private Button option_ok;
    private TextView multiplayer;

    private LinearLayout menu_main;
    private LinearLayout menu_option;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start = (ImageView) findViewById(R.id.start);
        help = (ImageView) findViewById(R.id.help);
        option = (ImageView) findViewById(R.id.option);
        quit = (ImageView) findViewById(R.id.quit);

        options = (RadioGroup) findViewById(R.id.options);
        bgm = (RadioGroup) findViewById(R.id.bgm_option);
        sound = (RadioGroup) findViewById(R.id.sound_option);

        option_ok = (Button) findViewById(R.id.ok_option);
        multiplayer = (TextView) findViewById(R.id.multiplay);

        menu_main = (LinearLayout) findViewById(R.id.main_menu);
        menu_option = (LinearLayout) findViewById(R.id.option_menu);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),GameActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("mode",getMode());
                bundle.putBoolean("bgm",isHasBgm());
                bundle.putBoolean("sound",isHasSound());
                intent.putExtra("Bundle",bundle);
                startActivity(intent);
            }
        });

        option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu_main.setVisibility(View.GONE);
                menu_option.setVisibility(View.VISIBLE);
            }
        });

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(),InfoActivity.class));
            }
        });

        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exit();
            }
        });

        option_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu_main.setVisibility(View.VISIBLE);
                menu_option.setVisibility(View.GONE);
            }
        });

        bgm.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if ( R.id.bgm_on == checkedId) {
                    setHasBgm(true);
                } else {
                    setHasBgm(false);
                }
            }
        });

        sound.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if ( R.id.sound_on == checkedId) {
                    setHasSound(true);
                } else {
                    setHasSound(false);
                }
            }
        });

        options.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.easy:
                        setMode(ConstantGame.MODE_EASY);
                        break;
                    case R.id.mid:
                        setMode(ConstantGame.MODE_MID);
                        break;
                    case R.id.hard:
                        setMode(ConstantGame.MODE_HARD);
                        break;

                        default:
                            setMode(ConstantGame.MODE_EASY);
                }
            }
        });

        multiplayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(),ReadyActivity.class));
            }
        });
    }

}
