package kr.ac.tukorea.gamekim2016180014.touchandslice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Choreographer;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ScreenView extends View implements Choreographer.FrameCallback {
    public static ScreenView view;

    private float elapsedSec;
    private long prevNs;
    private boolean initialized;
    private Paint fpsPaint;

    public ScreenView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        view = this;
        fpsPaint = new Paint();
        fpsPaint.setColor(Color.RED);
        fpsPaint.setTextSize(100);
    }

    @Override
    protected void onSizeChanged(int w, int h, int old_w, int old_h) {
        super.onSizeChanged(w, h, old_w, old_h);
        Metrics.setResolution(w, h);

        if(initialized == false) {
            initialized = true;
            GameScene.getInstance().init();
            Choreographer.getInstance().postFrameCallback(this);
        }
    }

    @Override
    public void doFrame(long currNs) {
        calculateTimeStep(currNs);
        update();
        invalidate();
        Choreographer.getInstance().postFrameCallback(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return GameScene.getInstance().onTouchEvent(event);
    }

    private void calculateTimeStep(long currNs) {
        elapsedSec = (float)(currNs - prevNs) / 1_000_000_000;
        prevNs = currNs;
    }

    private void update() {
        GameScene.getInstance().update(elapsedSec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        GameScene.getInstance().draw(canvas);
        canvas.drawText(String.valueOf((int)(1/elapsedSec)), 10, 100, fpsPaint);
    }
}
