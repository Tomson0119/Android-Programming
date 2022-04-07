package kr.ac.tukorea.gamekim2016180014.touchandslice;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

public class Image {
    protected Rect border;
    protected Paint borderPaint;
    protected Bitmap bitmap;
    protected RectF dstRect;

    public Image(int bitmapId) {
        bitmap = BitmapPool.getBitmap(bitmapId);
        createBorder();
        float width = Metrics.getSize(R.dimen.image_width);
        float height = Metrics.getSize(R.dimen.image_height);
        dstRect = new RectF(-width / 2,-height/2, width/2, height/2);
    }

    private void createBorder() {
        borderPaint = new Paint();
        borderPaint.setColor(Color.BLACK);
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(2);
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, null, dstRect, null);
        canvas.drawRect(dstRect, borderPaint);
    }

    public void move(float x, float y) {
        dstRect.offset(x, y);
    }

    public RectF getRect() {
        return dstRect;
    }
}
