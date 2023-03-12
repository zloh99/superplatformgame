package com.example.superplatformgame.gameobject;

import static com.example.superplatformgame.gamepanel.ButtonJump.*;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.example.superplatformgame.GameCamera;
import com.example.superplatformgame.GameLoop;
import com.example.superplatformgame.R;
import com.example.superplatformgame.gamepanel.ButtonJump;
import com.example.superplatformgame.gamepanel.ButtonLeft;
import com.example.superplatformgame.gamepanel.ButtonRight;

/**
 * Player is a character in the game controllable by the player with the buttons
 * The Player class is an extension of the Hitbox class, which is an extension of the GameObject class
 */

public class Player extends Hitbox {
    public static final double SPEED_PIXELS_PER_SECOND = 10.0;
    public static final double MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS; //pixels/s divided by updates/s = pixels/update
    private final ButtonLeft buttonLeft;
    private final ButtonRight buttonRight;
    private final ButtonJump buttonJump;
    private Animator animator;
    private PlayerState playerState;

    /**
     * Constructor for player class
     */
    public Player(Context context, ButtonLeft buttonLeft, ButtonRight buttonRight, ButtonJump buttonJump, double positionX, double positionY, double radius, Animator animator) {
        super(context, ContextCompat.getColor(context, R.color.player), positionX, positionY, radius);
        this.positionX = positionX;
        this.positionY = positionY;
        this.buttonLeft = buttonLeft;
        this.buttonRight = buttonRight;
        this.buttonJump = buttonJump;
        this.radius = radius;
        this.animator = animator;
        this.playerState = new PlayerState(this);

        paint = new Paint();
        int color = ContextCompat.getColor(context, R.color.player);
        paint.setColor(color);
    }

    @Override
    public void update() {
        //update player velocity based on which button is pressed
        if (buttonLeft.getState()) {
            velocityX += -MAX_SPEED;
        } else if (buttonRight.getState()) {
            velocityX += MAX_SPEED;
        } else {
            velocityX = 0;
        }

        //update player position
        positionX += velocityX;

        //update player direction

        playerState.update();
    }

    public void draw(Canvas canvas, GameCamera gameCamera) {
        animator.draw(canvas, gameCamera, this);
    }

    public PlayerState getPlayerState() {
        return playerState;
    }
}
