package com.example.superplatformgame.map;

import android.graphics.Rect;

import com.example.superplatformgame.graphics.SpriteSheet;

public class MapLayout {
    public static final int TILE_WIDTH_PIXELS = SpriteSheet.TILE_WIDTH_PIXELS;
    public static final int TILE_HEIGHT_PIXELS = SpriteSheet.TILE_HEIGHT_PIXELS;
    public static final int NUMBER_OF_ROW_TILES = 60;
    public static final int NUMBER_OF_COLUMN_TILES = 60;
    private int[][] layout;

    public MapLayout(Tilemap.MapType mapType) {
        initializeLayout(mapType);
    }
    public int[][] getLayout() {return layout;};
    private void initializeLayout(Tilemap.MapType mapType) {
        if (mapType == Tilemap.MapType.GRASS_MAP){
            layout = new int[][]{
                    {2,0,0,0,0,0,0,2,2,2,0,0,0,0,0,0,0},
                    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                    {1,0,0,2,2,2,2,0,0,0,0,0,0,0,0,0,0},
                    {1,2,2,1,1,1,1,2,2,2,2,2,2,2,2,2,2},
                    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            };
        }
    }

    public int getLayoutTileHeight(int[][] layout) {
        return layout.length;
    }

    public int getLayoutTileWidth(int[][] layout) {
        int counter = 0;
        for (int i = 0; i < 1; i++) {
            counter += layout[i].length;
        }
        return counter;
    }


    public boolean isSolid(int x, int y) {
        //for grass tile bitmap, anything below 1 is non-solid
        try {
            if (layout[y][x] < 1) {
                return false;
            }
            else {
                return true;
            }
        }
        catch (ArrayIndexOutOfBoundsException e) {
            //do nothing
        }
        return false;
    }

}
