package com.example.superplatformgame.gameobject;

public abstract class HitboxState {
    public enum State {
        NOT_MOVING_LEFT,
        STARTED_MOVING_LEFT,
        IS_MOVING_LEFT,
        NOT_MOVING_RIGHT,
        STARTED_MOVING_RIGHT,
        IS_MOVING_RIGHT
    }

    public abstract State getState();
}
