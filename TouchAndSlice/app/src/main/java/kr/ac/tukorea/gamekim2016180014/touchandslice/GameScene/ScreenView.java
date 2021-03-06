package kr.ac.tukorea.gamekim2016180014.touchandslice.GameScene;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Choreographer;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import kr.ac.tukorea.gamekim2016180014.touchandslice.Common.Metrics;
import kr.ac.tukorea.gamekim2016180014.touchandslice.Main.GameActivity;
import kr.ac.tukorea.gamekim2016180014.touchandslice.Main.ResultActivity;
import kr.ac.tukorea.gamekim2016180014.touchandslice.R;

public class ScreenView extends View implements Choreographer.FrameCallback {
    private static String TAG = ScreenView.class.getSimpleName();
    public static ScreenView view;

    private static TextView timeTextView;
    private static TextView scoreTextView;

    private static boolean loop;

    private float totalSec;
    private float elapsedSec;
    private long prevNs;
    private boolean initialized;

    private int currentScore;

    public ScreenView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        view = this;
        view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        loop = true;
    }

    @Override
    protected void onSizeChanged(int w, int h, int old_w, int old_h) {
        super.onSizeChanged(w, h, old_w, old_h);
        Metrics.setResolution(w, h);

        if(!initialized) {
            initialized = true;
            FrameLayout parent = (FrameLayout)getParent();

            timeTextView = (TextView)parent.findViewById(R.id.timeTextId);
            scoreTextView = (TextView)parent.findViewById(R.id.scoreTextId);

            GameScene.getInstance().init();
            Choreographer.getInstance().postFrameCallback(this);
        }
    }

    @Override
    public void doFrame(long currNs) {
        if(loop) {
            calculateTimeStep(currNs);
            update();
            invalidate();
            Choreographer.getInstance().postFrameCallback(this);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return GameScene.getInstance().onTouchEvent(event);
    }

    private void calculateTimeStep(long currNs) {
        if(elapsedSec < 0.0f) {
            prevNs = currNs;
        }
        elapsedSec = (float)(currNs - prevNs) / 1_000_000_000;
        if(prevNs == 0) elapsedSec = 0.0f;
        prevNs = currNs;

        totalSec += elapsedSec;
        updateTimeText();
    }

    private void updateTimeText() {
        int minute = (int)(totalSec / 60);
        int second = (int)(totalSec - minute * 60);

        String minStr = String.valueOf(minute);
        if(minute < 10) minStr = "0" + minStr;

        String secStr = String.valueOf(second);
        if(second < 10) secStr = "0" + secStr;

        timeTextView.setText(minStr + ":" + secStr);
    }

    private void update() {
        GameScene.getInstance().update(elapsedSec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        GameScene.getInstance().draw(canvas);
    }

    public void onPause() {
        loop = false;
        elapsedSec = -1.0f;
    }

    public void onResume() {
        if(loop == false) {
            loop = true;
            Choreographer.getInstance().postFrameCallback(this);
        }
    }

    public void increaseScore(int increment) {
        currentScore += increment;
        scoreTextView.setText(String.valueOf(currentScore));
    }

    public int getScore() {
        return currentScore;
    }

    public void loadResultActivity() {
        GameActivity activity = (GameActivity)getContext();
        activity.saveBestScore();

        Intent intent = new Intent(activity, ResultActivity.class);
        intent.putExtra("time", totalSec);
        intent.putExtra("score", currentScore);

        reset();

        activity.startActivity(intent);
    }

    public void reset() {
        currentScore = 0;
        scoreTextView.setText(String.valueOf(currentScore));
        totalSec = 0;
        elapsedSec = 0;
        prevNs = 0;
    }
}
