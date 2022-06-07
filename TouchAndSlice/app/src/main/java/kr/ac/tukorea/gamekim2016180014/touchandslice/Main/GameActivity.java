package kr.ac.tukorea.gamekim2016180014.touchandslice.Main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.TextView;

import kr.ac.tukorea.gamekim2016180014.touchandslice.Common.AudioPlayer;
import kr.ac.tukorea.gamekim2016180014.touchandslice.GameScene.GameScene;
import kr.ac.tukorea.gamekim2016180014.touchandslice.GameScene.ScreenView;
import kr.ac.tukorea.gamekim2016180014.touchandslice.R;

public class GameActivity extends AppCompatActivity {
    private static String TAG = GameActivity.class.getSimpleName();
    private int bestScore;
    private boolean paused;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        paused = false;

        AudioPlayer.getInstance().addAudio(this, R.raw.normal_obj_spawn);
        AudioPlayer.getInstance().addAudio(this, R.raw.normal_obj_slice);
        AudioPlayer.getInstance().addAudio(this, R.raw.bad_obj_spawn);
        AudioPlayer.getInstance().addAudio(this, R.raw.trap_obj_slice);
        AudioPlayer.getInstance().addAudio(this, R.raw.killer_obj_slice);
        AudioPlayer.getInstance().addAudio(this, R.raw.game_over);

        loadBestScore();
    }

    @Override
    protected void onPause() {
        ScreenView.view.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ScreenView.view.onResume();
    }

    @Override
    protected void onDestroy() {
        ScreenView.view = null;
        super.onDestroy();
    }

    public void onResumeOrPauseBtn(View view) {
        ImageButton btn = (ImageButton)view;
        if(!paused) {
            btn.setImageResource(R.mipmap.play_button);
            onPause();
            paused = true;
        } else {
            btn.setImageResource(R.mipmap.pause_button);
            paused = false;
            onResume();
        }
    }

    public void loadBestScore() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        bestScore = prefs.getInt("bestScore", 0);
        TextView bestScoreView = (TextView)findViewById(R.id.bestScoreTextId);
        bestScoreView.setText("Best: " + String.valueOf(bestScore));
    }

    public void saveBestScore() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        int currScore = ScreenView.view.getScore();
        bestScore = (currScore > bestScore) ? currScore : bestScore;
        TextView bestScoreView = (TextView)findViewById(R.id.bestScoreTextId);
        bestScoreView.setText("Best: " + String.valueOf(bestScore));
        prefs.edit().putInt("bestScore", bestScore).apply();
    }

}