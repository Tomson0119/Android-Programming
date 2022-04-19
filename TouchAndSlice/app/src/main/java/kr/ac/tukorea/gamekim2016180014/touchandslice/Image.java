package kr.ac.tukorea.gamekim2016180014.touchandslice;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Image extends CustomRect {
    protected Bitmap bitmap;

    public Image(int bitmapId) {
        super();
        init(bitmapId, 0, 0);
    }

    public Image(int bitmapId, float width, float height) {
        super();
        init(bitmapId, width, height);
    }

    private void init(int bitmapId, float width, float height) {
        bitmap = BitmapPool.getBitmap(bitmapId);
        initAsRect(width, height);
    }

    public void draw(Canvas canvas) {
        //canvas.drawBitmap(bitmap, null, dstRect, null);
        super.draw(canvas);
    }
}
