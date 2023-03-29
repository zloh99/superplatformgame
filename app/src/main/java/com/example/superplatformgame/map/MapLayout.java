package com.example.superplatformgame.map;

import android.graphics.Rect;

import com.example.superplatformgame.graphics.SpriteSheet;

import java.util.Arrays;

public class MapLayout {
    public static final int TILE_WIDTH_PIXELS = SpriteSheet.TILE_WIDTH_PIXELS;
    public static final int TILE_HEIGHT_PIXELS = SpriteSheet.TILE_HEIGHT_PIXELS;
    public static final int NUMBER_OF_ROW_TILES = 120;
    public static final int NUMBER_OF_COLUMN_TILES = 120;
    private int[][] layout;

    public MapLayout(Tilemap.MapType mapType) {
        initializeLayout(mapType);
    }

    public int[][] getLayout() {
        return layout;
    }

    private void initializeLayout(Tilemap.MapType mapType) {
        int[] ground = {0, 0, 0, 0, 2, 1, 1, 1};
        int[] pillar = {0, 0, 0, 2, 1, 1, 1, 1};
        int[] chasm = {0, 0, 0, 0, 0, 0, 0, 0};
        int[] overhead_block = {0, 0, 0, 0, 2, 1, 1, 1};
        int[] winning_tile = {0, 0, 0, 3, 2, 1, 1, 1};

        if (mapType == Tilemap.MapType.GRASS_MAP) {
            int[][] untransposedMatrix = new int[35][]; // set the map distance from start to end as 25 blocks wide.
            for (int i = 0; i < untransposedMatrix.length; i++) {
                if (i < 7 || i > 27) {
                    untransposedMatrix[i] = ground.clone();
                } else {
                    untransposedMatrix[i] = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
                }
            }
            int numRows = untransposedMatrix.length;
            int numCols = untransposedMatrix[0].length;
            int[][] tempLayout = new int[numRows][numCols]; // initialize tempLayout for untransposedMatrix
            boolean prevWasOverheadBlock = false;

            // generate random rows for all rows in between the first and last 7 rows
            // generate random rows for all rows in between the first and last 7 rows
            for (int i = 7; i < numRows - 7; i++) {
                double rand = Math.random();
                if (rand < 0.3) {
                    tempLayout[i] = chasm;
                    prevWasOverheadBlock = false;
                    if (i + 1 < numRows - 7) { // check if there's still a row after the chasm
                        tempLayout[i + 1] = ground;
                        tempLayout[i - 1] = ground;// generate a ground array before/after chasm
                    }
                } else if (rand < 0.6) {
                    if (prevWasOverheadBlock) {
                        tempLayout[i] = ground;
                        prevWasOverheadBlock = false;
                    } else {
                        tempLayout[i] = overhead_block;
                        prevWasOverheadBlock = true;
                    }
                } else {
                    tempLayout[i] = pillar;
                    prevWasOverheadBlock = false;
                }
            }

            // check for two consecutive chasm rows and replace the first one with ground array
            for (int i = 7; i < numRows - 8; i++) {
                if (Arrays.equals(tempLayout[i], chasm) && Arrays.equals(tempLayout[i + 1], chasm)) {
                    tempLayout[i] = ground;
                    break;
                }
            }

            // copy the first and last 7 rows from the original matrix
            for (int i = 0; i < 7; i++) {
                tempLayout[i] = untransposedMatrix[i];
            }
            for (int i = numRows - 7; i < numRows; i++) {
                // insert winning tile at the end of the map
                tempLayout[i] = (i == numRows - 1) ? winning_tile : untransposedMatrix[i];
            }

            // transpose untransposedMatrix and assign the result to layout
            layout = new int[numCols][numRows];
            for (int i = 0; i < numCols; i++) {
                for (int j = 0; j < numRows; j++) {
                    layout[i][j] = tempLayout[j][i];
                }
            }
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
            } else if (layout[y][x] == 3) {
                return false;
            } else {
                return true;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            //do nothing
        }
        return false;
    }

    public boolean atSpaceship(int x, int y) {
        try {
            if (layout[y][x] == 3) {
                return true;
            } else {
                return false;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            //do nothing
        }
        return false;
    }
}