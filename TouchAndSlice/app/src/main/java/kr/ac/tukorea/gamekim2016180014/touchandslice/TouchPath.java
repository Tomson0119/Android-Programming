package kr.ac.tukorea.gamekim2016180014.touchandslice;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;

import java.util.LinkedList;
import java.util.Queue;

public class TouchPath {
    private static final float STROKE_WIDTH = 5.0f;

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

    public void update(float elapsed) {
        accumulation += elapsed;
        if(accumulation >= duration) {
            points.poll();
            connectPoints();
            accumulation = 0.0f;
        }
    }

    public void draw(Canvas canvas) {
        canvas.drawPath(path, paint);
    }

    public void appendPoint(float x, float y) {
        points.add(new PointF(x, y));
        if(points.size() > 10) {
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
}
