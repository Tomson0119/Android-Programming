package kr.ac.tukorea.gamekim2016180014.dragonflight.framework;

import android.graphics.RectF;

public class CollisionHelper {
    public static boolean collides(BoxCollidable o1, BoxCollidable o2) {
        RectF rect1 = o1.getBoundingRect();
        RectF rect2 = o2.getBoundingRect();

        if(rect1.left > rect2.right) return false;
        if(rect1.top > rect2.bottom) return false;
        if(rect1.right < rect2.left) return false;
        if(rect1.bottom < rect2.top) return false;

        return true;
    }
}
