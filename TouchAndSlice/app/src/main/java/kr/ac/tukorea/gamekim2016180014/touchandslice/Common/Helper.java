package kr.ac.tukorea.gamekim2016180014.touchandslice.Common;

import android.graphics.PointF;

public class Helper {
    public static PointF deepCopyPointF(PointF obj) {
        PointF newObj = new PointF();
        newObj.set(obj.x, obj.y);
        return newObj;
    }
}
