package kr.ac.tukorea.gamekim2016180014.touchandslice;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Choreographer;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class ScreenView extends View implements Choreographer.FrameCallback {

    private static final String TAG = ScreenView.class.getSimpleName();

    private TouchPath touchPath;

    private float elapsedSec;
    private long prevNs;

    public ScreenView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        touchPath = new TouchPath();
        elapsedSec = 0;
        prevNs = 0;

        Choreographer.getInstance().postFrameCallback(this);
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
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                return true;

            case MotionEvent.ACTION_MOVE:
                float x = event.getX();
                float y = event.getY();
                touchPath.appendPoint(x, y);
                return true;

            case MotionEvent.ACTION_UP:
                touchPath.clearPath();
                return true;
        }
        return super.onTouchEvent(event);
    }

    private void calculateTimeStep(long currNs) {
        elapsedSec = (float)(currNs - prevNs) / 1_000_000_000;
        prevNs = currNs;
    }

    private void update() {
        touchPath.update(elapsedSec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        touchPath.draw(canvas);
    }
}
