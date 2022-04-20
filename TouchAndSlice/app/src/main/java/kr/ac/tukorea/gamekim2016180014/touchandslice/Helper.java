package kr.ac.tukorea.gamekim2016180014.touchandslice;

import android.graphics.PointF;

public class Helper {
    static PointF deepCopyPointF(PointF obj) {
        PointF newObj = new PointF();
        newObj.set(obj.x, obj.y);
        return newObj;
    }
}
