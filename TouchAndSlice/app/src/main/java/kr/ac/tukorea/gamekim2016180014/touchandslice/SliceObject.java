package kr.ac.tukorea.gamekim2016180014.touchandslice;

import android.graphics.Canvas;
import android.graphics.RectF;

public class SliceObject implements GameObject {
    private Image image;
    private float x;
    private float y;

    public SliceObject(float x, float y, int imageId) {
        image = new Image(imageId);
        this.x = x;
        this.y = y;
        image.move(x, y);
    }

    @Override
    public void update(float elapsed) {

    }

    @Override
    public void draw(Canvas canvas) {
        image.draw(canvas);
    }

    public RectF getRect() {
        return image.getRect();
    }
}
