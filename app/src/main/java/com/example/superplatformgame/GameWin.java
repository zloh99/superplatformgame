package com.example.superplatformgame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;

import androidx.core.content.ContextCompat;

/**
 * GameOver is a class that handles the game win screen once a player touches the spaceship tile.
 */
public class GameWin {
    private Context context;

    private boolean isPressed; //for the game restart button
    private Rect rect;
    private Bitmap bitmap;
    private Rect buttonPosition;
    public GameWin(Context context) {
        this.context = context;

        //load button bitmap
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inScaled = false;
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.newgame_button, bitmapOptions);

        //initialise invisible rectangle to detect touch
        float xContinue = 700;
        float yContinue = 400;
        buttonPosition = new Rect((int) xContinue, (int) yContinue, (int) (xContinue + bitmap.getWidth()), (int) (yContinue + bitmap.getHeight()));
    }

    public void draw(Canvas canvas, Typeface typeFace) {
        //Draw "You Win" string
        String text = "You Win!";
        float x = 800;
        float y = 200;
        Paint paint = new Paint();
        int color = ContextCompat.getColor(context, R.color.gameWin);
        paint.setColor(color);
        float textSize = 150;
        paint.setTextSize(textSize);
        paint.setTypeface(typeFace);
        canvas.drawText(text, x, y, paint);

        //Draw restart button
        canvas.drawBitmap(bitmap, new Rect(0, 0, 768, 128), buttonPosition,
                null);
        //draw invisible rectangle over the continue text to detect touch
        rect = buttonPosition;
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
