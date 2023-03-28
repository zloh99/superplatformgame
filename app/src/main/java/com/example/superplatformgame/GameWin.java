package com.example.superplatformgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import androidx.core.content.ContextCompat;

/**
 * GameOver is a class that handles the game win screen once a player touches the spaceship tile.
 */
public class GameWin {
    private Context context;

    private boolean isPressed; //for the game restart button
    private Rect rect;

    public GameWin(Context context) {
        this.context = context;
    }

    public void draw(Canvas canvas) {
        //Draw "You Win" string
        String text = "You Win!";
        float x = 800;
        float y = 200;
        Paint paint = new Paint();
        int color = ContextCompat.getColor(context, R.color.gameOver);
        paint.setColor(color);
        float textSize = 150;
        paint.setTextSize(textSize);
        canvas.drawText(text, x, y, paint);

        //Draw restart button
        String textContinue = "Restart?";
        float xContinue = 800;
        float yContinue = 400;
        Paint paintContinue = new Paint();
        paintContinue.setColor(color);
        paintContinue.setTextSize(100);
        canvas.drawText(textContinue, xContinue, yContinue, paintContinue);
        //draw invisible rectangle over the continue text to detect touch
        rect = new Rect((int) xContinue,
                (int) (yContinue - paintContinue.getTextSize()),
                (int) (xContinue + paintContinue.measureText(textContinue)),
                (int) yContinue);
        //canvas.drawRect(rect, paintContinue);
    }

    public boolean isPressed(double touchX, double touchY) {
        return rect.contains((int) touchX, (int) touchY);
    }

    public void setIsPressed(boolean isPressed) {
        this.isPressed = isPressed;
    }

    public boolean getIsPressed() {
        return isPressed;
    }
}
