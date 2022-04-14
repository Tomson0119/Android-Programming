package kr.ac.tukorea.gamekim2016180014.touchandslice;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;

public class SliceObject implements GameObject {
    private final Image image;
    private PointF center;
    private PointF[] slicePoints;
    private Paint slicePaint;

    public SliceObject(float x, float y, int imageId) {
        image = new Image(imageId);
        center = new PointF(x, y);
        image.move(x, y);
        slicePoints = new PointF[2];
        slicePoints[0] = new PointF();
        slicePoints[1] = new PointF();
        slicePaint = new Paint();
        slicePaint.setColor(Color.BLUE);
        slicePaint.setStrokeWidth(10);
    }

    @Override
    public void update(float elapsed) {
        image.update(elapsed);
    }

    @Override
    public void draw(Canvas canvas) {
        image.draw(canvas);
        for(PointF p : slicePoints) {
            canvas.drawPoint(p.x, p.y, slicePaint);
        }
    }

    public RectF getRect() {
        return image.getRect();
    }

    public void slice(float slope) {
        setSlicePoints(slope);

        PointF[] vertices = image.getVertices();
        for(int i=0;i<vertices.length;i++) {
            PointF p11 = vertices[i];
            PointF p12 = vertices[(i+1)%vertices.length];
        }
    }

    private void setSlicePoints(float slope) {
        RectF rect = image.getRect();
        if(slope < Float.MAX_VALUE) {
            if(Math.abs(slope) <= 1.0f) {
                slicePoints[0].set(center.x - 200.0f, center.y - 200.0f * slope);
                slicePoints[1].set(center.x + 200.0f, center.y + 200.0f * slope);
            } else {
                slicePoints[0].set(center.x - 200.0f / slope, center.y - 200.0f);
                slicePoints[1].set(center.x + 200.0f / slope, center.y + 200.0f);
            }
        } else {
            slicePoints[0].set(center.x, center.y - 200.0f);
            slicePoints[1].set(center.x, center.y + 200.0f);
        }
    }
}
