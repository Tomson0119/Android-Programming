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
        // Random --> start x, initial vx, generate interval.
        setRandomInterval();
        dt += elapsed;
        if(dt >= interval) {

            SliceObject obj = (SliceObject)ObjectPool.getInstance().get(SliceObject.class);
            if(obj == null) {
                obj = new SliceObject();
            }
            obj.init(0, Metrics.height, R.mipmap.hamburger);
            obj.setInitialVel(10.0f, 50.0f);
            ObjectPool.getInstance().add(obj);
            GameScene.getInstance().addObject(obj);
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

    @Override
    public void draw(Canvas canvas) {

    }
}
