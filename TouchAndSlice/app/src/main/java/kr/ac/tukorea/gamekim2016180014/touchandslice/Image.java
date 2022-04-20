package kr.ac.tukorea.gamekim2016180014.touchandslice;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;

public class Image extends CustomRect {
    protected Bitmap bitmap;
    private Paint bitmapPaint;

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
        bitmapPaint = new Paint();
        bitmapPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OVER));
        initAsRect(width, height);
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, null, getRect(), null);
        super.draw(canvas);
    }

    public void drawRect(Canvas canvas) {
        canvas.drawBitmap(bitmap, null, getRect(), null);
    }
}
