package kr.ac.tukorea.gamekim2016180014.dragonflight.framework;

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

import kr.ac.tukorea.gamekim2016180014.dragonflight.game.MainGame;

public class GameView extends View implements Choreographer.FrameCallback {
    public static GameView view;
    private static final String TAG = GameView.class.getSimpleName();

    private Paint fpsPaint = new Paint();
    private long lastTimeNanos;
    private int framesPerSecond;
    private boolean initialized;
    private boolean running;

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        view = this;
    }

    @Override
    protected void onSizeChanged(int w, int h, int old_w, int old_h) {
        super.onSizeChanged(w, h, old_w, old_h);
        Metrics.width = w;
        Metrics.height = h;

        if (!initialized) {
            initView();
            initialized = true;
            running = true;
            Choreographer.getInstance().postFrameCallback(this);
        }
    }

    @Override
    public void doFrame(long currentTimeNanos) {
        if(running == false) {
            Log.d(TAG, "Hello");
            return;
        }
        long now = currentTimeNanos;
        int elapsed = (int) (now - lastTimeNanos);
        if (elapsed != 0) {
            framesPerSecond = 1_000_000_000 / elapsed;
            lastTimeNanos = now;
            MainGame game = MainGame.getInstance();
            game.update(elapsed);
            invalidate();
        }
        Choreographer.getInstance().postFrameCallback(this);
    }

    private void initView() {
        MainGame.getInstance().init();
        fpsPaint.setColor(Color.BLUE);
        fpsPaint.setTextSize(100);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return MainGame.getInstance().onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        MainGame.getInstance().draw(canvas);

        canvas.drawText("FPS:" + framesPerSecond, 10, 100, fpsPaint);
        canvas.drawText(String.valueOf(MainGame.getInstance().getObjectCount()), 10, 200, fpsPaint);
    }

    public void pauseGame() {
        running = false;
    }

    public void resumeGame() {
        if(initialized && running == false) {
            running = true;
            Choreographer.getInstance().postFrameCallback(this);
            Log.d(TAG, "Resume");
        }
    }
}
