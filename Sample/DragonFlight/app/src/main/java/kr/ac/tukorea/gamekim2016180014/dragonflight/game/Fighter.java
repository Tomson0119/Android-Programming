package kr.ac.tukorea.gamekim2016180014.dragonflight.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

import kr.ac.tukorea.gamekim2016180014.dragonflight.framework.Metrics;
import kr.ac.tukorea.gamekim2016180014.dragonflight.R;
import kr.ac.tukorea.gamekim2016180014.dragonflight.framework.Sprite;

public class Fighter extends Sprite {
    private static final String TAG = Fighter.class.getSimpleName();
    private RectF targetRect = new RectF();

    private float dx;
    private float tx, ty;
    private float elapsedTimeForFire;
    private float fireInterval;

    private static Bitmap targetBitmap;

    public Fighter(float x, float y) {
        super(x, y, R.dimen.fighter_radius, R.mipmap.plane);
        setTargetPosition(x, y);
        fireInterval = Metrics.floatValue(R.dimen.fire_interval);
    }

    public void update() {
        float frameTime = MainGame.getInstance().frameTime;
        elapsedTimeForFire += frameTime;
        if(elapsedTimeForFire >= fireInterval) {
            fire();
            elapsedTimeForFire -= fireInterval;
        }

        if (dx == 0)
            return;

        float d = dx * MainGame.getInstance().frameTime;
        if ((d > 0 && x + d > tx) || (d < 0 && x + d < tx)) {
            d = tx - x;
            x = tx;
        } else {
            x += d;
        }
        dstRect.offset(d, 0.0f);
    }

    public void setTargetPosition(float tx, float ty) {
        this.tx = tx;
        this.ty = ty;
        targetRect.set(tx - radius / 2, ty - radius / 2,
                tx + radius / 2, ty + radius / 2);

        dx = Metrics.size(R.dimen.fighter_speed);
        if (tx < x) {
            dx *= -1.0f;
        }
    }

    public void fire() {
        Bullet bullet = Bullet.get(x, y);
        MainGame.getInstance().add(MainGame.Layer.bullet, bullet);
    }
}
