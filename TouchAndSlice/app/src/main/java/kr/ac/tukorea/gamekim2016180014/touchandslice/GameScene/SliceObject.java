package kr.ac.tukorea.gamekim2016180014.touchandslice.GameScene;

import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.RectF;

import kr.ac.tukorea.gamekim2016180014.touchandslice.Common.AudioPlayer;
import kr.ac.tukorea.gamekim2016180014.touchandslice.Common.Metrics;
import kr.ac.tukorea.gamekim2016180014.touchandslice.R;

public class SliceObject implements GameObject {
    private static final String TAG = SliceObject.class.getSimpleName();
    private final Image[] images;

    private final PointF center;
    private final PointF[] slicePoints;
    private final PointF[] imagePoints;

    private final float gravity;

    private boolean isSliced;
    private float vx0;
    private float vy0;
    private float dt;
    private double theta;

    private enum Mode {
        NORMAL, TRAP, KILLER
    }
    private Mode mode;

    public SliceObject() {
        images = new Image[3];
        for(int i=0;i<3;i++) {
            images[i] = new Image();
        }
        center = new PointF();
        slicePoints = new PointF[2];
        for(int i=0;i<slicePoints.length;i++) {
            slicePoints[i] = new PointF();
        }
        imagePoints = new PointF[8];
        for(int i=0;i<imagePoints.length;i++) {
            imagePoints[i] = new PointF();
        }
        gravity = Metrics.floatValue(R.dimen.gravity);
    }

    public void init(float x, float y, int mode, int imageId) {
        float width = Metrics.getSize(R.dimen.obj_image_width);
        float height = Metrics.getSize(R.dimen.obj_image_height);
        for(int i=0;i<3;i++) {
            images[i].init(imageId, width, height);
            images[i].move(x, y);
        }
        setObjMode(mode);
        center.set(x, y);
        isSliced = false;
    }

    private void setObjMode(int mode) {
        this.mode = Mode.values()[mode];
        switch(mode) {
            case 0:
                AudioPlayer.getInstance().playAudio(R.raw.normal_obj_spawn);
                break;
            case 1:
            case 2:
                AudioPlayer.getInstance().playAudio(R.raw.bad_obj_spawn);
                break;
        }
    }

    public void setInitialVel(float x, float y) {
        this.vx0 = x;
        this.vy0 = y;
        this.theta = Math.atan2(y, x);
        this.dt = 0.0f;
    }

    @Override
    public void update(float elapsed) {
        float dx = vx0 * (float)Math.cos(theta) * 50.0f * elapsed;
        float dy = -vy0 * (float)Math.sin(theta) + gravity * dt;
        dt += elapsed;

        if(vx0 < 0.0f) {
            dx *= -1.0f;
        }
        for(int i=0;i<images.length;i++) {
            if(isSliced && i == 1)
                images[i].move(dx * 0.5f, dy);
            else
                images[i].move(dx, dy);
        }
        center.offset(dx, dy);
    }

    @Override
    public void draw(Canvas canvas) {
        if(!isSliced) {
            images[0].draw(canvas);
        } else {
            images[1].draw(canvas);
            images[2].draw(canvas);
        }
    }

    public RectF getRect() {
        return images[0].getRect();
    }

    public void slice(float slope) {
        isSliced = true;
        processSliceEvent();
        setSlicePoints(slope);
    }

    private void processSliceEvent() {
        switch(mode.ordinal()) {
            case 0:
                AudioPlayer.getInstance().playAudio(R.raw.normal_obj_slice);
                GameScene.getInstance().increaseScore();
                break;

            case 1:
                AudioPlayer.getInstance().playAudio(R.raw.killer_obj_slice);
                GameScene.getInstance().increaseFailCount();
                break;

            case 2:
                AudioPlayer.getInstance().playAudio(R.raw.killer_obj_slice);
                GameScene.getInstance().GameOver();
                break;
        }
    }

    private void setSlicePoints(float slope) {
        float offset_w = images[0].getRectWidth() / 2;
        float offset_h = images[0].getRectHeight() / 2;

        if(slope < Float.MAX_VALUE) {
            if(Math.abs(slope) <= 1.0f) {
                slicePoints[0].set(center.x - offset_w, center.y - offset_w * slope);
                slicePoints[1].set(center.x + offset_w, center.y + offset_w * slope);
                divideHorizontal();
            } else {
                slicePoints[0].set(center.x - offset_h / slope, center.y - offset_h);
                slicePoints[1].set(center.x + offset_h / slope, center.y + offset_h);
                divideVertical();
            }
        } else {
            slicePoints[0].set(center.x, center.y - offset_h);
            slicePoints[1].set(center.x, center.y + offset_h);
            divideVertical();
        }

        for(int i=0;i<imagePoints.length/2;i++) {
            images[1].setPoint(i, imagePoints[i]);
            images[2].setPoint(i, imagePoints[i+imagePoints.length/2]);
        }
    }

    private void divideHorizontal() {
        RectF origin = images[0].getRect();

        imagePoints[0].set(slicePoints[0].x, slicePoints[0].y);
        imagePoints[1].set(slicePoints[1].x, slicePoints[1].y);
        imagePoints[2].set(origin.right, origin.bottom);
        imagePoints[3].set(origin.left, origin.bottom);

        imagePoints[4].set(origin.left, origin.top);
        imagePoints[5].set(origin.right, origin.top);
        imagePoints[6].set(slicePoints[1].x, slicePoints[1].y);
        imagePoints[7].set(slicePoints[0].x , slicePoints[0].y);
    }

    private void divideVertical() {
        RectF origin = images[0].getRect();

        imagePoints[0].set(slicePoints[0].x, slicePoints[0].y);
        imagePoints[1].set(origin.right, origin.top);
        imagePoints[2].set(origin.right, origin.bottom);
        imagePoints[3].set(slicePoints[1].x, slicePoints[1].y);

        imagePoints[4].set(origin.left, origin.top);
        imagePoints[5].set(slicePoints[0].x , slicePoints[0].y);
        imagePoints[6].set(slicePoints[1].x, slicePoints[1].y);
        imagePoints[7].set(origin.left, origin.bottom);
    }

    public boolean isSliced() {
        return isSliced;
    }
    public PointF getCenter() { return center; }
}
