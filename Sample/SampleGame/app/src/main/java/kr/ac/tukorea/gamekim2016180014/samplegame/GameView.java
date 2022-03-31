package kr.ac.tukorea.gamekim2016180014.samplegame;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class GameView extends View {

    private Bitmap soccerBitmap;
    private Rect srcRect = new Rect();
    private Rect dstRect = new Rect();

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        Resources res = getResources();
        soccerBitmap = BitmapFactory.decodeResource(res, R.mipmap.soccer_ball);
        srcRect.set(0, 0, soccerBitmap.getWidth(), soccerBitmap.getHeight());
        dstRect.set(0,0,100,100);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(soccerBitmap, srcRect, dstRect, null);
    }
}
