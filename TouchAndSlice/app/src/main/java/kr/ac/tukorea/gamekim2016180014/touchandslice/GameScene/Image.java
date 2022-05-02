package kr.ac.tukorea.gamekim2016180014.touchandslice.GameScene;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import kr.ac.tukorea.gamekim2016180014.touchandslice.Common.BitmapPool;

public class Image extends CustomRect {
    protected Bitmap bitmap;
    private Paint rectPaint;

    public Image() {
        super();
        initPaint();
    }

    public Image(int bitmapId, float width, float height) {
        super();
        initPaint();
        init(bitmapId, width, height);
    }

    public void init(int bitmapId, float width, float height) {
        bitmap = BitmapPool.getBitmap(bitmapId);
        initAsRect(width, height);
    }

    private void initPaint() {
        rectPaint = new Paint();
        rectPaint.setColor(Color.BLACK);
        rectPaint.setStyle(Paint.Style.STROKE);
        rectPaint.setStrokeWidth(5);
    }

    public void draw(Canvas canvas) {
        canvas.save();
        super.draw(canvas);
        canvas.drawBitmap(bitmap, null, getRect(), null);
        canvas.restore();
    }

    public void drawRect(Canvas canvas) {
        canvas.drawRect(getRect(), rectPaint);
        canvas.drawBitmap(bitmap, null, getRect(), null);
    }


}