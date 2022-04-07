package kr.ac.tukorea.gamekim2016180014.touchandslice;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;

import java.util.LinkedList;

public class TouchPath implements GameObject {
    private static final float STROKE_WIDTH = 5.0f;
    private static final int MAX_POINTS = 10;

    private float duration;
    private float accumulation;
    private LinkedList<PointF> points;
    private Paint paint;
    private Path path;

    public TouchPath() {
        points = new LinkedList<>();

        accumulation = 0.0f;
        duration = 0.05f;

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(STROKE_WIDTH);

        path = new Path();
    }

    @Override
    public void update(float elapsed) {
        accumulation += elapsed;
        if(accumulation >= duration) {
            points.poll();
            connectPoints();
            accumulation = 0.0f;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawPath(path, paint);
    }

    public void appendPoint(float x, float y) {
        points.add(new PointF(x, y));
        if(points.size() > MAX_POINTS) {
            points.poll();
        }
        connectPoints();
    }

    public void clearPath() {
        path.reset();
        points.clear();
    }

    private void connectPoints() {
        if(points.isEmpty())
            return;

        path.reset();
        path.moveTo(points.get(0).x, points.get(0).y);
        for(int i = 1; i < points.size(); i++) {
            path.lineTo(points.get(i).x, points.get(i).y);
        }
    }

    public boolean isCollided(SliceObject obj) {
        RectF rect = obj.getRect();
        int count = 0;
        for(PointF point : points) {
            if(isInside(rect, point)) {
                count += 1;
                if(count == 2) return true;
            }
        }
        return false;
    }

    private boolean isInside(RectF rect, PointF point) {
        return ((rect.top <= point.y && point.y <= rect.bottom)
                && (rect.left <= point.x && point.x <= rect.right));
    }
}
