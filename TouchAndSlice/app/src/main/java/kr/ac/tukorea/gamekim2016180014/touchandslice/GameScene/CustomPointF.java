package kr.ac.tukorea.gamekim2016180014.touchandslice.GameScene;

import android.graphics.Canvas;
import android.graphics.PointF;

public class CustomPointF implements GameObject {
    private PointF pointF;

    public CustomPointF() {
        pointF = new PointF();
    }

    public void set(float x, float y) {
        if(pointF != null)
            pointF.set(x, y);
    }

    public PointF getPointF() {
        return pointF;
    }

    @Override
    public void update(float elapsed) {

    }

    @Override
    public void draw(Canvas canvas) {

    }
}
