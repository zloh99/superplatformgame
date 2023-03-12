package com.example.superplatformgame.gameobject;

import android.util.Log;

/**
 * Class that records different motion states of the player
 */
public class PlayerState {

    public enum State {
        NOT_MOVING_LEFT,
        STARTED_MOVING_LEFT,
        IS_MOVING_LEFT,
        NOT_MOVING_RIGHT,
        STARTED_MOVING_RIGHT,
        IS_MOVING_RIGHT
    }

    private Player player;
    private State state;

    public PlayerState(Player player) {
        this.player = player;
        this.state = State.NOT_MOVING_RIGHT;
    }

    public State getState() {
        return state;
    }

    public void update() {
        switch (state) {
            case NOT_MOVING_RIGHT:
            case NOT_MOVING_LEFT:
                if (player.velocityX > 0) {
                    state = State.STARTED_MOVING_RIGHT;
                    Log.d("PlayerState.java", "STARTED_MOVING_RIGHT");
                }
                else if (player.velocityX < 0) {
                    state = State.STARTED_MOVING_LEFT;
                    Log.d("PlayerState.java", "STARTED_MOVING_LEFT");
                }
                break;
            case STARTED_MOVING_RIGHT:
            case STARTED_MOVING_LEFT:
                if (player.velocityX > 0) {
                    state = State.IS_MOVING_RIGHT;
                    Log.d("PlayerState.java", "IS_MOVING_RIGHT");
                }
                else if (player.velocityX < 0) {
                    state = State.IS_MOVING_LEFT;
                    Log.d("PlayerState.java", "IS_MOVING_LEFT");
                }
                break;
            case IS_MOVING_LEFT:
                if (player.velocityX == 0) {
                    state = State.NOT_MOVING_LEFT;
                    Log.d("PlayerState.java", "NOT_MOVING_LEFT");
                }
                break;
            case IS_MOVING_RIGHT:
                if (player.velocityX == 0) {
                    state = State.NOT_MOVING_RIGHT;
                    Log.d("PlayerState.java", "NOT_MOVING_RIGHT");
                }
                break;
            default:
                break;
        }
    }
}
