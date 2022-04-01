package kr.ac.tukorea.gamekim2016180014.samplegame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;

public class Fighter {
    private Rect dstRect = new Rect();

    private static Bitmap bitmap;
    private static Rect srcRect = new Rect();

    public Fighter() {
        dstRect.set(0, 0, 200, 200);
    }

    public void update(View view) {
    }

    public void draw(@NonNull Canvas canvas) {
        canvas.drawBitmap(bitmap, srcRect, dstRect, null);
    }

    public static void setBitmap(@NonNull Bitmap bitmap) {
        Fighter.bitmap = bitmap;
        srcRect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
    }

    public void setPosition(float x, float y) {
        int radius = 200 / 2;
        dstRect.set(
                (int)x - radius,
                (int)y - radius,
                (int)x + radius,
                (int)y + radius
        );
    }
}
