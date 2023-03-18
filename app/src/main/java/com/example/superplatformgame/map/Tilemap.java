package com.example.superplatformgame.map;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import com.example.superplatformgame.Game;
import com.example.superplatformgame.GameCamera;
import com.example.superplatformgame.gameobject.Player;
import com.example.superplatformgame.graphics.SpriteSheet;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

/**
 * Tilemap is a class that handles the drawing of tile bitmaps onto the canvas, based on a map layout
 * from the class MapLayout. It also contains a method for detecting player collision.
 */
public class Tilemap {

    public enum MapType {
        //add to this enumeration for each map type
        GRASS_MAP
    }
    private MapType mapType;

    private MapLayout mapLayout;

    private int[][] tilemap;

    //this is a 2 dimensional ArrayList to store the invisible rectangle objects drawn over the tilemap for
    //the purposes of collision detection.
    private ArrayList<ArrayList<Rect>> rectLayout;

    private SpriteSheet spriteSheet;

    private Bitmap bitmap;
    private int tileWidth;
    private int tileHeight;
    private Paint paint;

    public Tilemap(SpriteSheet spriteSheet) {
        //initiialise everything
        tileHeight = SpriteSheet.TILE_HEIGHT_PIXELS;
        tileWidth = SpriteSheet.TILE_WIDTH_PIXELS;
        this.mapType = MapType.GRASS_MAP;
        mapLayout = new MapLayout(MapType.GRASS_MAP);
        rectLayout = new ArrayList<ArrayList<Rect>>();

        //randomly select a map type
        MapType chosenMap = pickMap();

        if (chosenMap == MapType.GRASS_MAP) {
            mapLayout = new MapLayout(chosenMap);
            this.spriteSheet = spriteSheet;
            bitmap = spriteSheet.getGrassTileBitmap();
        }

        //initialise a paint object for the purpose of testing (in case we need to see the invisible rectangles)
        paint = new Paint();
        paint.setColor(Color.RED);
    }

    public void draw(Canvas canvas, GameCamera gameCamera) {
        //get map layout in the form of a 2 dimensional list
        int[][] layout = mapLayout.getLayout();

        //add rows and columns to the rectLayout ArrayList to make sure its the same size as our maplayout (to store the invisible rectangles in)
        for (int i = 0; i < mapLayout.getLayoutTileHeight(layout); i++) {
            // Initialize each column for this row
            ArrayList<Rect> row = new ArrayList<Rect>(mapLayout.getLayoutTileWidth(layout));
            for (int j = 0; j < mapLayout.getLayoutTileWidth(layout); j++) {
                row.add(null); // add null values for now since we are initialising an empty ArrayList
            }
            rectLayout.add(row);
        }

        //iterating through the mapLayout list and drawing the corresponding tile
        for (int row = 0; row < layout.length; row++) {
            for (int col = 0; col < layout[row].length; col++) {
                int tileIdx = layout[row][col];
                int tileX = col * tileWidth;
                int tileY = row * tileHeight;
                Rect srcRect = new Rect(
                        tileIdx * tileWidth,
                        0,
                        (tileIdx + 1) * tileWidth,
                        tileHeight
                );
                Rect dstRect = new Rect(
                        (int) gameCamera.gameToDisplayCoordinatesX(tileX),
                        (int) gameCamera.gameToDisplayCoordinatesY(tileY),
                        (int) gameCamera.gameToDisplayCoordinatesX(tileX + tileWidth),
                        (int) gameCamera.gameToDisplayCoordinatesY(tileY + tileHeight)
                );
                //drawing tile to the canvas
                canvas.drawBitmap(bitmap, srcRect, dstRect, null);
                //and drawing invisible rectangle to the same location as the tile
                rectLayout.get(row).set(col, dstRect);
            }
        }

        //uncomment to show the tilemap as Rect objects
/*        for (int row = 0; row < rectLayout.size(); row++) {
            for (int col = 0; col < rectLayout.get(row).size(); col++) {
                Rect tileRect = rectLayout.get(row).get(col);
                if(tileRect != null) {
                    if(mapLayout.isSolid(col, row)) {
                        canvas.drawRect(tileRect, paint);
                    }
                }
            }
        }*/
    }

    private MapType pickMap() {
        //randomly pick a map type
        int pick = new Random().nextInt(MapType.values().length);
        return MapType.values()[pick];
    }

    public ArrayList<ArrayList<Rect>> getRectLayout() {return rectLayout;}

    public MapLayout getMapLayout() {return mapLayout;}

    //isColliding checks for collision between a rectangle drawn over the player sprite, against all the rectangles in the rectLayout ArrayList

   public boolean isColliding(Player player, GameCamera gameCamera, boolean checkX, boolean checkY) {
        //get player rectangle
        Rect playerRect = player.getFuturePlayerRect(gameCamera);

        //iterate through every invisible rectangle in rectLayout and check to see if there is intersection between them and the player rectangle
        for (int row = 0; row < rectLayout.size(); row++) {
            for (int col = 0; col < rectLayout.get(row).size(); col++) {
                Rect tileRect = rectLayout.get(row).get(col);
                if(tileRect != null) {
                    if (checkX && tileRect.intersect(playerRect.left, playerRect.top, playerRect.right, playerRect.bottom)) {
                        //checkX is just a flag to indicate to check for X collision
                        if(mapLayout.isSolid(col, row)) {
                            //collision detected in x direction
                            //Log.d("Tilemap.java", "Collision detected in x direction");
                            return true;
                        }
                    }
                    if (checkY && tileRect.intersect(playerRect.left, playerRect.top, playerRect.right, playerRect.bottom)) {
                        //checkY is just a flag to indicate to check for X collision
                        if(mapLayout.isSolid(col, row)) {
                            //collision detected in y direction
                            //Log.d("Tilemap.java", "Collision detected in y direction");
                            return true;
                        }
                    }
                }
            }
        }
        //collision not detected
        //Log.d("Tilemap.java", "Collision NOT detected");
        return false;
    }
}
