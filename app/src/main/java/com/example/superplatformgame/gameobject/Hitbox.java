package com.example.superplatformgame.gameobject;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.superplatformgame.GameCamera;
import com.example.superplatformgame.map.MapLayout;
import com.example.superplatformgame.map.Tilemap;

/**
 * Hitbox is an abstract class that inherits from gameObject. Currently not in use, do not modify. Make sure to
 * extend all other game objects from this class.
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

//    public static boolean isColliding(Hitbox obj1, Hitbox obj2) {
 //       double distance = getDistanceBetweenObjects(obj1, obj2);
  //      double distanceToCollision = obj1.getRadius() + obj2.getRadius(); //if distance between 2 objects < sum of their radii, then they have collided
  //      if (distance < distanceToCollision) return true;
   //     else return false;

/*    private double getRadius() {
        return radius;
    }*/

    //public abstract void update(GameCamera gameCamera);

    public abstract void update(GameCamera gameCamera, Tilemap tileMap);

    public void draw(Canvas canvas, GameCamera gameCamera) {
        canvas.drawCircle(
                (float) gameCamera.gameToDisplayCoordinatesX(positionX),
                (float) gameCamera.gameToDisplayCoordinatesY(positionY),
                (float) radius,
                paint
        );
    }
}
