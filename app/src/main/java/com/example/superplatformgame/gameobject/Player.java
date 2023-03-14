package com.example.superplatformgame.gameobject;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.example.superplatformgame.GameCamera;
import com.example.superplatformgame.GameLoop;
import com.example.superplatformgame.R;
import com.example.superplatformgame.gamepanel.ButtonJump;
import com.example.superplatformgame.gamepanel.ButtonLeft;
import com.example.superplatformgame.gamepanel.ButtonRight;
import com.example.superplatformgame.graphics.SpriteSheet;
import com.example.superplatformgame.map.MapLayout;
import com.example.superplatformgame.map.Tilemap;

import java.util.ArrayList;

/**
 * Player is a character in the game controllable by the player with the buttons
 * The Player class is an extension of the Hitbox class, which is an extension of the GameObject class
 */

public class Player extends Hitbox {
    public static final double SPEED_PIXELS_PER_SECOND = 10.0;
    public static final double MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS; //pixels/s divided by updates/s = pixels/update
    private static final double GRAVITY = 0.5; // acceleration due to gravity
    private static final double FRICTION = 0.1; // friction coefficient
    private final ButtonLeft buttonLeft;
    private final ButtonRight buttonRight;
    private final ButtonJump buttonJump;
    private Animator animator;
    private PlayerState playerState;
    private Tilemap tileMap;
    private double previousPositionX;
    private double previousPositionY;

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
        tileMap = new Tilemap(new SpriteSheet(context));

        paint = new Paint();
        int color = ContextCompat.getColor(context, R.color.player);
        paint.setColor(color);
    }

    public void moveBackX() {
        Log.d("Player.java", "Player moved back in x direction");
        positionX = previousPositionX;
    }

    public void moveBackY() {
        Log.d("Player.java", "Player moved back in y direction");
        positionY = previousPositionY;
        //playerState.setAirborneState(true);
    }

    @Override
    public void update(GameCamera gameCamera) {
        //update player velocity based on which button is pressed
        if (buttonLeft.getState()) {
            velocityX += -MAX_SPEED;
        } else if (buttonRight.getState()) {
            velocityX += MAX_SPEED;
        } else if (buttonJump.getState()) {
            velocityY += MAX_SPEED;
        } else {
            velocityX = 0;
            velocityY = 0;
        }

        //update player position
        previousPositionX = positionX;
        previousPositionY = positionY;

        // If there is no collision, update the player's position as normal
        positionX += velocityX;
        positionY += velocityY;


        //update player direction
        playerState.update(tileMap, gameCamera);
    }

    /*@Override
    public void update(GameCamera gameCamera) {
        //update player velocity based on which button is pressed
        if (buttonLeft.getState()) {
            velocityX += -MAX_SPEED;
        } else if (buttonRight.getState()) {
            velocityX += MAX_SPEED;
        } else if (buttonJump.getState()) {
            velocityY += MAX_SPEED;
        }
        else {
            velocityX = 0;
            velocityY = 0;
        }

        //apply gravity
*//*        if(playerState.getAirborneState() == PlayerState.AirborneState.GROUND) {
            velocityY = 0;
            Log.d("Player.java", "GRAVITY stopped");
        }
        else if (playerState.getAirborneState() == PlayerState.AirborneState.AIR) {
            velocityY += GRAVITY;
            Log.d("Player.java", "GRAVITY acted");
        }*//*
        //velocityY += GRAVITY;

        //update player position
        previousPositionX = positionX;
        previousPositionY = positionY;
        positionX += velocityX;
        positionY += velocityY;
        //Log.d("Player.java", "positionX: " + Double.toString(positionX));

        //update player direction

        playerState.update(tileMap, gameCamera);
    }*/

    public void draw(Canvas canvas, GameCamera gameCamera) {
        animator.draw(canvas, gameCamera, this);
    }

    @Override
    public void update() {

    }

    public PlayerState getPlayerState() {
        return playerState;
    }

    public Rect getPlayerRect(GameCamera gameCamera){
        Rect playerRect = new Rect(
                (int) gameCamera.gameToDisplayCoordinatesX(getPositionX()),
                (int) gameCamera.gameToDisplayCoordinatesY(getPositionY()),
                (int) gameCamera.gameToDisplayCoordinatesX(getPositionX() + SpriteSheet.SPRITE_WIDTH_PIXELS/2),
                (int) gameCamera.gameToDisplayCoordinatesY(getPositionY() + SpriteSheet.SPRITE_HEIGHT_PIXELS/2+5)
        );
        return playerRect;
    }

}
