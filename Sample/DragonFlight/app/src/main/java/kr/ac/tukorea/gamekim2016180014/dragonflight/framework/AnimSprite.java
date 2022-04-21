package kr.ac.tukorea.gamekim2016180014.dragonflight.framework;

import android.graphics.Canvas;
import android.graphics.Rect;

import kr.ac.tukorea.gamekim2016180014.dragonflight.game.MainGame;

public class AnimSprite extends Sprite {

    private final float framePerSecond;
    private final int frameCount;
    private int imageWidth;
    private int imageHeight;
    private long timeCreatedOn;

    private Rect srcRect;

    public AnimSprite(float x, float y, int radiusDimenResId, int bitmapResId, float fps, int frameCount) {
        super(x, y, radiusDimenResId, bitmapResId);
        imageWidth = bitmap.getWidth();
        imageHeight = bitmap.getHeight();
        this.framePerSecond = fps;

        if(frameCount == 0) {
            frameCount = imageWidth / imageHeight;
            imageWidth = imageHeight;
        } else {
            imageWidth = imageWidth / frameCount;
        }
        this.frameCount = frameCount;

        srcRect = new Rect();
        timeCreatedOn = System.currentTimeMillis();
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {
        float time = (System.currentTimeMillis() - timeCreatedOn) / 1000.0f;
        int index = Math.round(time*framePerSecond) % frameCount;
        srcRect.set(index * imageWidth, 0, (index + 1) * imageWidth, imageHeight);
        canvas.drawBitmap(bitmap, srcRect, dstRect, null);
    }
}
