package kr.ac.tukorea.gamekim2016180014.samplegame;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Choreographer;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;

class Ball {
    public int dx;
    public int dy;
    public Rect dstRect;

    public Ball(int dx, int dy, Rect rect) {
        this.dx = dx;
        this.dy = dy;
        dstRect = rect;
    }

    public void updateOffset(View view) {
        dstRect.offset(dx, dy);
        if (dstRect.left < 0 || dstRect.right > view.getWidth())
            dx *= -1;
        if (dstRect.top < 0 || dstRect.bottom > view.getHeight())
            dy *= -1;
    }
}

public class GameView extends View implements Choreographer.FrameCallback {

    private static final String TAG = GameView.class.getSimpleName();

    private Bitmap soccerBitmap;
    private Rect srcRect;
    private ArrayList<Ball> balls = new ArrayList<Ball>();

    private long prevTimeNs;
    private int fps;

    private Paint fpsPaint;

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
        Choreographer.getInstance().postFrameCallback(this);
    }

    private void initView() {
        Resources res = getResources();
        soccerBitmap = BitmapFactory.decodeResource(res, R.mipmap.soccer_ball);
        srcRect = new Rect(0, 0, soccerBitmap.getWidth(), soccerBitmap.getHeight());
        balls.add(new Ball(10, 10, new Rect(0, 0, 100, 100)));

        prevTimeNs = 0;
        fps = 0;

        fpsPaint = new Paint();
        fpsPaint.setColor(Color.BLUE);
        fpsPaint.setTextSize(100);
    }

    @Override
    public void doFrame(long currTimeNs) {
        long now = currTimeNs;
        int elapsed = (int)(now - prevTimeNs);
        fps = 1_000_000_000 / elapsed;
        prevTimeNs = now;
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
            canvas.drawBitmap(soccerBitmap, srcRect, ball.dstRect, null);
        }
        canvas.drawText(String.valueOf(fps), 100, 200, fpsPaint);
        //Log.d(TAG, "On Draw call!");
    }

    public void onClick() {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            int x = (int) event.getX();
            int y = (int) event.getY();

            balls.add(new Ball(x, y, new Rect(10, 10, 100, 100)));
            return true;
        }
        return false;
    }
}
