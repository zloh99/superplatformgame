package com.example.superplatformgame;

import android.graphics.Rect;

import com.example.superplatformgame.gameobject.GameObject;

/**
 * This class controls the camera and centers it onto a game object (the player)
 */
public class GameCamera {
    public final Rect DISPLAY_RECT;
    private final int widthPixels;
    private final int heightPixels;
    private double gameToDisplayCoordinateOffsetX;
    private double gameToDisplayCoordinateOffsetY;
    private final double displayCenterX;
    private final double displayCenterY;
    private double gameCenterX;
    private double gameCenterY;
    private final GameObject centerObject;

    public GameCamera(int widthPixels, int heightPixels, GameObject centerObject) {
        this.widthPixels = widthPixels;
        this.heightPixels = heightPixels;
        DISPLAY_RECT = new Rect(0, 0, widthPixels, heightPixels);

        this.centerObject = centerObject;

        displayCenterX = widthPixels/2.0;
        displayCenterY = heightPixels/2.0;
    }


    public void update() {
        gameCenterX = centerObject.getPositionX(); //centerObject - any game object to center the screen around
        gameCenterY = centerObject.getPositionY();

        gameToDisplayCoordinateOffsetX = displayCenterX - gameCenterX;
        gameToDisplayCoordinateOffsetY = displayCenterY - gameCenterY;
    }
    public double gameToDisplayCoordinatesX(double x) {
        return x + gameToDisplayCoordinateOffsetX;
    }

    public double gameToDisplayCoordinatesY(double y) {
        return y + gameToDisplayCoordinateOffsetY;
    }

    public Rect getGameRect() {
        return new Rect(
                (int) (gameCenterX - widthPixels/2),
                (int) (gameCenterY - heightPixels/2),
                (int) (gameCenterX + widthPixels/2),
                (int) (gameCenterY + heightPixels/2)
        );
    }
}
