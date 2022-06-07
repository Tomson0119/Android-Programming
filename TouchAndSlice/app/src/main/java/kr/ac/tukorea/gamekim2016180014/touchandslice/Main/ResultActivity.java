package kr.ac.tukorea.gamekim2016180014.touchandslice.Main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import kr.ac.tukorea.gamekim2016180014.touchandslice.GameScene.GameScene;
import kr.ac.tukorea.gamekim2016180014.touchandslice.R;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Bundle extras = getIntent().getExtras();
        int score = extras.getInt("score");
        float totalSec = extras.getFloat("time");

        TextView scoreText = findViewById(R.id.resultScoreTextId);
        scoreText.setText("Score: " + score);

        int min = (int)(totalSec / 60);
        int sec = (int)(totalSec - min * 60);

        TextView timeText = findViewById(R.id.resultTimeTextId);
        timeText.setText("Time: " + min + "min " + sec + "sec");
    }

    public void onRestartBtnClick(View view) {
        finish();
    }
}