package com.example.superplatformgame.gameobject;

import android.content.Context;

public class Saw extends Enemy{
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
    public Saw(Context context, double positionX, double positionY, double width, double velocity, Animator animator) {
        super(context, positionX, positionY, width, velocity, animator);
        setGravity(5);
        setDamageable(false);
        setScorePoints(100);
    }
}
