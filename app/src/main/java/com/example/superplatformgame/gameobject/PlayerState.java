package com.example.superplatformgame.gameobject;

import android.util.Log;

import com.example.superplatformgame.GameCamera;
import com.example.superplatformgame.map.Tilemap;

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

    public enum AirborneState {
        AIR,
        GROUND
    }

    private Player player;
    private State state;
    private AirborneState airborneState;

    public PlayerState(Player player) {
        this.player = player;
        this.state = State.NOT_MOVING_RIGHT;
        this.airborneState = AirborneState.AIR;
    }

    public State getState() {
        return state;
    }

    public AirborneState getAirborneState() {
        return airborneState;
    }

    public void setAirborneState(boolean bool) {
        //true for ground, false for sky
        if (bool) {
            airborneState = AirborneState.GROUND;
        }
        else airborneState = AirborneState.AIR;
    }

    public void update(Tilemap tilemap, GameCamera gameCamera) {
        switch (state) {
            case NOT_MOVING_RIGHT:
            case NOT_MOVING_LEFT:
                if (player.velocityX > 0) {
                    state = State.STARTED_MOVING_RIGHT;
                    //Log.d("PlayerState.java", "STARTED_MOVING_RIGHT");
                }
                else if (player.velocityX < 0) {
                    state = State.STARTED_MOVING_LEFT;
                    //Log.d("PlayerState.java", "STARTED_MOVING_LEFT");
                }
                break;
            case STARTED_MOVING_RIGHT:
            case STARTED_MOVING_LEFT:
                if (player.velocityX > 0) {
                    state = State.IS_MOVING_RIGHT;
                    //Log.d("PlayerState.java", "IS_MOVING_RIGHT");
                }
                else if (player.velocityX < 0) {
                    state = State.IS_MOVING_LEFT;
                    //Log.d("PlayerState.java", "IS_MOVING_LEFT");
                }
                break;
            case IS_MOVING_LEFT:
                if (player.velocityX == 0) {
                    state = State.NOT_MOVING_LEFT;
                    //Log.d("PlayerState.java", "NOT_MOVING_LEFT");
                }
                break;
            case IS_MOVING_RIGHT:
                if (player.velocityX == 0) {
                    state = State.NOT_MOVING_RIGHT;
                    //Log.d("PlayerState.java", "NOT_MOVING_RIGHT");
                }
                break;
            default:
                break;
        }
/*        switch (airborneState) {
            case GROUND:
                if(tilemap.isColliding(player, gameCamera, false, true)) {
                    airborneState = AirborneState.GROUND;
                    Log.d("PlayerState.java", "GROUND");
                }
                else {
                    airborneState = AirborneState.AIR;
                    Log.d("PlayerState.java", "AIR");
                }
            case AIR:
                if(tilemap.isColliding(player, gameCamera, false, true)) {
                    airborneState = AirborneState.GROUND;
                    Log.d("PlayerState.java", "GROUND");
                }
                else {
                    airborneState = AirborneState.AIR;
                    Log.d("PlayerState.java", "AIR");
                }
        }*/
    }
}
