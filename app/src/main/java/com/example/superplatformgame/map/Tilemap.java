package com.example.superplatformgame.map;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import com.example.superplatformgame.Game;
import com.example.superplatformgame.GameCamera;
import com.example.superplatformgame.gameobject.Player;
import com.example.superplatformgame.graphics.SpriteSheet;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class Tilemap {

    public enum MapType {
        GRASS_MAP
    }
    private MapType mapType;

    private MapLayout mapLayout;

    private int[][] tilemap;
    private ArrayList<ArrayList<Rect>> rectLayout;

    private SpriteSheet spriteSheet;

    private Bitmap bitmap;
    private int tileWidth;
    private int tileHeight;

    public Tilemap(SpriteSheet spriteSheet) {
        tileHeight = SpriteSheet.TILE_HEIGHT_PIXELS;
        tileWidth = SpriteSheet.TILE_WIDTH_PIXELS;
        this.mapType = MapType.GRASS_MAP;
        mapLayout = new MapLayout(MapType.GRASS_MAP);
        rectLayout = new ArrayList<ArrayList<Rect>>();

        MapType chosenMap = pickMap();

        if (chosenMap == MapType.GRASS_MAP) {
            mapLayout = new MapLayout(chosenMap);
            this.spriteSheet = spriteSheet;
            bitmap = spriteSheet.getGrassTileBitmap();
        }
    }

    public void draw(Canvas canvas, GameCamera gameCamera) {
        int[][] layout = mapLayout.getLayout();
        for (int i = 0; i < mapLayout.getLayoutTileHeight(layout); i++) {
            // Initialize each column for this row
            ArrayList<Rect> row = new ArrayList<Rect>(mapLayout.getLayoutTileWidth(layout));
            for (int j = 0; j < mapLayout.getLayoutTileWidth(layout); j++) {
                row.add(null); // add null values for now
            }
            rectLayout.add(row);
        }

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
                canvas.drawBitmap(bitmap, srcRect, dstRect, null);
                rectLayout.get(row).set(col, dstRect);
            }
        }
    }

    private MapType pickMap() {
        //randomly pick a map type
        int pick = new Random().nextInt(MapType.values().length);
        return MapType.values()[pick];
    }

    public ArrayList<ArrayList<Rect>> getRectLayout() {return rectLayout;}

   public boolean isColliding(Player player, GameCamera gameCamera, boolean checkX, boolean checkY) {
        Rect playerRect = player.getPlayerRect(gameCamera);
        for (int row = 0; row < rectLayout.size(); row++) {
            for (int col = 0; col < rectLayout.get(row).size(); col++) {
                Rect tileRect = rectLayout.get(row).get(col);
                if(tileRect != null) {
                    if (checkX && tileRect.intersect(playerRect.left, playerRect.top, playerRect.right, playerRect.bottom)) {
                        if(mapLayout.isSolid(col, row)) {
                            //collision detected in x direction
                            //Log.d("Tilemap.java", "Collision detected in x direction");
                            return true;
                        }
                    }
                    if (checkY && tileRect.intersect(playerRect.left, playerRect.top, playerRect.right, playerRect.bottom)) {
                        if(mapLayout.isSolid(col, row)) {
                            //collision detected in y direction
                            //Log.d("Tilemap.java", "Collision detected in y direction");
                            return true;
                        }
                    }
                }
            }
        }
        Log.d("Tilemap.java", "Collision NOT detected");
        return false;
    }
}
