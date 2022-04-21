package kr.ac.tukorea.gamekim2016180014.touchandslice.Main;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import kr.ac.tukorea.gamekim2016180014.touchandslice.GameScene.GameScene;
import kr.ac.tukorea.gamekim2016180014.touchandslice.GameScene.ScreenView;
import kr.ac.tukorea.gamekim2016180014.touchandslice.R;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
}