package kr.ac.tukorea.gamekim2016180014.dragonflight.game;

import android.graphics.Canvas;

import java.util.ArrayList;

import kr.ac.tukorea.gamekim2016180014.dragonflight.framework.CollisionHelper;
import kr.ac.tukorea.gamekim2016180014.dragonflight.framework.GameObject;

public class CollisionChecker implements GameObject {
    @Override
    public void update() {
        MainGame mainGame = MainGame.getInstance();
        ArrayList<GameObject> bullets = mainGame.objectsAt(MainGame.Layer.bullet);
        ArrayList<GameObject> enemies = mainGame.objectsAt(MainGame.Layer.enemy);

        for(GameObject obj : enemies) {
            if((obj instanceof Enemy) == false) {
                continue;
            }
            Enemy enemy = (Enemy)obj;

            boolean collided = false;
            for(GameObject otherObj : bullets) {
                if((otherObj instanceof Bullet) == false) {
                    continue;
                }
                Bullet bullet = (Bullet)otherObj;
                if(CollisionHelper.collides(enemy, bullet)) {
                    mainGame.remove(bullet);
                    boolean dead = enemy.decreaseLife(bullet.getPower());
                    if(dead) {
                        mainGame.remove(enemy);
                        mainGame.score.add(enemy.getScore());
                    }
                    collided = true;
                    return;
                }
            }
            if(collided) {
                continue;
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {

    }
}
