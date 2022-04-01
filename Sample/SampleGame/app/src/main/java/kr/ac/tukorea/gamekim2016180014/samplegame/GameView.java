package kr.ac.tukorea.gamekim2016180014.samplegame;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
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
    private static final int MAX_BALL = 10;
    private static final int SPEED = 10;

    private ArrayList<Ball> balls = new ArrayList<Ball>();
    private Paint fpsPaint = new Paint();

    private long prevTimeNs = 0;
    private int fps = 0;

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
        Choreographer.getInstance().postFrameCallback(this);
    }

    private void initView() {
        Resources res = getResources();
        Ball.setBitmap(BitmapFactory.decodeResource(res, R.mipmap.soccer_ball));

        Random random = new Random();
        for(int i = 0; i < MAX_BALL; i++) {
            int dx = random.nextInt(10) + 5;
            int dy = random.nextInt(10) + 5;
            balls.add(new Ball(dx,dy,SPEED));
        }

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
        for(Ball ball : balls) {
            ball.updateOffset(this);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (Ball ball : balls) {
            ball.draw(canvas);
        }
        canvas.drawText(String.valueOf(fps), 100, 200, fpsPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            balls.add(new Ball((int)event.getX(), (int)event.getY(), SPEED));
            return true;
        }
        return false;
    }
}
