package kr.ac.tukorea.gamekim2016180014.dragonflight.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

import kr.ac.tukorea.gamekim2016180014.dragonflight.R;
import kr.ac.tukorea.gamekim2016180014.dragonflight.framework.BitmapPool;
import kr.ac.tukorea.gamekim2016180014.dragonflight.framework.GameObject;
import kr.ac.tukorea.gamekim2016180014.dragonflight.framework.Metrics;

public class Score implements GameObject {
    private final Bitmap bitmap;
    private final int srcCharWidth, srcCharHeight;
    private final float right, top;
    private final float dstCharWidth, dstCharHeight;
    private Rect srcRect = new Rect();
    private RectF dstRect = new RectF();
    private int score;

    public Score() {
        this.bitmap = BitmapPool.get(R.mipmap.number_24x32);
        this.right = Metrics.width - Metrics.size(R.dimen.score_margin_right);
        this.top = Metrics.size(R.dimen.score_margin_top);
        this.dstCharWidth = Metrics.size(R.dimen.score_digit_width);
        this.srcCharWidth = bitmap.getWidth() / 10;
        this.srcCharHeight = bitmap.getHeight();
        this.dstCharHeight = dstCharWidth * srcCharHeight / srcCharWidth;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void add(int score) {
        this.score += score;
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {
        int value = this.score;
        float x = right;
        while (value > 0) {
            int digit = value % 10;
            srcRect.set(digit * srcCharWidth, 0, (digit + 1) * srcCharWidth, srcCharHeight);
            x -= dstCharWidth;
            dstRect.set(x, top, x + dstCharWidth, top + dstCharHeight);
            canvas.drawBitmap(bitmap, srcRect, dstRect, null);
            value /= 10;
        }
    }

    public int getScore() {
        return score;
    }
}
