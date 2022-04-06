package kr.ac.tukorea.gamekim2016180014.samplegame;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.Choreographer;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Random;

public class GameView extends View implements Choreographer.FrameCallback {

    private static final String TAG = GameView.class.getSimpleName();

    public static View view;
    public static Resources res;

    private Paint fpsPaint;
    private long prevTimeNs = 0;
    private int fps = 0;

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
        Choreographer.getInstance().postFrameCallback(this);
    }

    private void initView() {
        view = this;
        res = getResources();

        MainGame.GetInstance().init();

        fpsPaint = new Paint();
        fpsPaint.setColor(Color.BLUE);
        fpsPaint.setTextSize(100);
    }

    @Override
    public void doFrame(long currTimeNs) {
        int elapsed = (int)(currTimeNs - prevTimeNs);
        fps = 1_000_000_000 / elapsed;
        prevTimeNs = currTimeNs;
        MainGame.GetInstance().update();
        invalidate();
        Choreographer.getInstance().postFrameCallback(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        MainGame.GetInstance().draw(canvas);
        canvas.drawText(String.valueOf(fps), 100, 200, fpsPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return MainGame.GetInstance().onTouchEvent(event);
    }
}
