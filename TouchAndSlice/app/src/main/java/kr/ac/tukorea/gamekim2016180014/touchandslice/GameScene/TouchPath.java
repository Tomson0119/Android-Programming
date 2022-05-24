package kr.ac.tukorea.gamekim2016180014.touchandslice.GameScene;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;

import java.util.LinkedList;

import kr.ac.tukorea.gamekim2016180014.touchandslice.Common.Helper;
import kr.ac.tukorea.gamekim2016180014.touchandslice.Common.MathHelper;
import kr.ac.tukorea.gamekim2016180014.touchandslice.Common.Metrics;
import kr.ac.tukorea.gamekim2016180014.touchandslice.Common.ObjectPool;
import kr.ac.tukorea.gamekim2016180014.touchandslice.R;

public class TouchPath implements GameObject {
    private static final String TAG = TouchPath.class.getSimpleName();

    private static final float STROKE_WIDTH = 5.0f;
    private static final float DURATION = 0.05f;
    private static final int MAX_POINTS = 10;

    private final Paint paint;
    private final float min_distance;

    private float accumulation;
    private LinkedList<CustomPointF> points;
    private Path path;

    private PointF firstPoint;
    private PointF lastPoint;

    public TouchPath() {
        points = new LinkedList<>();

        accumulation = 0.0f;

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(STROKE_WIDTH);

        min_distance = Metrics.getSize(R.dimen.obj_image_width);

        path = new Path();
    }

    @Override
    public void update(float elapsed) {
        accumulation += elapsed;
        if(accumulation >= DURATION) {
            removePoint();
            connectPoints();
            accumulation = 0.0f;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawPath(path, paint);
    }

    public void appendPoint(float x, float y) {
        if(!points.isEmpty()) {
            PointF last = points.peekLast().getPointF();

            float xDiff = (x - last.x);
            float yDiff = (y - last.y);
            float length = (float) Math.sqrt(xDiff * xDiff + yDiff * yDiff);
            int count = Math.round(length / (min_distance * 0.5f));

            if (count >= 1) {
                for (int i = 1; i < count; i++) {
                    float targetX = xDiff / count * i + last.x;
                    float targetY = yDiff / count * i + last.y;
                    points.add(Helper.getCustomPointF(targetX, targetY));
                }
            }
        }

        points.add(Helper.getCustomPointF(x, y));
        if(points.size() > MAX_POINTS) {
            removePoint();
        }
        connectPoints();
    }

    public void removePoint() {
        if(!points.isEmpty()) {
            ObjectPool.getInstance().retrieve(points.get(0));
            points.poll();
        }
    }

    public void clearPath() {
        path.reset();
        for(CustomPointF p : points) {
            ObjectPool.getInstance().retrieve(p);
        }
        points.clear();
    }

    private void connectPoints() {
        if(points.isEmpty()) return;
        path.reset();
        PointF front = points.get(0).getPointF();
        path.moveTo(front.x, front.y);
        for(int i = 1; i < points.size(); i++) {
            PointF p = points.get(i).getPointF();
            path.lineTo(p.x, p.y);
        }
    }

    public boolean isCollidedWith(SliceObject obj) {
        if(obj.isSliced()) return false;

        RectF rect = obj.getRect();
        firstPoint = null;
        lastPoint = null;
        int count = 0;
        for(CustomPointF point : points) {
            if (MathHelper.isInside(rect, point.getPointF())) {
                if (firstPoint == null) {
                    firstPoint = point.getPointF();
                }
                lastPoint = point.getPointF();
                count += 1;
            }
        }
        return (count >= 2);
    }

    public float getSlope() {
        return MathHelper.getSlope(firstPoint, lastPoint);
    }
}
