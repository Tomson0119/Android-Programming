package kr.ac.tukorea.gamekim2016180014.dragonflight.game;

import android.graphics.Canvas;

import kr.ac.tukorea.gamekim2016180014.dragonflight.R;
import kr.ac.tukorea.gamekim2016180014.dragonflight.framework.GameObject;
import kr.ac.tukorea.gamekim2016180014.dragonflight.framework.Metrics;

public class EnemyGenerator implements GameObject {
    private static final float INITIAL_SPAWN_INTERVAL = 2.0f;
    private final float fallSpeed;
    private final float spawnInterval;
    private float elapsedTime;

    public EnemyGenerator() {
        fallSpeed = Metrics.size(R.dimen.enemy_initial_speed);
        spawnInterval = INITIAL_SPAWN_INTERVAL;
        Enemy.size = Metrics.width / 5.0f;
    }

    @Override
    public void update() {
        float frameTime = MainGame.getInstance().frameTime;
        elapsedTime += frameTime;
        if(elapsedTime > spawnInterval) {
            spawnEnemy();
            elapsedTime -= spawnInterval;
        }
    }

    private void spawnEnemy() {
        float offset = Metrics.width / 5.0f;

        for(int i=0;i<5;i++) {
            Enemy enemy = new Enemy(offset * i + Enemy.size / 2.0f, fallSpeed);
            MainGame.getInstance().add(enemy);
        }
    }

    @Override
    public void draw(Canvas canvas) {

    }
}
