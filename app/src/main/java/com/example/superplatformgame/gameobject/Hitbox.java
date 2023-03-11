package com.example.superplatformgame.gameobject;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.superplatformgame.GameCamera;

/**
 * Hitbox is an abstract class which implements a draw method from
 * GameObject for drawing the hitbox of the object
 */
public abstract class Hitbox extends GameObject {
    protected double radius;
    protected Paint paint;

    public Hitbox(Context context, int color, double positionX, double positionY, double radius) {
        super(positionX, positionY);
        this.radius = radius;

        //Set color of circle
        paint = new Paint();
        paint.setColor(color);
    }

    /**
     * isColliding will be a method to check for collision
     * @param
     * @param
     * @return
     */
//    public static boolean isColliding(Hitbox obj1, Hitbox obj2) {
 //       double distance = getDistanceBetweenObjects(obj1, obj2);
  //      double distanceToCollision = obj1.getRadius() + obj2.getRadius(); //if distance between 2 objects < sum of their radii, then they have collided
  //      if (distance < distanceToCollision) return true;
   //     else return false;

    private double getRadius() {
        return radius;
    }

    public void draw(Canvas canvas, GameCamera gameCamera) {
        canvas.drawCircle(
                (float) gameCamera.gameToDisplayCoordinatesX(positionX),
                (float) gameCamera.gameToDisplayCoordinatesY(positionY),
                (float) radius,
                paint
        );
    }
}
