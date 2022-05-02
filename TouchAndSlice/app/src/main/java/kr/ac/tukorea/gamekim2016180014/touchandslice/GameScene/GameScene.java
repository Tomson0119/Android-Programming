package kr.ac.tukorea.gamekim2016180014.touchandslice.GameScene;

import android.app.slice.Slice;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.media.SoundPool;
import android.util.Log;
import android.view.MotionEvent;

import androidx.annotation.NonNull;

import java.util.LinkedList;

import kr.ac.tukorea.gamekim2016180014.touchandslice.Common.AudioPlayer;
import kr.ac.tukorea.gamekim2016180014.touchandslice.Common.Metrics;
import kr.ac.tukorea.gamekim2016180014.touchandslice.R;

public class GameScene {
    private static final String TAG = GameScene.class.getSimpleName();
    private static GameScene instance;

    public static GameScene getInstance() {
        if(instance == null) {
            instance = new GameScene();
        }
        return instance;
    }

    private TouchPath touchPath;
    private LinkedList<GameObject> gameObjects;

    public void init() {
        gameObjects = new LinkedList<>();
        touchPath = new TouchPath();
        gameObjects.add(touchPath);
        gameObjects.add(new ObjectGenerator());
    }

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
        return false;
    }

    public void update(float elapsed) {
        for(GameObject obj : gameObjects) {
            obj.update(elapsed);
            isOutOfScreen(obj);
            processCollision(obj);
        }
    }

    private void isOutOfScreen(GameObject obj) {
        if(obj instanceof SliceObject) {
            SliceObject sliceObj = (SliceObject)obj;
            PointF p = sliceObj.getCenter();
            if(p.y >= sliceObj.getRect().height() / 2 + Metrics.height) {
                RemoveObject(obj);
            }
        }
    }

    public void addObject(@NonNull GameObject obj) {
        ScreenView.view.post(new Runnable() {
            @Override
            public void run() {
                gameObjects.add(obj);
            }
        });
    }

    private void RemoveObject(@NonNull GameObject obj) {
        ScreenView.view.post(new Runnable() {
            @Override
            public void run() {
                ObjectPool.getInstance().retrieve(obj);
                gameObjects.remove(obj);
            }
        });
    }

    private void processCollision(GameObject obj) {
        if(!(obj instanceof SliceObject)) return;

        SliceObject sliceObj = (SliceObject)obj;
        if(touchPath.isCollidedWith(sliceObj)) {
            AudioPlayer.getInstance().playAudio("ObjSlice");
            float slope = touchPath.getSlope();
            sliceObj.slice(slope);
        }
    }

    public void draw(Canvas canvas) {
        for(GameObject obj : gameObjects) {
            obj.draw(canvas);
        }
    }
}
