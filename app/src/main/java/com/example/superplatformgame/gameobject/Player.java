package com.example.superplatformgame.gameobject;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
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
import com.example.superplatformgame.map.Tilemap;

/**
 * Player is a character in the game controllable by the player with the buttons
 * The Player class is an extension of the Hitbox class, which is an extension of the GameObject class
 */

public class Player extends Hitbox {
    public static final double SPEED_PIXELS_PER_SECOND = 30.0;
    public static final double MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS; //pixels/s divided by updates/s = pixels/update
    public static final double GRAVITY = 5; // acceleration due to gravity
    public static final double BOUNCE_FACTOR = 0.7; // scale of how much the player bounces off the walls
    public static final double JUMP_SPEED = 60; //how high player can jump
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
    
    public void setPlayerVelocityX(double x) {
        velocityX = x;
    }
    public void setPlayerVelocityY(double y) {
        velocityY = y;
    }
    public double getPlayerVelocityY() {
        return velocityY;
    }

/*    public void moveBackX() {
        //Log.d("Player.java", "Player moved back in x direction");
        //velocityX += -MAX_SPEED;
        //positionX = previousPositionX;
    }*/

    public void moveBackY() {
        //Log.d("Player.java", "Player moved back in y direction");
        positionY = previousPositionY;
    }

    @Override
    public void update(GameCamera gameCamera, Tilemap tileMap) {
        //update player velocity based on which button is pressed
        if (buttonLeft.getState()) {
            if(tileMap.isColliding(this, gameCamera, true, false)) {
                //if player presses left button and collides with something, create an opposing force * bounce factor to make the player bounce off the collided object
                positionX = previousPositionX;
                velocityX = -velocityX * BOUNCE_FACTOR;
            } else {
                //if player presses left button and there is no collision, then player moves left
                velocityX += -MAX_SPEED;
            }
        }
         else if (buttonRight.getState()) {
            if(tileMap.isColliding(this, gameCamera, true, false)) {
                //if player presses right button and collides with something, create an opposing force * bounce factor to make the player bounce off the collided object
                positionX = previousPositionX;
                velocityX = -velocityX * BOUNCE_FACTOR;
            } else {
                //if player presses right button and there is no collision, then player moves right
                velocityX += MAX_SPEED;
            }
        } else {
             //if neither left or right button is pressed, set X velocity to 0.
            velocityX = 0;
        }
        if (buttonJump.getState()) {
            if (velocityY == 0) {
                //if jump button is pressed and current Y velocity = 0 (means that player is not jumping or falling), then player jumps.
                velocityY -= JUMP_SPEED;
            }
        } else {
            //if jump button is not pressed, then do nothing and let gravity pull the player down
        }

        //Log.d("Player.java", "VelocityX: " + velocityX + ", VelocityY: " + velocityY);

        //apply gravity
        velocityY += GRAVITY;
        //Log.d("Player.java", "Gravity applied");

        //before player's position is updated, keep track of their current position and store it as the previous position
        previousPositionX = positionX;
        previousPositionY = positionY;

        //update the player's position as normal
        positionX += velocityX;
        positionY += velocityY;
        //Log.d("Player.java", "PositionX: " + positionX + ", PositionY: " + positionY);


        //update player statae
        playerState.update(tileMap, gameCamera);
    }

    public void draw(Canvas canvas, GameCamera gameCamera) {
        //animator draw method to draw sprite onto player location
        animator.draw(canvas, gameCamera, this);

        paint = new Paint();
        paint.setColor(Color.RED);
        //canvas.drawCircle((float) (int) gameCamera.gameToDisplayCoordinatesX(getPositionX()), (float) (int) gameCamera.gameToDisplayCoordinatesY(getPositionY()), 10, paint);
        //canvas.drawRect(getPlayerRect(gameCamera), paint);
    }

    @Override
    public void update() {
        //empty method to match superclass
    }

    public PlayerState getPlayerState() {
        return playerState;
    }

/*
    public Rect getPlayerRect(GameCamera gameCamera){
        Rect playerRect = new Rect(
                (int) gameCamera.gameToDisplayCoordinatesX(getPositionX()) - SpriteSheet.SPRITE_WIDTH_PIXELS/2,
                (int) gameCamera.gameToDisplayCoordinatesY(getPositionY()) - SpriteSheet.SPRITE_HEIGHT_PIXELS/2,
                (int) gameCamera.gameToDisplayCoordinatesX(getPositionX()) + SpriteSheet.SPRITE_WIDTH_PIXELS/2,
                (int) gameCamera.gameToDisplayCoordinatesY(getPositionY()) + SpriteSheet.SPRITE_HEIGHT_PIXELS/2
        );
        //Log.d("Player.java", "futurePlayerRect.bottom: " + (int) gameCamera.gameToDisplayCoordinatesY(getPositionY() + SpriteSheet.SPRITE_HEIGHT_PIXELS/2+5));
        return playerRect;
    }
*/

    public Rect getFuturePlayerRect(GameCamera gameCamera){
        //get a rectangle that is bounded over player's sprite + their velocity
        Rect playerRect = new Rect(
                (int) (gameCamera.gameToDisplayCoordinatesX(getPositionX()) - SpriteSheet.SPRITE_WIDTH_PIXELS/2 + velocityX),
                (int) (gameCamera.gameToDisplayCoordinatesY(getPositionY()) - SpriteSheet.SPRITE_HEIGHT_PIXELS/2 + velocityY),
                (int) (gameCamera.gameToDisplayCoordinatesX(getPositionX()) + SpriteSheet.SPRITE_WIDTH_PIXELS/2 + velocityX),
                (int) (gameCamera.gameToDisplayCoordinatesY(getPositionY()) + SpriteSheet.SPRITE_HEIGHT_PIXELS/2 + velocityY)
        );
        //Log.d("Player.java", "futurePlayerRect.bottom: " + (int) gameCamera.gameToDisplayCoordinatesY(getPositionY() + velocityY + SpriteSheet.SPRITE_HEIGHT_PIXELS/2+5));
        return playerRect;
    }

}
