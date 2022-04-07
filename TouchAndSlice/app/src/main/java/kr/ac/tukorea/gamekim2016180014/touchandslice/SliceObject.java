package kr.ac.tukorea.gamekim2016180014.touchandslice;

import android.graphics.Canvas;

public class SliceObject implements GameObject {
    private Image image;
    private float x;
    private float y;

    public SliceObject(float x, float y) {
        image = new Image(R.mipmap.computer);
        this.x = x;
        this.y = y;
    }

    @Override
    public void update(float elapsed) {

    }

    @Override
    public void draw(Canvas canvas) {
        image.draw(canvas);
    }
}
