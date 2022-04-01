package kr.ac.tukorea.gamekim2016180014.samplegame;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Ball implements GameObject {
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

        if(bitmap == null) {
            bitmap = BitmapFactory.decodeResource(GameView.res, R.mipmap.soccer_ball);
            srcRect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        }
    }

    public void update() {
        dstRect.offset(dx, dy);
        if (dstRect.left < 0 || dstRect.right > GameView.view.getWidth())
            dx *= -1;
        if (dstRect.top < 0 || dstRect.bottom > GameView.view.getHeight())
            dy *= -1;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, srcRect, dstRect, null);
    }
}
