package kr.ac.tukorea.gamekim2016180014.touchandslice.Main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import kr.ac.tukorea.gamekim2016180014.touchandslice.Common.AudioPlayer;
import kr.ac.tukorea.gamekim2016180014.touchandslice.R;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        AudioPlayer.getInstance().addAudio(this, R.raw.game_start_button);
    }

    public void onGameStartBtn(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
        AudioPlayer.getInstance().playAudio(R.raw.game_start_button);
    }

    public void onExitBtn(View view) {
        System.out.println("Exit button has clicked");
    }
}