package kr.ac.tukorea.gamekim2016180014.touchandslice;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;

public class SliceObject implements GameObject {
    private final Image image;
    private float x;
    private float y;

    private float rotSpeed;

    public SliceObject(float x, float y, int imageId) {
        image = new Image(imageId);
        this.x = x;
        this.y = y;
        image.move(x, y);
    }

    public void setRotationSpeed(float speed) {
        image.setRotation(speed);
    }

    @Override
    public void update(float elapsed) {
        image.update(elapsed);
    }

    @Override
    public void draw(Canvas canvas) {
        image.draw(canvas);
    }

    public RectF getRect() {
        return image.getRect();
    }
}
