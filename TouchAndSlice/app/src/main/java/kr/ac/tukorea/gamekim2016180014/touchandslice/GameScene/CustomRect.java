package kr.ac.tukorea.gamekim2016180014.touchandslice.GameScene;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Build;

import androidx.annotation.RequiresApi;

public class CustomRect {
    static protected final int MAX_POINTS = 4;
    protected PointF[] points;
    protected Path clipShapePath;

    protected Paint linePaint; // DEBUG
    protected RectF baseRect;

    public CustomRect() {
        points = new PointF[MAX_POINTS];
        for(int i=0;i<MAX_POINTS;i++) {
            points[i] = new PointF();
        }

        clipShapePath = new Path();

        linePaint = new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(5);
        linePaint.setColor(Color.BLACK);

        baseRect = new RectF();
    }

    public void initAsRect(float width, float height) {
        float hw = width/2;
        float hh = height/2;
        points[0].set(-hw, -hh);
        points[1].set(+hw, -hh);
        points[2].set(+hw, +hh);
        points[3].set(-hw, +hh);
        baseRect.set(-hw, -hh, +hw, +hh);
    }

    public void setPoint(int idx, PointF point) {
        points[idx] = point;
    }

    public void draw(Canvas canvas) {
        composePath();
        canvas.clipPath(clipShapePath);
        canvas.drawPath(clipShapePath, linePaint);
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
        for(PointF point : points) {
            point.x += dx;
            point.y += dy;
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
