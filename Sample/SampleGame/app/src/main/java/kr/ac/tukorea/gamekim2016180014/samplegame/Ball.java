package kr.ac.tukorea.gamekim2016180014.samplegame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.View;

public class Ball {
    public int dx;
    public int dy;

    private Rect dstRect = new Rect();

    private static Bitmap bitmap;
    private static Rect srcRect = new Rect();

    public Ball(int x, int y, int speed) {
        int len = (int)Math.sqrt((x*x)+(y*y));

        this.dx = speed * x / len;
        this.dy = speed * y / len;
        dstRect.set(0, 0, 100, 100);
    }

    public void updateOffset(View view) {
        dstRect.offset(dx, dy);
        if (dstRect.left < 0 || dstRect.right > view.getWidth())
            dx *= -1;
        if (dstRect.top < 0 || dstRect.bottom > view.getHeight())
            dy *= -1;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, srcRect, dstRect, null);
    }

    public static void setBitmap(Bitmap bitmap) {
        Ball.bitmap = bitmap;
        srcRect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
    }
}
