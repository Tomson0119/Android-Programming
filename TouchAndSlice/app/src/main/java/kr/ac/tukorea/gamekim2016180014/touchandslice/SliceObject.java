package kr.ac.tukorea.gamekim2016180014.touchandslice;

import android.app.slice.Slice;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.Shape;

public class SliceObject implements GameObject {
    private final Image baseImage;
    private final Image childImage1;
    private final Image childImage2;

    private final PointF center;
    private final PointF[] slicePoints;
    private final Paint slicePaint;

    private boolean isSliced;

    public SliceObject(float x, float y, int imageId) {
        float width = Metrics.getSize(R.dimen.image_width);
        float height = Metrics.getSize(R.dimen.image_height);

        baseImage = new Image(imageId, width, height);
        childImage1 = new Image(imageId, width, height);
        childImage2 = new Image(imageId, width, height);
        baseImage.move(x, y);
        childImage1.move(x, y);
        childImage2.move(x, y);

        center = new PointF(x, y);

        slicePoints = new PointF[2];
        slicePoints[0] = new PointF();
        slicePoints[1] = new PointF();
        slicePaint = new Paint();
        slicePaint.setColor(Color.BLUE);
        slicePaint.setStrokeWidth(10);

        isSliced = false;
    }

    @Override
    public void update(float elapsed) {
        if(isSliced) {
            childImage1.move(+elapsed * 10.0f, 0.0f);
            childImage2.move(-elapsed * 10.0f, 0.0f);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        if(isSliced == false) {
            baseImage.draw(canvas);
        } else {
            childImage1.draw(canvas);
            childImage2.draw(canvas);
        }
    }

    public RectF getRect() {
        return baseImage.getRect();
    }

    public void slice(float slope) {
        isSliced = true;
        setSlicePoints(slope);
    }

    private void setSlicePoints(float slope) {
        float offset_w = baseImage.getRectWidth() / 2;
        float offset_h = baseImage.getRectHeight() / 2;

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
        RectF origin = baseImage.getRect();

        childImage1.setPoint(0, Helper.deepCopyPointF(slicePoints[0]));
        childImage1.setPoint(1, Helper.deepCopyPointF(slicePoints[1]));
        childImage1.setPoint(2, new PointF(origin.right, origin.bottom));
        childImage1.setPoint(3, new PointF(origin.left, origin.bottom));
        //childImage1.move(0, -baseImage.getRectHeight() / 2);

        childImage2.setPoint(0, new PointF(origin.left, origin.top));
        childImage2.setPoint(1, new PointF(origin.right, origin.top));
        childImage2.setPoint(2, Helper.deepCopyPointF(slicePoints[1]));
        childImage2.setPoint(3, Helper.deepCopyPointF(slicePoints[0]));
        //childImage2.move(0, baseImage.getRectHeight() / 2);
    }

    private void divideVertical() {
        RectF origin = baseImage.getRect();

        childImage1.setPoint(0, Helper.deepCopyPointF(slicePoints[0]));
        childImage1.setPoint(1, new PointF(origin.right, origin.top));
        childImage1.setPoint(2, new PointF(origin.right, origin.bottom));
        childImage1.setPoint(3, Helper.deepCopyPointF(slicePoints[1]));
        //childImage1.move(-baseImage.getRectWidth()/2, 0);

        childImage2.setPoint(0, new PointF(origin.left, origin.top));
        childImage2.setPoint(1, Helper.deepCopyPointF(slicePoints[0]));
        childImage2.setPoint(2, Helper.deepCopyPointF(slicePoints[1]));
        childImage2.setPoint(3, new PointF(origin.left, origin.bottom));
        //childImage2.move(baseImage.getRectWidth()/2, 0);
    }

    public boolean isSliced() {
        return isSliced;
    }
}
