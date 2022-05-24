package kr.ac.tukorea.gamekim2016180014.touchandslice.Main;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import kr.ac.tukorea.gamekim2016180014.touchandslice.Common.AudioPlayer;
import kr.ac.tukorea.gamekim2016180014.touchandslice.GameScene.ScreenView;
import kr.ac.tukorea.gamekim2016180014.touchandslice.R;

public class GameActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        AudioPlayer.getInstance().addAudio(this, "ObjSlice", R.raw.normal_obj_slice);
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