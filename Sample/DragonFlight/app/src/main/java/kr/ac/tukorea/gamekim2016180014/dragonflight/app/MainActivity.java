package kr.ac.tukorea.gamekim2016180014.dragonflight.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import kr.ac.tukorea.gamekim2016180014.dragonflight.R;
import kr.ac.tukorea.gamekim2016180014.dragonflight.framework.GameView;
import kr.ac.tukorea.gamekim2016180014.dragonflight.game.MainGame;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onBtnStart(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }
}