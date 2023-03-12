package com.example.superplatformgame.graphics;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;

import com.example.superplatformgame.R;

/**
 * Class responsible for returning specific sprite we are interested in, which we then add to the Sprite Object
 * The sprite object is then returned to the Player object, which then draws it to the screen
 */
public class SpriteSheet {
    private static final int SPRITE_WIDTH_PIXELS = 96;
    private static final int SPRITE_HEIGHT_PIXELS = 120;
    //bitmap = 2d array of pixels, with each pixel having 3 values: R, G, and B
    private Bitmap playerBitmapRight;
    private Bitmap playerBitmapLeft;
    private Matrix matrix;

    public SpriteSheet(Context context) {
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inScaled = false;
        playerBitmapRight = BitmapFactory.decodeResource(context.getResources(), R.drawable.green_alien_big, bitmapOptions);
        Matrix matrix = new Matrix();
        matrix.preScale(-1, 1);
        playerBitmapLeft = Bitmap.createBitmap(playerBitmapRight, 0, 0, playerBitmapRight.getWidth(), playerBitmapRight.getHeight(), matrix, true);
    }

    public Sprite[] getPlayerSpriteArray() {
        //method for selecting the bitmap for the player's sprite
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

    public Bitmap getBitmapRight() {
        return playerBitmapRight;
    }

    public Bitmap getBitmapLeft() {
        return playerBitmapLeft;
    }

    private Sprite getSpriteByIndex(int idxRow, int idxCol) {
        return new Sprite(this, new Rect(
                idxCol*SPRITE_WIDTH_PIXELS,
                idxRow*SPRITE_HEIGHT_PIXELS,
                (idxCol + 1)*SPRITE_WIDTH_PIXELS,
                (idxRow + 1)*SPRITE_HEIGHT_PIXELS
        ));
    }

}
