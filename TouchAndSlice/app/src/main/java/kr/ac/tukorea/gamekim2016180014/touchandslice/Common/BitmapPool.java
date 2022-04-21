package kr.ac.tukorea.gamekim2016180014.touchandslice.Common;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.HashMap;

import kr.ac.tukorea.gamekim2016180014.touchandslice.GameScene.ScreenView;

public class BitmapPool {
    private static HashMap<Integer, Bitmap> pool = new HashMap<>();

    public static Bitmap getBitmap(int bitmapId) {
        Bitmap bitmap = pool.get(bitmapId);
        if(bitmap == null) {
            Resources res = ScreenView.view.getResources();
            bitmap = BitmapFactory.decodeResource(res, bitmapId);
            pool.put(bitmapId, bitmap);
        }
        return bitmap;
    }
}
