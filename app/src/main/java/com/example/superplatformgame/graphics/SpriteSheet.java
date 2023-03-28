package com.example.superplatformgame.graphics;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.util.DisplayMetrics;

import com.example.superplatformgame.R;

/**
 * Class responsible for returning specific sprite we are interested in, which we then add to the Sprite Object
 * The sprite object is then returned to the Player object, which then draws it to the screen
 */
public class SpriteSheet {

    public static final int TILE_SCALE_FACTOR = 5;
    public static final int SPRITE_WIDTH_PIXELS = 96;
    public static final int SPRITE_HEIGHT_PIXELS = 120;

    public static final int ENEMY_WIDTH_PIXELS = 32 * TILE_SCALE_FACTOR;

    public static final int ENEMY_HEIGHT_PIXELS = 32 * TILE_SCALE_FACTOR;
    public static final int SKYBOX_WIDTH_PIXELS = 256;
    public static final int SKYBOX_HEIGHT_PIXELS = 128;

    public static final int GRASSTILES_WIDTH_PIXELS = 48;

    public static final int GRASSTILES_HEIGHT_PIXELS = 32;
    public static final int TILE_WIDTH_PIXELS = GRASSTILES_WIDTH_PIXELS * TILE_SCALE_FACTOR;
    public static final int TILE_HEIGHT_PIXELS = GRASSTILES_HEIGHT_PIXELS * TILE_SCALE_FACTOR;
    private Bitmap scaledSawBitmapLeft;
    private Bitmap scaledSawBitmapRight;
    private Bitmap scaledBirdBitmapLeft;
    private Bitmap scaledBirdBitmapRight;
    private Bitmap scaledWolfBitmapRight;
    private Bitmap scaledWolfBitmapLeft;
    //bitmap = 2d array of pixels, with each pixel having 3 values: R, G, and B
    private Bitmap playerBitmapRight;
    private Bitmap playerBitmapLeft;

    private Bitmap wolfBitmapRight;

    private Bitmap wolfBitmapLeft;

    private Bitmap sawBitmapRight;

    private Bitmap sawBitmapLeft;

    private Bitmap birdBitmapRight;

    private Bitmap birdBitmapLeft;
    private Bitmap skyBitmap;;
    private Bitmap scaledSkyBitmap;

    private Bitmap grassTileBitmap;
    private Bitmap scaledGrassTileBitmap;
    private Matrix matrix;

