package kr.ac.tukorea.gamekim2016180014.samplegame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.TypedValue;

import androidx.annotation.NonNull;

public class Fighter implements GameObject {
    private RectF dstRect = new RectF();

    private static Bitmap bitmap;
    private static Rect srcRect = new Rect();

    private float x, y;
    private float dx, dy;
    private float tx, ty;
    private float radius;

    public Fighter(float x_, float y_) {
        x = x_;
        y = y_;
        tx = x_;
        ty = y_;

        radius = Metrics.size(R.dimen.fighter_radius);
        dstRect.set(x - radius, y - radius, x + radius, y + radius);

        if(bitmap == null) {
            bitmap = BitmapFactory.decodeResource(GameView.res, R.mipmap.plane);
            srcRect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        }
    }

    public void update() {
        float angle = (float) Math.atan2((ty-y), (tx-x));

        float speed = Metrics.size(R.dimen.fighter_speed);
        dx = (float)(MainGame.GetInstance().frameTime * speed * Math.cos(angle));
        dy = (float)(MainGame.GetInstance().frameTime * speed * Math.sin(angle));

        if((dx < 0 && (x + dx) < tx) || (dx > 0 && (x + dx) > tx)) {
            x = tx;
            dx = 0.0f;
        } else {
            x += dx;
        }

        if((dy < 0 && (y + dy) < ty) || (dy > 0 && (y + dy) > ty)) {
            y = ty;
            dy = 0.0f;
        } else {
            y += dy;
        }
        dstRect.offset(dx, dy);
    }

    public void draw(@NonNull Canvas canvas) {
        canvas.drawBitmap(bitmap, srcRect, dstRect, null);
    }

    public void setTargetPosition(float x_, float y_) {
        tx = x_;
        ty = y_;
    }
}
