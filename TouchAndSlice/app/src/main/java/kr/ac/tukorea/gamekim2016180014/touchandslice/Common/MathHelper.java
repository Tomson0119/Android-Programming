package kr.ac.tukorea.gamekim2016180014.touchandslice.Common;

import android.graphics.PointF;
import android.graphics.RectF;

public class MathHelper {
    private static String TAG = MathHelper.class.getSimpleName();

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

    public static boolean isInside(RectF rect, PointF point) {
        return ((rect.top <= point.y && point.y <= rect.bottom)
                && (rect.left <= point.x && point.x <= rect.right));
    }
}
