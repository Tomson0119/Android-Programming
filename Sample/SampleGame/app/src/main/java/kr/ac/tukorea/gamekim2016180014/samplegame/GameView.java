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

    public static View view;
    public static Resources res;

    private static final String TAG = GameView.class.getSimpleName();
    private static final int MAX_BALL = 10;
    private static final int SPEED = 10;

    private ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();
    private Fighter fighter;

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

        Random random = new Random();
        for(int i = 0; i < MAX_BALL; i++) {
            int dx = random.nextInt(10) + 5;
            int dy = random.nextInt(10) + 5;
            gameObjects.add(new Ball(dx,dy,SPEED));
        }
        fighter = new Fighter();
        gameObjects.add(fighter);

        fpsPaint = new Paint();
        fpsPaint.setColor(Color.BLUE);
        fpsPaint.setTextSize(100);
    }

    @Override
    public void doFrame(long currTimeNs) {
        int elapsed = (int)(currTimeNs - prevTimeNs);
        fps = 1_000_000_000 / elapsed;
        prevTimeNs = currTimeNs;
        update();
        invalidate();
        Choreographer.getInstance().postFrameCallback(this);
    }

    private void update() {
        for(GameObject obj : gameObjects) {
            obj.update();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (GameObject obj : gameObjects) {
            obj.draw(canvas);
        }
        canvas.drawText(String.valueOf(fps), 100, 200, fpsPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                float x = event.getX();
                float y = event.getY();
                fighter.setPosition(x, y);
                return true;
        }
        return super.onTouchEvent(event);
    }
}
