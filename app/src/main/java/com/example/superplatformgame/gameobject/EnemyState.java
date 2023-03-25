package com.example.superplatformgame.gameobject;

import com.example.superplatformgame.GameCamera;
import com.example.superplatformgame.map.Tilemap;

public class EnemyState extends HitboxState {

    private Enemy enemy;
    private HitboxState.State state;

    public EnemyState(Enemy enemy) {
        this.enemy = enemy;
        this.state = HitboxState.State.NOT_MOVING_RIGHT;
    }

    public HitboxState.State getState() {
        return state;
    }

    public void update(Tilemap tilemap, GameCamera gameCamera) {
        switch (state) {
            case NOT_MOVING_RIGHT:
            case NOT_MOVING_LEFT:
                if (enemy.velocityX > 0) {
                    state = HitboxState.State.STARTED_MOVING_RIGHT;
                    //Log.d("PlayerState.java", "STARTED_MOVING_RIGHT");
                }
                else if (enemy.velocityX < 0) {
                    state = HitboxState.State.STARTED_MOVING_LEFT;
                    //Log.d("PlayerState.java", "STARTED_MOVING_LEFT");
                }
                break;
            case STARTED_MOVING_RIGHT:
            case STARTED_MOVING_LEFT:
                if (enemy.velocityX > 0) {
                    state = HitboxState.State.IS_MOVING_RIGHT;
                    //Log.d("PlayerState.java", "IS_MOVING_RIGHT");
                }
                else if (enemy.velocityX < 0) {
                    state = HitboxState.State.IS_MOVING_LEFT;
                    //Log.d("PlayerState.java", "IS_MOVING_LEFT");
                }
                break;
            case IS_MOVING_LEFT:
                if (enemy.velocityX == 0) {
                    state = HitboxState.State.NOT_MOVING_LEFT;
                    //Log.d("PlayerState.java", "NOT_MOVING_LEFT");
                }
                break;
            case IS_MOVING_RIGHT:
                if (enemy.velocityX == 0) {
                    state = HitboxState.State.NOT_MOVING_RIGHT;
                    //Log.d("PlayerState.java", "NOT_MOVING_RIGHT");
                }
                break;
            default:
                break;
        }
    }
}
