package kr.ac.tukorea.gamekim2016180014.touchandslice;

import android.graphics.Canvas;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.LinkedList;

public class GameScene {
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
        gameObjects.add(new SliceObject(
                Metrics.width / 2,
                Metrics.getSize(R.dimen.image_y),
                R.mipmap.hamburger));
        touchPath = new TouchPath();
        gameObjects.add(touchPath);
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
            processCollision(obj);
        }
    }

    private void processCollision(GameObject obj) {
        if(touchPath == obj) return;

        if(touchPath.isCollided((SliceObject)obj)) {
            System.out.println("Hey");
        }
    }

    public void draw(Canvas canvas) {
        for(GameObject obj : gameObjects) {
            obj.draw(canvas);
        }
    }
}
