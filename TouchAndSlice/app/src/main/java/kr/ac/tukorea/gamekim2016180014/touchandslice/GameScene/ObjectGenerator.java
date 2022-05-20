package kr.ac.tukorea.gamekim2016180014.touchandslice.GameScene;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.Random;

import kr.ac.tukorea.gamekim2016180014.touchandslice.Common.Helper;
import kr.ac.tukorea.gamekim2016180014.touchandslice.Common.Metrics;
import kr.ac.tukorea.gamekim2016180014.touchandslice.Common.ObjectPool;
import kr.ac.tukorea.gamekim2016180014.touchandslice.R;

public class ObjectGenerator implements GameObject {
    private static final String TAG = ObjectGenerator.class.getSimpleName();

    private final float min_vx;
    private final float max_vx;
    private final float vy;

    private final Random random;
    private final float min_interval;
    private final float max_interval;

    private float interval;
    private float dt;

    private Paint debugPaint;

    ObjectGenerator() {
        min_vx = Metrics.floatValue(R.dimen.min_vx);
        max_vx = Metrics.floatValue(R.dimen.max_vx);
        vy = Metrics.floatValue(R.dimen.vy);

        random = new Random();
        min_interval = Metrics.floatValue(R.dimen.min_generate_interval);
        max_interval = Metrics.floatValue(R.dimen.max_generate_interval);

        debugPaint = new Paint();
        debugPaint.setColor(Color.RED);
        debugPaint.setTextSize(50.0f);
        debugPaint.setStrokeWidth(10);
    }

    @Override
    public void update(float elapsed) {
        setRandomInterval();
        dt += elapsed;
        if(dt >= interval) {
            generateObject();
            dt = 0.0f;
            interval = 0.0f;
        }
    }

    private void setRandomInterval() {
        if(interval == 0.0f) {
            interval = min_interval + random.nextFloat() * (max_interval - min_interval);
        }
    }

    private void generateObject() {
        SliceObject obj = Helper.getSliceObject();

        float pos_x = random.nextFloat() * Metrics.width;
        obj.init(pos_x, Metrics.height, R.mipmap.hamburger);

        float vx = random.nextFloat() * (max_vx - min_vx) + min_vx;
        if(pos_x > Metrics.width / 2) {
            vx *= -1.0f;
        }
        obj.setInitialVel(vx, vy);
        GameScene.getInstance().addObject(obj);
    }

    @Override
    public void draw(Canvas canvas) {
        int sliceObjCnt = ObjectPool.getInstance().getCount(SliceObject.class);
        int pointFCnt = ObjectPool.getInstance().getCount(CustomPointF.class);
        canvas.drawText("CustomPointF: " + pointFCnt, 10, 500, debugPaint);
        canvas.drawText("SliceObject: " + sliceObjCnt, 10, 600, debugPaint);
    }
}
