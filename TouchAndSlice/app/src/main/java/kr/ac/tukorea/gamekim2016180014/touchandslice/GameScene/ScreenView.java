package kr.ac.tukorea.gamekim2016180014.touchandslice.GameScene;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Choreographer;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import kr.ac.tukorea.gamekim2016180014.touchandslice.Common.Metrics;
import kr.ac.tukorea.gamekim2016180014.touchandslice.R;

public class ScreenView extends View implements Choreographer.FrameCallback {
    public static ScreenView view;
    private static TextView timeTextView;
    private static TextView scoreTextView;
    private static boolean loop;

    private float totalSec;
    private float elapsedSec;
    private long prevNs;
    private boolean initialized;
    private final Paint fpsPaint;

    public ScreenView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        view = this;
        view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        loop = true;

        fpsPaint = new Paint();
        fpsPaint.setColor(Color.RED);
        fpsPaint.setTextSize(100);
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
        canvas.drawText(String.valueOf((int)(1/elapsedSec)), 10, 500, fpsPaint);
    }

    public void onPause() {
        loop = false;
        System.out.println("Paused!");
    }

    public void onResume() {
        if(loop == false) {
            loop = true;
            Choreographer.getInstance().postFrameCallback(this);
            System.out.println("Resumed!");
        }
    }

    public void setScore(int score) {
        scoreTextView.setText(String.valueOf(score));
    }
}
