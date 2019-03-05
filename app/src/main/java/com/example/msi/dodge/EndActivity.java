package com.example.msi.dodge;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.example.msi.dodge.Constant.ConstantGame;
import org.w3c.dom.Text;

public class EndActivity extends BaseActivity {

    private int result;

    private TextView resultText;

    private MediaPlayer winMusic;
    private MediaPlayer loseMusic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        Intent intent = getIntent();
        final Bundle bundle = intent.getBundleExtra("Bundle");
        setHasBgm(bundle.getBoolean("bgm"));
        setMode(bundle.getInt("mode"));
        setHasSound(bundle.getBoolean("sound"));
        result = bundle.getInt("result");

        resultText = (TextView) findViewById(R.id.result);

        winMusic = MediaPlayer.create(this,R.raw.win);
        loseMusic = MediaPlayer.create(this,R.raw.lose);

        if (ConstantGame.WIN == result) {
            resultText.setText(getString(R.string.win));
            if (isHasSound()) {
                winMusic.start();
            }

        } else {
            resultText.setText(getString(R.string.lose));
            if (isHasSound()) {
                loseMusic.start();
            }
        }
    }

    public void tryAgain(View view) {
        Intent intent = new Intent(this,GameActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("mode",getMode());
        bundle.putBoolean("bgm",isHasBgm());
        bundle.putBoolean("sound",isHasSound());
        intent.putExtra("Bundle",bundle);
        startActivity(intent);
    }

    public void goMain(View view) {
        startActivity(new Intent(this,MainActivity.class));
    }
}
