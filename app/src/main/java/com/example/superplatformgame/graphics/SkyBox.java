package com.example.superplatformgame.graphics;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.Region;
import android.util.Log;

import com.example.superplatformgame.GameCamera;

import java.util.Iterator;
import java.util.List;

public class SkyBox {
    private Bitmap skyBitmap;
    private SpriteSheet spriteSheet;
    private Rect rect;
    private Region region;
    private double positionX, positionY;

    public SkyBox(SpriteSheet spriteSheet) {
        this.spriteSheet = spriteSheet;
        skyBitmap = spriteSheet.getSkyBitmap();
        this.rect = new Rect(0, 0, spriteSheet.getSkyBitmapWidth(), spriteSheet.getSkyBitmapHeight());
    }


    public void draw(Canvas canvas, GameCamera gameCamera, int x, int y) {
        int width = spriteSheet.getSkyBitmapWidth();
        int height = spriteSheet.getSkyBitmapHeight();
            Rect rectScreen = new Rect(
                    (int) gameCamera.gameToDisplayCoordinatesX(x),
                    y,
                    (int) gameCamera.gameToDisplayCoordinatesX((x + width)),
                    y + height
            );
            canvas.drawBitmap(
                    skyBitmap,
                    rect,
                    rectScreen, //destination rectangle: the display screen of the game
                    null
            );
        }

        public int getWidth() {
        return spriteSheet.getSkyBitmapWidth();

    }



    }






