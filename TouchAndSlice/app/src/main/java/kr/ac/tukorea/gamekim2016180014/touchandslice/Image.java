package kr.ac.tukorea.gamekim2016180014.touchandslice;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

public class Image {
    protected Bitmap bitmap;
    protected RectF dstRect;

    public Image(int bitmapId) {
        bitmap = BitmapPool.getBitmap(bitmapId);
        dstRect = new RectF(0,0, 100, 100);
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, null, dstRect, null);
    }
}
