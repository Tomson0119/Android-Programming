package kr.ac.tukorea.gamekim2016180014.dragonflight.game;

import android.graphics.RectF;

import kr.ac.tukorea.gamekim2016180014.dragonflight.R;
import kr.ac.tukorea.gamekim2016180014.dragonflight.framework.BoxCollidable;
import kr.ac.tukorea.gamekim2016180014.dragonflight.framework.Metrics;
import kr.ac.tukorea.gamekim2016180014.dragonflight.framework.Sprite;

public class Enemy extends Sprite implements BoxCollidable {
    public static float size;
    protected float dy;
    protected RectF boundingBox = new RectF();

    public Enemy(float x, float speed) {
        super(x, -size, size, size, R.mipmap.f_01_01);
        dy = speed;
    }

    @Override
    public void update() {
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
}
