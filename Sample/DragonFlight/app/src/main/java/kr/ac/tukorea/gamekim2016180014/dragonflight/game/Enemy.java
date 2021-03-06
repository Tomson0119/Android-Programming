package kr.ac.tukorea.gamekim2016180014.dragonflight.game;

import android.graphics.RectF;
import android.util.Log;

import java.util.ArrayList;

import kr.ac.tukorea.gamekim2016180014.dragonflight.R;
import kr.ac.tukorea.gamekim2016180014.dragonflight.framework.AnimSprite;
import kr.ac.tukorea.gamekim2016180014.dragonflight.framework.BitmapPool;
import kr.ac.tukorea.gamekim2016180014.dragonflight.framework.BoxCollidable;
import kr.ac.tukorea.gamekim2016180014.dragonflight.framework.Metrics;
import kr.ac.tukorea.gamekim2016180014.dragonflight.framework.Recyclable;
import kr.ac.tukorea.gamekim2016180014.dragonflight.framework.RecycleBin;

public class Enemy extends AnimSprite implements BoxCollidable, Recyclable {
    private static final String TAG = Enemy.class.getSimpleName();
    protected static int[] bitmapIds = {
            R.mipmap.enemy_01, R.mipmap.enemy_02,
            R.mipmap.enemy_03, R.mipmap.enemy_04,
            R.mipmap.enemy_05, R.mipmap.enemy_06,
            R.mipmap.enemy_07, R.mipmap.enemy_08,
            R.mipmap.enemy_09, R.mipmap.enemy_10,
            R.mipmap.enemy_11, R.mipmap.enemy_12,
            R.mipmap.enemy_13, R.mipmap.enemy_14,
            R.mipmap.enemy_15, R.mipmap.enemy_16,
            R.mipmap.enemy_17, R.mipmap.enemy_18,
            R.mipmap.enemy_19, R.mipmap.enemy_20,
    };
    public static int MIN_LEVEL = 1;
    public static int MAX_LEVEL = bitmapIds.length;
    public static float size;
    private static float FRAME_PER_SECOND = 10.0f;

    protected int level;
    protected float dy;
    protected RectF boundingBox = new RectF();
    protected float maxLife;
    protected float life;
    protected Gauge gauge;

    public static Enemy get(int level, float x, float speed) {
        Enemy enemy = (Enemy)RecycleBin.get(Enemy.class);
        if(enemy != null) {
            enemy.set(level, x, speed);
            return enemy;
        }
        return new Enemy(level, x, speed);
    }

    private void set(int level, float x, float speed) {
        bitmap = BitmapPool.get(bitmapIds[level-1]);
        this.x = x;
        this.y = -size;
        this.dy = speed;
        this.level = level;
        life = maxLife = level * 10;
    }

    private Enemy(int level, float x, float speed) {
        super(x, -size, R.dimen.enemy_radius, bitmapIds[level-1], FRAME_PER_SECOND, 8);
        this.level = level;
        dy = speed;
        life = maxLife = level * 10;

        Log.d(TAG, "Enemy created");
    }

    @Override
    public void update() {
        super.update();
        float frameTime = MainGame.getInstance().frameTime;
        y += dy * frameTime;
        setDstRectWithRadius();
        if (dstRect.top > Metrics.height) {
            MainGame.getInstance().remove(this);
        }
    }

    @Override
    public RectF getBoundingRect() {
        boundingBox.set(dstRect);
        boundingBox.inset(size/10, size/10);
        return boundingBox;
    }

    @Override
    public void finish() {

    }

    public int getScore() {
        return level*10;
    }

    public boolean decreaseLife(float power) {
        life -= power;
        return (life <= 0);
    }
}
