package kr.ac.tukorea.gamekim2016180014.dragonflight.game;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

import java.util.ArrayList;

import kr.ac.tukorea.gamekim2016180014.dragonflight.framework.BoxCollidable;
import kr.ac.tukorea.gamekim2016180014.dragonflight.framework.GameObject;
import kr.ac.tukorea.gamekim2016180014.dragonflight.framework.Metrics;
import kr.ac.tukorea.gamekim2016180014.dragonflight.R;
import kr.ac.tukorea.gamekim2016180014.dragonflight.framework.Recyclable;
import kr.ac.tukorea.gamekim2016180014.dragonflight.framework.RecycleBin;

public class Bullet implements GameObject, BoxCollidable, Recyclable {

    protected float x, y;
    protected final float length;
    protected final float speed;
    protected static Paint paint;
    protected static float laserWidth;
    protected RectF boundingBox;

    private float power;

    public static Bullet get(float x, float y, float power) {
        Bullet bullet = (Bullet)RecycleBin.get(Bullet.class);
        if(bullet != null) {
            bullet.set(x, y, power);
            return bullet;
        }
        return new Bullet(x, y, power);
    }

    private void set(float x, float y, float power) {
        this.x = x;
        this.y = y;
        this.power = power;
    }

    private Bullet(float x, float y, float power) {
        this.x = x;
        this.y = y;
        this.power = power;
        length = Metrics.size(R.dimen.laser_length);
        speed = Metrics.size(R.dimen.laser_speed);
        boundingBox = new RectF();

        if (paint == null) {
            paint = new Paint();
            paint.setColor(Color.RED);
            laserWidth = Metrics.size(R.dimen.laser_width);
            paint.setStrokeWidth(laserWidth);
        }
        Log.d(TAG, "Bullet created");
    }

    @Override
    public void update() {
        MainGame game = MainGame.getInstance();
        float frameTime = game.frameTime;
        y -= speed * frameTime;
        float hw = laserWidth / 2;
        boundingBox.set(x - hw, y, x + hw, y + length);

        if(y < 0.0f) {
            game.remove(this);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawLine(x, y, x, y + length, paint);
    }

    @Override
    public RectF getBoundingRect() {
        return boundingBox;
    }

    @Override
    public void finish() {

    }

    public float getPower() {
        return power;
    }
}
