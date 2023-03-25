package com.example.superplatformgame.gameobject;

import android.content.Context;

public class Bird extends Enemy{
    /**
     * Constructor for enemy class
     *
     * @param context
     * @param positionX
     * @param positionY
     * @param width
     * @param velocity
     * @param animator
     */
    public Bird(Context context, double positionX, double positionY, double width, double velocity, Animator animator) {
        super(context, positionX, positionY, width, velocity, animator);
        setGravity(0);
        setDamageable(true);
        setScorePoints(50);
    }
}
