package kr.ac.tukorea.gamekim2016180014.touchandslice.GameScene;

import android.app.slice.Slice;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.Log;

import kr.ac.tukorea.gamekim2016180014.touchandslice.Common.Helper;
import kr.ac.tukorea.gamekim2016180014.touchandslice.Common.Metrics;
import kr.ac.tukorea.gamekim2016180014.touchandslice.R;

public class SliceObject implements GameObject {
    private static final String TAG = SliceObject.class.getSimpleName();
    private final Image[] images;

    private final PointF center;
    private final PointF[] slicePoints;

    private boolean isSliced;
    private float vx0;
    private float vy0;
    private float dt;
    private double theta;

    public SliceObject() {
        images = new Image[3];
        for(int i=0;i<3;i++) {
            images[i] = new Image();
        }
        center = new PointF();
        slicePoints = new PointF[2];
        for(int i=0;i<2;i++) {
            slicePoints[i] = new PointF();
        }
        Log.d(TAG, "slice object created!");
    }

    public void init(float x, float y, int imageId) {
        float width = Metrics.getSize(R.dimen.image_width);
        float height = Metrics.getSize(R.dimen.image_height);
        for(int i=0;i<3;i++) {
            images[i].init(imageId, width, height);
            images[i].move(x, y);
        }
        center.set(x, y);
        isSliced = false;
    }

    public void setInitialVel(float x, float y) {
        this.vx0 = x;
        this.vy0 = y;
        this.theta = Math.atan2(y, x);
        this.dt = 0.0f;
    }

    @Override
    public void update(float elapsed) {
        if(isSliced) {
            images[1].move(+elapsed * 10.0f, 0.0f);
            images[2].move(-elapsed * 10.0f, 0.0f);
        }
        else {
            float dx = vx0 * (float)Math.cos(theta);
            float dy = -vy0 * (float)Math.sin(theta) + Metrics.floatValue(R.dimen.gravity) * dt;
            dt += elapsed;

            for(Image img : images) {
                img.move(dx, dy);
            }
            center.offset(dx, dy);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        if(isSliced == false) {
            images[0].draw(canvas);
        } else {
            images[1].draw(canvas);
            images[2].draw(canvas);
        }
    }

    public RectF getRect() {
        return images[0].getRect();
    }

    public void slice(float slope) {
        isSliced = true;
        setSlicePoints(slope);
    }

    private void setSlicePoints(float slope) {
        float offset_w = images[0].getRectWidth() / 2;
        float offset_h = images[0].getRectHeight() / 2;

        if(slope < Float.MAX_VALUE) {
            if(Math.abs(slope) <= 1.0f) {
                slicePoints[0].set(center.x - offset_w, center.y - offset_w * slope);
                slicePoints[1].set(center.x + offset_w, center.y + offset_w * slope);
                divideHorizontal();
            } else {
                slicePoints[0].set(center.x - offset_h / slope, center.y - offset_h);
                slicePoints[1].set(center.x + offset_h / slope, center.y + offset_h);
                divideVertical();
            }
        } else {
            slicePoints[0].set(center.x, center.y - offset_h);
            slicePoints[1].set(center.x, center.y + offset_h);
            divideVertical();
        }
    }

    private void divideHorizontal() {
        RectF origin = images[0].getRect();

        images[1].setPoint(0, Helper.deepCopyPointF(slicePoints[0]));
        images[1].setPoint(1, Helper.deepCopyPointF(slicePoints[1]));
        images[1].setPoint(2, new PointF(origin.right, origin.bottom));
        images[1].setPoint(3, new PointF(origin.left, origin.bottom));

        images[2].setPoint(0, new PointF(origin.left, origin.top));
        images[2].setPoint(1, new PointF(origin.right, origin.top));
        images[2].setPoint(2, Helper.deepCopyPointF(slicePoints[1]));
        images[2].setPoint(3, Helper.deepCopyPointF(slicePoints[0]));
    }

    private void divideVertical() {
        RectF origin = images[0].getRect();

        images[1].setPoint(0, Helper.deepCopyPointF(slicePoints[0]));
        images[1].setPoint(1, new PointF(origin.right, origin.top));
        images[1].setPoint(2, new PointF(origin.right, origin.bottom));
        images[1].setPoint(3, Helper.deepCopyPointF(slicePoints[1]));

        images[2].setPoint(0, new PointF(origin.left, origin.top));
        images[2].setPoint(1, Helper.deepCopyPointF(slicePoints[0]));
        images[2].setPoint(2, Helper.deepCopyPointF(slicePoints[1]));
        images[2].setPoint(3, new PointF(origin.left, origin.bottom));
    }

    public boolean isSliced() {
        return isSliced;
    }
    public PointF getCenter() { return center; }
}
