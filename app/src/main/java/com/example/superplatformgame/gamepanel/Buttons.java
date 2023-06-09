package com.example.superplatformgame.gamepanel;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.superplatformgame.GameCamera;
/**
 * Buttons is an abstract class which is the foundation
 * for all buttons in the game.
 */
public abstract class Buttons {
    protected double positionX, positionY = 0.0;
    protected double width;
    protected double height;

    public Buttons(Context context, double positionX, double positionY, double width, double height) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.width = width;
        this.height = height;
    }

    public abstract void draw(Canvas canvas);

    public abstract void update();

}
