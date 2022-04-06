package kr.ac.tukorea.gamekim2016180014.samplegame;

import android.content.res.Resources;

public class Metrics {
    public static int width;
    public static int height;

    public static float size(int dimenResId) {
        return GameView.res.getDimension(dimenResId);
    }
}
