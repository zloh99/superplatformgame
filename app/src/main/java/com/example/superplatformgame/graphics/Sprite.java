package com.example.superplatformgame.graphics;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.DisplayMetrics;

/**
 * Sprite is a class that handles the drawing of bitmaps from the class SpriteSheet to the canvas.
 */
public class Sprite {

    private final SpriteSheet spriteSheet;
    private final Rect rect;

    public Sprite(SpriteSheet spriteSheet, Rect rect) {
        this.spriteSheet = spriteSheet;
        this.rect = rect;
    }



    public void drawRight(Canvas canvas, int x, int y) {
        //method to draw player sprite when it is facing right
        canvas.drawBitmap(
                spriteSheet.getBitmapRight(),
                rect,
                new Rect(x, y, x+getWidth(), y+getHeight()),
                null
        );
    }

    public void drawLeft(Canvas canvas, int x, int y) {
        //method to draw player sprite when it is facing left
        canvas.drawBitmap(
                spriteSheet.getBitmapLeft(),
                rect,
                new Rect(x, y, x+getWidth(), y+getHeight()),
                null
        );
    }

    public int getWidth() {
        return rect.width();
    }
    public int getHeight() {
        return rect.height();
    }
}
