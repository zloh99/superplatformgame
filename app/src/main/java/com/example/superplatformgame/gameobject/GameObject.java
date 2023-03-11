package com.example.superplatformgame.gameobject;


import android.graphics.Canvas;

import com.example.superplatformgame.GameCamera;

/**
 * GameObject is an abstract class which is the foundation of all
 * world objects in the game.
 */
public abstract class GameObject {
    protected double positionX, positionY = 0.0;
    protected double velocityX, velocityY = 0.0;
    protected double directionX = 1.0;

    protected double directionY = 0.0;

    public GameObject(double positionX, double positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }



    public abstract void draw(Canvas canvas, GameCamera gameCamera); //abstract method - inherited class must implement the same method

    public abstract void update();

    public double getPositionX() { return positionX;
    }

    public double getPositionY() { return positionY;
    }

    public static double getDistanceBetweenObjects(GameObject obj1, GameObject obj2) {
        //Find absolute distance using pythagoras theorem
        return Math.sqrt(
                Math.pow(obj2.getPositionX() - obj1.getPositionX(), 2) +
                        Math.pow(obj2.getPositionY() - obj1.getPositionY(), 2)
        );
    }

    public double getDirectionX() {
        return directionX;
    }

    public double getDirectionY() {
        return directionY;
    }
}
