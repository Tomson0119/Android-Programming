package kr.ac.tukorea.gamekim2016180014.morecontrols;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class GameView extends View {

    private static final String TAG = GameView.class.getSimpleName();
    private Paint redPaint;
    private Bitmap soccerBitmap;
    private Rect srcRect;
    private RectF dstRect;
    private int radius;

    public GameView(Context context) {
        super(context);
        initView();
        setRect(100);
    }
    public GameView(Context context, AttributeSet as) {
        super(context, as);
        initView();
        setRect(100);
    }

    private void setRect(int r) {
        int width = getWidth();
        int height = getHeight();

        int cx = getPaddingLeft() + (width-getPaddingLeft() - getPaddingRight()) / 2;
        int cy = getPaddingTop() + (height-getPaddingTop() - getPaddingBottom()) / 2;

        radius = r;
        int left = cx - radius;
        int right = cx + radius;
        int top = cy - radius;
        int bottom = cy + radius;

        srcRect = new Rect(0, 0, soccerBitmap.getWidth(), soccerBitmap.getHeight());
        dstRect = new RectF(left, top, right, bottom);
    }

    private void initView() {
        redPaint = new Paint();
        redPaint.setColor(Color.RED);

        Resources res = getResources();
        soccerBitmap = BitmapFactory.decodeResource(res, R.mipmap.soccer_ball);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(soccerBitmap, srcRect, dstRect, null);
    }
}
