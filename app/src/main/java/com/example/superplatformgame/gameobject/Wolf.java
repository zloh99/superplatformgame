package com.example.superplatformgame.gameobject;

import static com.example.superplatformgame.gameobject.Enemy.*;

import android.content.Context;
import android.graphics.Canvas;

import com.example.superplatformgame.GameCamera;
import com.example.superplatformgame.map.Tilemap;

public class Wolf extends Enemy{
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
    private Animator animator;
    
    public Wolf(Context context, double positionX, double positionY, double width, double velocity, Animator animator) {
        super(context, positionX, positionY, width, velocity, animator);
        setGravity(5);
        setDamageable(true);
        setScorePoints(20);
    }

    public void draw(Canvas canvas, GameCamera gameCamera) {
        //animator draw method to draw sprite onto player location
        animator.drawEnemy(canvas, gameCamera, this);

//        paint = new Paint();
//        paint.setColor(Color.RED);
//        canvas.drawRect(getPlayerRect(gameCamera), paint);
    }
}
