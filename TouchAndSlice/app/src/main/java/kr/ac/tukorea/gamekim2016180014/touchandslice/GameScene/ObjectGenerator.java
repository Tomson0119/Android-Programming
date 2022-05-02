package kr.ac.tukorea.gamekim2016180014.touchandslice.GameScene;

import android.app.slice.Slice;
import android.graphics.Canvas;
import android.util.Log;

import java.util.Random;

import kr.ac.tukorea.gamekim2016180014.touchandslice.Common.Metrics;
import kr.ac.tukorea.gamekim2016180014.touchandslice.R;

public class ObjectGenerator implements GameObject {
    private static final String TAG = ObjectGenerator.class.getSimpleName();

    private final Random random;
    private final float min_interval;
    private final float max_interval;

    private float interval;
    private float dt;

    ObjectGenerator() {
        random = new Random();
        min_interval = Metrics.floatValue(R.dimen.min_generate_interval);
        max_interval = Metrics.floatValue(R.dimen.max_generate_interval);
        //Log.d(TAG, "min: " + min_interval);
        //Log.d(TAG, "max: " + max_interval);
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
            //Log.d(TAG, "Next interval: " + interval);
        }
    }

    private void generateObject() {
        SliceObject obj = (SliceObject)ObjectPool.getInstance().get(SliceObject.class);
        if(obj == null) {
            obj = new SliceObject();
            ObjectPool.getInstance().add(obj);
        }

        float pos_x = random.nextFloat() * Metrics.width;
        obj.init(pos_x, Metrics.height, R.mipmap.hamburger);

        float min_vx = Metrics.floatValue(R.dimen.min_vx);
        float max_vx = Metrics.floatValue(R.dimen.max_vx);
        float vx = random.nextFloat() * (max_vx - min_vx) + min_vx;
        if(pos_x > Metrics.width / 2) {
            vx *= -1.0f;
        }
        obj.setInitialVel(vx, Metrics.floatValue(R.dimen.vy));
        GameScene.getInstance().addObject(obj);
    }

    @Override
    public void draw(Canvas canvas) {

    }
}
