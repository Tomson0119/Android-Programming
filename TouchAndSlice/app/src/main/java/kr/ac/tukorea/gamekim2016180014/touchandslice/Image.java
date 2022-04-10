package kr.ac.tukorea.gamekim2016180014.touchandslice;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;

public class Image {
    protected Rect border;
    protected Paint borderPaint;
    protected Bitmap bitmap;
    protected RectF dstRect;

    private PointF[] vertices;
    private Paint vertexPaint;

    private Matrix mat;

    public Image(int bitmapId) {
        bitmap = BitmapPool.getBitmap(bitmapId);
        createBorder();
        float width = Metrics.getSize(R.dimen.image_width);
        float height = Metrics.getSize(R.dimen.image_height);
        dstRect = new RectF(-width / 2,-height/2, width/2, height/2);

        vertices = new PointF[4];
        for(int i=0;i<vertices.length;i++) {
            vertices[i] = new PointF();
        }

        vertexPaint = new Paint();
        vertexPaint.setColor(Color.RED);
        vertexPaint.setStrokeWidth(10);

        mat = new Matrix();
        mat.mapRect(dstRect);
    }

    public void setRotation(float angle) {
        mat.setRotate(angle, dstRect.centerX(), dstRect.centerY());
        mat.mapRect(dstRect);
    }

    private void createBorder() {
        borderPaint = new Paint();
        borderPaint.setColor(Color.BLACK);
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(2);
    }

    public void update(float elapsed) {
        vertices[0].set(dstRect.left, dstRect.top);
        vertices[1].set(dstRect.right, dstRect.top);
        vertices[2].set(dstRect.left, dstRect.bottom);
        vertices[3].set(dstRect.right, dstRect.bottom);

        //System.out.println(vertices[0]);
    }

    public void draw(Canvas canvas) {
        //canvas.drawBitmap(bitmap, null, dstRect, null);
        canvas.drawRect(dstRect, borderPaint);
        for(int i=0;i<vertices.length;i++) {
            canvas.drawPoint(vertices[i].x, vertices[i].y, vertexPaint);
        }
        canvas.drawPoint(dstRect.centerX(), dstRect.centerY(), vertexPaint);
    }

    public void move(float x, float y) {
        dstRect.offset(x, y);
    }

    public RectF getRect() {
        return dstRect;
    }
}
