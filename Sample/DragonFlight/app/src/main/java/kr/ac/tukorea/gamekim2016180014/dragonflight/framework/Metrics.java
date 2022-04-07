package kr.ac.tukorea.gamekim2016180014.dragonflight.framework;

import android.content.res.Resources;

import kr.ac.tukorea.gamekim2016180014.dragonflight.framework.GameView;

public class Metrics {
    public static int width;
    public static int height;

    public static float size(int dimenResId) {
        Resources res = GameView.view.getResources();
        float size = res.getDimension(dimenResId);
        return size;
    }
}
