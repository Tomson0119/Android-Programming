package kr.ac.tukorea.gamekim2016180014.touchandslice.Common;

import android.graphics.PointF;

import kr.ac.tukorea.gamekim2016180014.touchandslice.GameScene.CustomPointF;
import kr.ac.tukorea.gamekim2016180014.touchandslice.GameScene.SliceObject;

public class Helper {
    public static SliceObject getSliceObject() {
        SliceObject obj = (SliceObject) ObjectPool.getInstance().get(SliceObject.class);
        if(obj == null) {
            obj = new SliceObject();
            ObjectPool.getInstance().add(obj);
        }
        return obj;
    }

    public static PointF getPointF(float x, float y) {
        CustomPointF obj = (CustomPointF)ObjectPool.getInstance().get(CustomPointF.class);
        if(obj == null) {
            obj = new CustomPointF();
            ObjectPool.getInstance().add(obj);
        }
        obj.set(x, y);
        return obj.getPointF();
    }
}
