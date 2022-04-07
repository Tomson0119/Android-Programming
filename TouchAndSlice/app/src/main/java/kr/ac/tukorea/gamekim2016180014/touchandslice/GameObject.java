package kr.ac.tukorea.gamekim2016180014.touchandslice;

import android.graphics.Canvas;

public interface GameObject {
    void update(float elapsed);
    void draw(Canvas canvas);
}
