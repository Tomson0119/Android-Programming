package kr.ac.tukorea.gamekim2016180014.samplegame;

import android.graphics.Canvas;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.Random;

public class MainGame {
    private static MainGame instance = null;
    public static MainGame GetInstance() {
        if(instance == null) {
            instance = new MainGame();
        }
        return instance;
    }

    private static final int SPEED = 10;
    private static final int MAX_BALL = 10;

    private ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();
    private Fighter fighter;

    public float frameTime;

    public void init() {
        Random random = new Random();
        for(int i = 0; i < MAX_BALL; i++) {
            int dx = random.nextInt(10) + 5;
            int dy = random.nextInt(10) + 5;
            gameObjects.add(new Ball(dx,dy,SPEED));
        }

        float fx = Metrics.width / 2;
        float fy = Metrics.height / 2;

        fighter = new Fighter(fx, fy);
        gameObjects.add(fighter);
    }

    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                float x = event.getX();
                float y = event.getY();
                fighter.setTargetPosition(x, y);
                return true;
        }
        return false;
    }

    public void update(int elapsed) {
        frameTime = (elapsed / 1_000_000_000f);

        for(GameObject obj : gameObjects) {
            obj.update();
        }
    }

    public void draw(Canvas canvas) {
        for(GameObject obj : gameObjects) {
            obj.draw(canvas);
        }
    }
}
