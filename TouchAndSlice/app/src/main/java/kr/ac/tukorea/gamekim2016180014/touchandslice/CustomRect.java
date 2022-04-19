package kr.ac.tukorea.gamekim2016180014.touchandslice;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;

public class CustomRect {
    static final int MAX_POINTS = 4;
    protected PointF[] points;

    protected Paint linePaint;
    protected Paint vertexPaint;

    protected RectF rect;

    public CustomRect() {
        points = new PointF[MAX_POINTS];

        linePaint = new Paint();
        linePaint.setStrokeWidth(5);
        linePaint.setColor(Color.BLACK);

        vertexPaint = new Paint();
        vertexPaint.setStrokeWidth(10);
        vertexPaint.setColor(Color.RED);

        rect = new RectF();
    }

    public void initAsRect(float width, float height) {
        float hw = width/2;
        float hh = height/2;
        setPoint(0, new PointF(-hw, -hh));
        setPoint(1, new PointF(+hw, -hh));
        setPoint(2, new PointF(+hw, +hh));
        setPoint(3, new PointF(-hw, +hh));
    }

    public void setPoint(int idx, PointF point) {
        points[idx] = point;
    }

    public void draw(Canvas canvas) {
        for(int i=1;i<points.length;i++) {
            PointF p1 = points[i-1];
            PointF p2 = points[i];

            if(i == points.length-1) {
                canvas.drawLine(p2.x, p2.y, points[0].x, points[0].y, linePaint);
                canvas.drawPoint(points[0].x, points[0].y, vertexPaint);
            }
            canvas.drawLine(p1.x, p1.y, p2.x, p2.y, linePaint);
            canvas.drawPoint(p2.x, p2.y, vertexPaint);
        }
    }

    public void move(float dx, float dy) {
        for(PointF point : points) {
            point.x += dx;
            point.y += dy;
        }
    }

    public RectF getRect() {
        if(isRect()) {
            rect.set(points[0].x, points[0].y, points[1].x, points[2].y);
            return rect;
        }
        return null;
    }

    public float getRectWidth() {
        if(isRect()) {
            return points[1].x - points[0].x;
        }
        return -1;
    }

    public float getRectHeight() {
        if(isRect()) {
            return points[2].y - points[1].y;
        }
        return -1;
    }

    private boolean isRect() {
        return (points[0].y == points[1].y
                && points[0].x == points[3].x
                && points[2].y == points[3].y
                && points[1].x == points[2].x);
    }
}
