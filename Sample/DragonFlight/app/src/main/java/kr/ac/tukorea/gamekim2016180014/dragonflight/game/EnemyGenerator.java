package kr.ac.tukorea.gamekim2016180014.dragonflight.game;

import android.graphics.Canvas;

import java.util.Random;

import kr.ac.tukorea.gamekim2016180014.dragonflight.R;
import kr.ac.tukorea.gamekim2016180014.dragonflight.framework.GameObject;
import kr.ac.tukorea.gamekim2016180014.dragonflight.framework.Metrics;

public class EnemyGenerator implements GameObject {
    private static final float INITIAL_SPAWN_INTERVAL = 2.0f;
    private final float fallSpeed;
    private final float spawnInterval;
    private float elapsedTime;
    private Random random;
    private int wave;

    public EnemyGenerator() {
        fallSpeed = Metrics.size(R.dimen.enemy_initial_speed);
        spawnInterval = INITIAL_SPAWN_INTERVAL;
        Enemy.size = Metrics.width / 5.0f;
        random = new Random();
        wave = 0;
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
        wave += 1;
        float offset = Metrics.width / 10;
        for(int i=0;i<10;i+=2) {
            int level = wave / 5 - random.nextInt(3);
            level = Math.max(Enemy.MIN_LEVEL, level);
            level = Math.min(Enemy.MAX_LEVEL, level);
            Enemy enemy = Enemy.get(level, offset * i + Enemy.size / 2.0f, fallSpeed);
            MainGame.getInstance().add(MainGame.Layer.enemy, enemy);
        }
    }

    @Override
    public void draw(Canvas canvas) {

    }
}
