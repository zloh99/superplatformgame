package com.example.superplatformgame;

import android.graphics.Rect;
import android.graphics.Region;
import android.util.Log;

import com.example.superplatformgame.gameobject.GameObject;
import com.example.superplatformgame.graphics.SpriteSheet;
import com.example.superplatformgame.map.MapLayout;

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
    private int mapBottomY;

    public GameCamera(int widthPixels, int heightPixels, GameObject centerObject, MapLayout mapLayout) {
        this.widthPixels = widthPixels;
        this.heightPixels = heightPixels;
        DISPLAY_RECT = new Rect(0, 0, widthPixels, heightPixels);

        this.centerObject = centerObject;

        displayCenterX = widthPixels/2.0;
        displayCenterY = heightPixels/2.0;

        mapBottomY = (int) gameToDisplayCoordinatesY(mapLayout.getLayoutTileHeight(mapLayout.getLayout()) * SpriteSheet.TILE_HEIGHT_PIXELS);
    }

    public void update() {
        if (getPlayerLeft() <= 0) {
            //if player is towards the leftmost side of the map, then do not fix camera on player's X axis
            gameCenterX = centerObject.getPositionX(); //centerObject - any game object to center the screen around
            gameCenterY = centerObject.getPositionY();

            gameToDisplayCoordinateOffsetY = displayCenterY - gameCenterY;
        } else if (getPlayerBottom() >= mapBottomY) {
            //if player camera is at the bounds of the bottom of the game world, then do not fix camera on player's Y axis
            gameCenterX = centerObject.getPositionX();
            gameCenterY = centerObject.getPositionY();

            gameToDisplayCoordinateOffsetX = displayCenterX - gameCenterX;
        } else if (getPlayerLeft() <= 0 && getPlayerBottom() >= mapBottomY) {
            //if player camera is at the leftmost side and bounds the bottom of the game world, do not fix camera on player's X and Y axis
            gameCenterX = centerObject.getPositionX();
            gameCenterY = centerObject.getPositionY();
        }
        else {
            //if player is away from the leftmost side of the map, and from the bottom of the map, then fix camera on player.
            gameCenterX = centerObject.getPositionX();
            gameCenterY = centerObject.getPositionY();

            gameToDisplayCoordinateOffsetX = displayCenterX - gameCenterX;
            gameToDisplayCoordinateOffsetY = displayCenterY - gameCenterY;
        }
        //Log.d("GameCamera.java", "getPlayerBottom(): " + getPlayerBottom());
        //Log.d("GameCamera.java", "mapBottomY: " + mapBottomY);
    }

    //remember to put these methods over any X and Y coordinates that are not fixed to the camera, these 2 methods are to account for the offset
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

    public int getScreenRight() {
        return (int) gameCenterX + widthPixels/2 + 200;
    }

    public int getPlayerLeft() {
        return (int) centerObject.getPositionX() - widthPixels/2;
    }

    public int getPlayerBottom() {
        return (int) centerObject.getPositionY() + heightPixels/2;
    }

    public int getMapBottomY() {
        return (int) mapBottomY;
    }

}
