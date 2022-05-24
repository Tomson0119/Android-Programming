package kr.ac.tukorea.gamekim2016180014.touchandslice.GameScene;

import android.graphics.Canvas;
import android.graphics.PointF;
import android.view.MotionEvent;

import androidx.annotation.NonNull;

import java.util.LinkedList;

import kr.ac.tukorea.gamekim2016180014.touchandslice.Common.AudioPlayer;
import kr.ac.tukorea.gamekim2016180014.touchandslice.Common.BitmapPool;
import kr.ac.tukorea.gamekim2016180014.touchandslice.Common.Metrics;
import kr.ac.tukorea.gamekim2016180014.touchandslice.Common.ObjectPool;
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
    private int scoreIncrement;
    private int score;

    private static final int MAX_FAIL = 3;
    private Image[] failImages;

    private int failCount;

    public void init() {
        gameObjects = new LinkedList<>();
        touchPath = new TouchPath();
        gameObjects.add(touchPath);
        gameObjects.add(new ObjectGenerator());

        scoreIncrement = Metrics.intValue(R.integer.score_increment);
        score = 0;

        float img_len = Metrics.getSize(R.dimen.x_image_len);
        float img_x = Metrics.width - MAX_FAIL * img_len + img_len / 2;
        float img_y = Metrics.getSize(R.dimen.x_image_y);
        failImages = new Image[MAX_FAIL];
        for(int i = 0; i < failImages.length; i++) {
            failImages[i] = new Image(R.mipmap.x_white, img_len, img_len);
            failImages[i].move(img_x + i * img_len, img_y);
        }
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
            float slope = touchPath.getSlope();
            sliceObj.slice(slope);
        }
    }

    public void draw(Canvas canvas) {
        for(Image img : failImages) {
            img.draw(canvas);
        }
        for(GameObject obj : gameObjects) {
            obj.draw(canvas);
        }
    }

    public void increaseScore() {
        score += scoreIncrement;
        ScreenView.view.setScore(score);
    }

    public void increaseFailCount() {
        if(failCount < MAX_FAIL) {
            failCount += 1;
            for(int i=0;i<failCount;i++) {
                int idx = failImages.length - 1 - i;
                failImages[idx].setImage(R.mipmap.x_red);
            }
        }
    }

    public void GameOver() {

    }

    public int getScore() {
        return score;
    }
}
