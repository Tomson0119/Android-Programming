package kr.ac.tukorea.gamekim2016180014.dragonflight.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;

import kr.ac.tukorea.gamekim2016180014.dragonflight.R;
import kr.ac.tukorea.gamekim2016180014.dragonflight.framework.GameView;
import kr.ac.tukorea.gamekim2016180014.dragonflight.framework.Metrics;
import kr.ac.tukorea.gamekim2016180014.dragonflight.framework.GameObject;
import kr.ac.tukorea.gamekim2016180014.dragonflight.framework.Recyclable;
import kr.ac.tukorea.gamekim2016180014.dragonflight.framework.RecycleBin;

public class MainGame {
    private static final String TAG = MainGame.class.getSimpleName();
    private static MainGame singleton;
    private Paint collidablePaint;
    Score score;

    public static MainGame getInstance() {
        if (singleton == null) {
            singleton = new MainGame();
        }
        return singleton;
    }
    private static final int BALL_COUNT = 10;

    private ArrayList<ArrayList<GameObject>> layers;
    public enum Layer {
        bg1, bullet, enemy, player, bg2, ui, controller, COUNT
    }
    private Fighter fighter;
    public float frameTime;

    public static void clear() {
        singleton = null;
    }

    public void init() {
        Log.d(TAG, "Init");
        initLayers(Layer.COUNT.ordinal());

        add(Layer.controller, new EnemyGenerator());
        add(Layer.controller, new CollisionChecker());

        float fx = Metrics.width / 2;
        float fy = Metrics.height - Metrics.size(R.dimen.fighter_y_offset);
        fighter = new Fighter(fx, fy);
        add(Layer.player, fighter);

        score = new Score();
        add(Layer.ui, score);

        add(Layer.bg1, new VertScrollBackground(R.mipmap.bg_city, Metrics.size(R.dimen.bg_speed)));
        add(Layer.bg2, new VertScrollBackground(R.mipmap.clouds, Metrics.size(R.dimen.clouds_speed)));

        collidablePaint = new Paint();
        collidablePaint.setStyle(Paint.Style.STROKE);
        collidablePaint.setColor(Color.RED);
    }

    private void initLayers(int count) {
        layers = new ArrayList<>();
        for(int i=0;i<count;i++) {
            layers.add(new ArrayList<>());
        }
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
        for (ArrayList<GameObject> gameObjects : layers) {
            for (GameObject obj : gameObjects) {
                obj.draw(canvas);
            }
        }
    }

    public void update(int elapsedNanos) {
        frameTime = (float) (elapsedNanos / 1_000_000_000f);
        for(ArrayList<GameObject> gameObjects : layers) {
            for (GameObject obj : gameObjects) {
                obj.update();
            }
        }
    }

    public ArrayList<GameObject> objectsAt(Layer layer) {
        return layers.get(layer.ordinal());
    }

    public void add(Layer layer, GameObject gameObject) {
        GameView.view.post(new Runnable() {
            @Override
            public void run() {
                ArrayList<GameObject> objects = layers.get(layer.ordinal());
                objects.add(gameObject);
            }
        });
    }

    public void remove(GameObject obj) {
        GameView.view.post(new Runnable() {
            @Override
            public void run() {
                for (ArrayList<GameObject> gameObjects : layers) {
                    boolean removed = gameObjects.remove(obj);
                    if(removed == false) continue;
                    if(gameObjects instanceof Recyclable) {
                        RecycleBin.add((Recyclable)gameObjects);
                    }
                    break;
                }
            }
        });
    }

    public int getObjectCount() {
        int count = 0;
        for(ArrayList<GameObject> objects : layers) {
            count += objects.size();
        }
        return count;
    }
}