    public SpriteSheet(Context context) {

        //load bitmap for the player sprite when facing right. load another of the same bitmap and reverse it, for player sprite facing left.
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inScaled = false;
        playerBitmapRight = BitmapFactory.decodeResource(context.getResources(), R.drawable.green_alien_big, bitmapOptions);
        this.matrix = new Matrix();
        matrix.preScale(-1, 1);
        playerBitmapLeft = Bitmap.createBitmap(playerBitmapRight, 0, 0, playerBitmapRight.getWidth(), playerBitmapRight.getHeight(), matrix, true);

        //load bitmap for the wolf enemy sprite when facing right. load another of the same bitmap and reverse it, for wolf sprite facing left.
        BitmapFactory.Options bitmapOptionsWolf = new BitmapFactory.Options();
        bitmapOptionsWolf.inScaled = false;
        wolfBitmapRight = BitmapFactory.decodeResource(context.getResources(), R.drawable.wolf, bitmapOptionsWolf);
        scaledWolfBitmapRight = scaleBitmap(wolfBitmapRight,TILE_SCALE_FACTOR*wolfBitmapRight.getHeight(), TILE_SCALE_FACTOR*wolfBitmapRight.getWidth());
        this.matrix = new Matrix();
        matrix.preScale(-1, 1);
        wolfBitmapLeft = Bitmap.createBitmap(wolfBitmapRight, 0, 0, wolfBitmapRight.getWidth(), wolfBitmapRight.getHeight(), matrix, true);
        scaledWolfBitmapLeft = scaleBitmap(wolfBitmapLeft,TILE_SCALE_FACTOR*wolfBitmapLeft.getHeight(), TILE_SCALE_FACTOR*wolfBitmapLeft.getWidth());

        //load bitmap for the saw enemy sprite when facing right. load another of the same bitmap and reverse it, for saw sprite facing left.
        BitmapFactory.Options bitmapOptionsSaw = new BitmapFactory.Options();
        bitmapOptionsSaw.inScaled = false;
        sawBitmapRight = BitmapFactory.decodeResource(context.getResources(), R.drawable.saw, bitmapOptionsSaw);
        scaledSawBitmapRight = scaleBitmap(sawBitmapRight,TILE_SCALE_FACTOR*sawBitmapRight.getHeight(), TILE_SCALE_FACTOR*sawBitmapRight.getWidth());
        this.matrix = new Matrix();
        matrix.preScale(-1, 1);
        sawBitmapLeft = Bitmap.createBitmap(sawBitmapRight, 0, 0, sawBitmapRight.getWidth(), sawBitmapRight.getHeight(), matrix, true);
        scaledSawBitmapLeft = scaleBitmap(sawBitmapLeft,TILE_SCALE_FACTOR*sawBitmapLeft.getHeight(), TILE_SCALE_FACTOR*sawBitmapLeft.getWidth());

        //load bitmap for the bird enemy sprite when facing right. load another of the same bitmap and reverse it, for bird enemy sprite facing left.
        BitmapFactory.Options bitmapOptionsBird = new BitmapFactory.Options();
        bitmapOptionsBird.inScaled = false;
        birdBitmapRight = BitmapFactory.decodeResource(context.getResources(), R.drawable.bird, bitmapOptionsBird);
        scaledBirdBitmapRight = scaleBitmap(birdBitmapRight,TILE_SCALE_FACTOR*birdBitmapRight.getHeight(), TILE_SCALE_FACTOR*birdBitmapRight.getWidth());
        this.matrix = new Matrix();
        matrix.preScale(-1, 1);
        birdBitmapLeft = Bitmap.createBitmap(birdBitmapRight, 0, 0, birdBitmapRight.getWidth(), birdBitmapRight.getHeight(), matrix, true);
        scaledBirdBitmapLeft = scaleBitmap(birdBitmapLeft,TILE_SCALE_FACTOR*birdBitmapLeft.getHeight(), TILE_SCALE_FACTOR*birdBitmapLeft.getWidth());

        //create and scale sky bitmap to 10 times larger so it can cover the whole screen
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        skyBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.skybox_blue, bitmapOptions);
        scaledSkyBitmap = scaleBitmap(skyBitmap,10*SKYBOX_HEIGHT_PIXELS, 10*SKYBOX_WIDTH_PIXELS);

