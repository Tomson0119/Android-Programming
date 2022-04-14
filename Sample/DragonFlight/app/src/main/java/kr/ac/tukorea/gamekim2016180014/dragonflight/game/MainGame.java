package kr.ac.tukorea.gamekim2016180014.dragonflight.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;

import java.util.ArrayList;

import kr.ac.tukorea.gamekim2016180014.dragonflight.R;
import kr.ac.tukorea.gamekim2016180014.dragonflight.framework.BoxCollidable;
import kr.ac.tukorea.gamekim2016180014.dragonflight.framework.CollisionHelper;
import kr.ac.tukorea.gamekim2016180014.dragonflight.framework.GameView;
import kr.ac.tukorea.gamekim2016180014.dragonflight.framework.Metrics;
import kr.ac.tukorea.gamekim2016180014.dragonflight.framework.GameObject;

public class MainGame {
    private static MainGame singleton;
    private Paint collidablePaint;

    public static MainGame getInstance() {
        if (singleton == null) {
            singleton = new MainGame();
        }
        return singleton;
    }
    private static final int BALL_COUNT = 10;
    private ArrayList<GameObject> gameObjects = new ArrayList<>();
    private Fighter fighter;
    public float frameTime;

    public static void clear() {
        singleton = null;
    }

    public void init() {
        gameObjects.clear();
        gameObjects.add(new EnemyGenerator());
        float fx = Metrics.width / 2;
        float fy = Metrics.height - Metrics.size(R.dimen.fighter_y_offset);
        fighter = new Fighter(fx, fy);
        gameObjects.add(fighter);

        collidablePaint = new Paint();
        collidablePaint.setStyle(Paint.Style.STROKE);
        collidablePaint.setColor(Color.RED);
    }

    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                float x = event.getX();
                float y = event.getY();
                fighter.setTargetPosition(x, y);
                return true;
        }
        return false;
    }

    public void draw(Canvas canvas) {
        for (GameObject obj : gameObjects) {
            obj.draw(canvas);
            if(obj instanceof BoxCollidable) {
                RectF box = ((BoxCollidable)obj).getBoundingRect();
                canvas.drawRect(box, collidablePaint);
            }
        }
    }

    public void update(int elapsedNanos) {
        frameTime = (float) (elapsedNanos / 1_000_000_000f);
        for (GameObject obj : gameObjects) {
            obj.update();
        }
        checkCollision();
    }

    private void checkCollision() {
        for(GameObject obj : gameObjects) {
            if((obj instanceof Enemy) == false) {
                continue;
            }
            Enemy enemy = (Enemy)obj;

            boolean removed = false;
            for(GameObject otherObj : gameObjects) {
                if((otherObj instanceof Bullet) == false) {
                    continue;
                }
                Bullet bullet = (Bullet)otherObj;
                if(CollisionHelper.collides(enemy, bullet)) {
                    remove(bullet);
                    remove(enemy);
                    removed = true;
                    return;
                }
            }
            if(removed) {
                continue;
            }
        }
    }

    public void add(GameObject gameObject) {
        GameView.view.post(new Runnable() {
            @Override
            public void run() {
                gameObjects.add(gameObject);
            }
        });
    }

    public void remove(GameObject obj) {
        GameView.view.post(new Runnable() {
            @Override
            public void run() {
                gameObjects.remove(obj);
            }
        });
    }

    public int getObjectCount() {
        return gameObjects.size();
    }
}
