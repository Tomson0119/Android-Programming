package kr.ac.tukorea.gamekim2016180014.touchandslice;

import android.graphics.PointF;
import android.graphics.RectF;

public class MathHelper {
    public static float getSlope(PointF first, PointF last) {
        assert(first != last);
        if(first.x < last.x) {
            return ((last.y - first.y) / (last.x - first.x));
        } else if(first.x > last.x) {
            return ((first.y - last.y) / (first.x - last.x));
        } else {
            return Float.MAX_VALUE;
        }
    }

    public static PointF getIntersection(PointF p11, PointF p12, PointF p21, PointF p22) {
        return null;
    }

    public static boolean isInside(RectF rect, PointF point) {
        return ((rect.top <= point.y && point.y <= rect.bottom)
                && (rect.left <= point.x && point.x <= rect.right));
    }
}
