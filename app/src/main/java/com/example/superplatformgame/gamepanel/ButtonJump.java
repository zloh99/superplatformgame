package com.example.superplatformgame.gamepanel;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Region;

import androidx.core.content.ContextCompat;

import com.example.superplatformgame.R;

public class ButtonJump extends Buttons{

    public enum State {
        PRESSED,
        NOT_PRESSED
    }
    private Paint paint;
    private Bitmap bitmap;
    private State state;
    private Rect rect;
    private Rect buttonPosition; //= new Rect(1700, 800, 2000, 1030);
    private Region region;


    public ButtonJump(Context context, double positionX, double positionY, double width, double height) {
        super(context, positionX, positionY, width, height);
        //Define paint object
        paint = new Paint();
        paint.setColor(ContextCompat.getColor(context, R.color.button));

        this.state = State.NOT_PRESSED;

        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inScaled = false;
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.button_jump, bitmapOptions);

        buttonPosition = new Rect((int) positionX, (int) positionY, (int) (positionX+width), (int) (positionY+height));
        this.region = new Region(buttonPosition);

    }

    @Override
    public void draw(Canvas canvas) {
        switch(state) {
            case NOT_PRESSED:
                canvas.drawBitmap(bitmap, new Rect(0, 0, 230, 242), buttonPosition,
                        null);
                break;
            case PRESSED:
                canvas.drawBitmap(bitmap, new Rect(230, 0, 460, 242), buttonPosition,
                        null);
                break;
        }
    }

    @Override
    public void update() {

    }

    public boolean isPressed(double touchPositionX, double touchPositionY) {
        //check if touch X and Y coordinates are within the button
        //reminder to tomorrow me: just draw an image for the button and draw a region over it
        if (region.contains((int) touchPositionX, (int) touchPositionY)) {
            return true;
        }
        else {return false;}
    }


    public void setIsPressed(boolean value) {
        if (value) {
            state = State.PRESSED;
        }
        else {
            state = State.NOT_PRESSED;
        }
    }

    public boolean getState() {
        //returns the current state of the button, true for pressed and false for not pressed
        if (state == State.PRESSED) {
            return true;
        } else {return false;}
    }
}
