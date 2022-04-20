package kr.ac.tukorea.gamekim2016180014.touchandslice;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;

public class CustomRect {
    static protected final int MAX_POINTS = 4;
    protected PointF[] points;
    protected Path clipShapePath;

    protected Paint linePaint;
    protected Paint clipPaint;
    protected Paint vertexPaint;

    protected RectF baseRect;

    public CustomRect() {
        points = new PointF[MAX_POINTS];
        clipShapePath = new Path();

        linePaint = new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(5);
        linePaint.setColor(Color.BLACK);

        clipPaint = new Paint();
        clipPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        clipPaint.setColor(Color.TRANSPARENT);
        clipPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));

        vertexPaint = new Paint();
        vertexPaint.setStrokeWidth(10);
        vertexPaint.setColor(Color.RED);

        baseRect = new RectF();
    }

    public void initAsRect(float width, float height) {
        float hw = width/2;
        float hh = height/2;
        setPoint(0, new PointF(-hw, -hh));
        setPoint(1, new PointF(+hw, -hh));
        setPoint(2, new PointF(+hw, +hh));
        setPoint(3, new PointF(-hw, +hh));
        baseRect.set(-hw, -hh, +hw, +hh);
    }

    public void setPoint(int idx, PointF point) {
        points[idx] = point;
    }

    public void draw(Canvas canvas) {
        composePath();
        canvas.drawPath(clipShapePath, clipPaint);
        //canvas.drawPath(clipShapePath, linePaint);
    }

    private void composePath() {
        clipShapePath.reset();
        clipShapePath.moveTo(points[0].x, points[1].y);
        for(int i=0;i<points.length;i++) {
            clipShapePath.lineTo(points[i].x, points[i].y);
        }
        clipShapePath.lineTo(points[0].x, points[0].y);
    }

    public void move(float dx, float dy) {
        int i=0;
        for(PointF point : points) {
            System.out.println(i + " " + point);
            point.x += dx;
            point.y += dy;
            System.out.println(point);
            i+=1;
        }
        baseRect.offset(dx, dy);
    }

    public RectF getRect() {
        return baseRect;
    }

    public float getRectWidth() {
        return baseRect.width();
    }

    public float getRectHeight() {
        return baseRect.height();
    }
}