        //create grass tiles bitmap and scale it up so it won't be so small.
        grassTileBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.grass_tiles_new, bitmapOptions);
        scaledGrassTileBitmap = scaleBitmap(grassTileBitmap,TILE_SCALE_FACTOR*grassTileBitmap.getHeight(), TILE_SCALE_FACTOR*grassTileBitmap.getWidth());
    }

    //get methods for player sprite bitmap
    public Sprite[] getPlayerSpriteArray() {
        //method for indexing and selecting parts of the bitmap for the player's sprite
        Sprite[] spriteArray = new Sprite[11];
        spriteArray[0] = new Sprite(this, new Rect(0*SPRITE_WIDTH_PIXELS, 0, 1*SPRITE_WIDTH_PIXELS, SPRITE_HEIGHT_PIXELS));
        spriteArray[1] = new Sprite(this, new Rect(1*SPRITE_WIDTH_PIXELS, 0, 2*SPRITE_WIDTH_PIXELS, SPRITE_HEIGHT_PIXELS));
        spriteArray[2] = new Sprite(this, new Rect(2*SPRITE_WIDTH_PIXELS, 0, 3*SPRITE_WIDTH_PIXELS, SPRITE_HEIGHT_PIXELS));
        spriteArray[3] = new Sprite(this, new Rect(3*SPRITE_WIDTH_PIXELS, 0, 4*SPRITE_WIDTH_PIXELS, SPRITE_HEIGHT_PIXELS));
        spriteArray[4] = new Sprite(this, new Rect(4*SPRITE_WIDTH_PIXELS, 0, 5*SPRITE_WIDTH_PIXELS, SPRITE_HEIGHT_PIXELS));
        spriteArray[5] = new Sprite(this, new Rect(5*SPRITE_WIDTH_PIXELS, 0, 6*SPRITE_WIDTH_PIXELS, SPRITE_HEIGHT_PIXELS));
        spriteArray[6] = new Sprite(this, new Rect(6*SPRITE_WIDTH_PIXELS, 0, 7*SPRITE_WIDTH_PIXELS, SPRITE_HEIGHT_PIXELS));
        spriteArray[7] = new Sprite(this, new Rect(7*SPRITE_WIDTH_PIXELS, 0, 8*SPRITE_WIDTH_PIXELS, SPRITE_HEIGHT_PIXELS));
        spriteArray[8] = new Sprite(this, new Rect(8*SPRITE_WIDTH_PIXELS, 0, 9*SPRITE_WIDTH_PIXELS, SPRITE_HEIGHT_PIXELS));
        spriteArray[9] = new Sprite(this, new Rect(9*SPRITE_WIDTH_PIXELS, 0, 10*SPRITE_WIDTH_PIXELS, SPRITE_HEIGHT_PIXELS));
        spriteArray[10] = new Sprite(this, new Rect(10*SPRITE_WIDTH_PIXELS, 0, 11*SPRITE_WIDTH_PIXELS, SPRITE_HEIGHT_PIXELS));
        return spriteArray;
    }

    public Sprite[] getEnemySpriteArray() {
        //method for indexing and selecting parts of the bitmap for the enemy sprite
        Sprite[] spriteArray = new Sprite[3];
        spriteArray[0] = new Sprite(this, new Rect(0*ENEMY_WIDTH_PIXELS, 0, 1*ENEMY_WIDTH_PIXELS, ENEMY_HEIGHT_PIXELS));
        spriteArray[1] = new Sprite(this, new Rect(1*ENEMY_WIDTH_PIXELS, 0, 2*ENEMY_WIDTH_PIXELS, ENEMY_HEIGHT_PIXELS));
        spriteArray[2] = new Sprite(this, new Rect(2*ENEMY_WIDTH_PIXELS, 0, 3*ENEMY_WIDTH_PIXELS, ENEMY_HEIGHT_PIXELS));
        return spriteArray;
    }

    public Bitmap getBitmapRight() {
        return playerBitmapRight;
    }
    public Bitmap getBitmapLeft() {
        return playerBitmapLeft;
    }

    public Bitmap getWolfBitmapRight() {
        return scaledWolfBitmapRight;
    }
    public Bitmap getWolfBitmapLeft() {
        return scaledWolfBitmapLeft;
    }

    public Bitmap getSawBitmapRight() {
        return scaledSawBitmapRight;
        //scaledSawBitmapRight;
    }
    public Bitmap getSawBitmapLeft() {
        return scaledSawBitmapLeft;
    }

    public Bitmap getBirdBitmapRight() {
        return scaledBirdBitmapRight;
    }
    public Bitmap getBirdBitmapLeft() {
        return scaledBirdBitmapLeft;
    }

    //get methods for skybox bitmap
    public Bitmap getSkyBitmap() {
        return scaledSkyBitmap;
    }
    public int getSkyBitmapWidth() {
        return scaledSkyBitmap.getWidth();
    }
    public int getSkyBitmapHeight() {
        return scaledSkyBitmap.getHeight();
    }

    //get methods for tilemap bitmaps
    public Bitmap getGrassTileBitmap() {return scaledGrassTileBitmap;}

/*    private Sprite getGrassTileByIndex(int idxRow, int idxCol) {
        //0,0 = sky, 0,1 = dirt, 0,2 = grass topper
        return new Sprite(this, new Rect(
                idxCol*GRASSTILES_WIDTH_PIXELS,
                idxRow*GRASSTILES_HEIGHT_PIXELS,
                (idxCol + 1)*GRASSTILES_WIDTH_PIXELS,
                (idxRow + 1)*GRASSTILES_HEIGHT_PIXELS
        ));
    }*/

    private Bitmap scaleBitmap(Bitmap bitmap, int newHeight, int newWidth) {
        //method to scale bitmap to a new set of dimensions

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float scaleWidth = ((float) newWidth)/width;
        float scaleHeight = ((float) newHeight)/height;
        //create matrix for bitmap transformation
        matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        //transform bitmap
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, false);
        return resizedBitmap;
    }

}
