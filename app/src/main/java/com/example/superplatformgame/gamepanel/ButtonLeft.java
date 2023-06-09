package com.example.superplatformgame.gamepanel;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Region;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.example.superplatformgame.R;

/**
 * ButtonLeft is the class that handles the drawing, updating, and states of the move left button.
 */
public class ButtonLeft extends Buttons{

    public enum State {
        PRESSED,
        NOT_PRESSED
    }
    private Paint paint;
    private Bitmap bitmap;
    private State state;
    private Rect rect;
    private Rect buttonPosition;
    private Region region;


    public ButtonLeft(Context context, double positionX, double positionY, double width, double height) {
        super(context, positionX, positionY, width, height);

        //initialise state (default = not pressed)
        this.state = State.NOT_PRESSED;

        //initialise bitmap
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inScaled = false;
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.button_left2, bitmapOptions);

        //initialise rect and region object to detect touch events
        buttonPosition = new Rect((int) positionX, (int) positionY, (int) (positionX+width), (int) (positionY+height));
        this.region = new Region(buttonPosition);

    }

    @Override
    public void draw(Canvas canvas) {
        //draws button differently based on whether it is pressed or not
        switch(state) {
            case NOT_PRESSED:
                canvas.drawBitmap(bitmap, new Rect(0, 0, 242, 230), buttonPosition,
                        null);
                break;
            case PRESSED:
                canvas.drawBitmap(bitmap, new Rect(242, 0, 484, 230), buttonPosition,
                        null);
                break;
        }
    }

    @Override
    public void update() {

    }

    public boolean isPressed(double touchPositionX, double touchPositionY) {
        //check if touch X and Y coordinates are within the button (which is a region drawn over the button)
        if (region.contains((int) touchPositionX, (int) touchPositionY)) {
            return true;
        }
        else {return false;}
    }


    public void setIsPressed(boolean value) {
        if (value) {
            state = State.PRESSED;
            //Log.d("ButtonLeft.java", "State.PRESSED");
        }
        else {
            state = State.NOT_PRESSED;
            //Log.d("ButtonLeft.java", "State.NOT_PRESSED");
        }
    }

    public boolean getState() {
        //returns the current state of the button, true for pressed and false for not pressed
        if (state == State.PRESSED) {
            return true;
        } else {return false;}
    }
}
