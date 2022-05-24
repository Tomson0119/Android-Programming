package kr.ac.tukorea.gamekim2016180014.touchandslice.Common;

import android.content.res.Resources;
import android.util.TypedValue;

import kr.ac.tukorea.gamekim2016180014.touchandslice.GameScene.ScreenView;

public class Metrics {
    public static int width;
    public static int height;

    public static void setResolution(int w, int h) {
        width = w;
        height = h;
    }

    public static float getSize(int dimenResId) {
        Resources res = ScreenView.view.getResources();
        return res.getDimension(dimenResId);
    }

    public static float floatValue(int dimenResId) {
        Resources res = ScreenView.view.getResources();
        TypedValue outValue = new TypedValue();
        res.getValue(dimenResId, outValue, true);
        return outValue.getFloat();
    }

    public static int intValue(int dimenResId) {
        Resources res = ScreenView.view.getResources();
        return res.getInteger(dimenResId);
    }
}
