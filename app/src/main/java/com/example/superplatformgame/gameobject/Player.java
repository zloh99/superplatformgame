package com.example.superplatformgame.gameobject;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import androidx.appcompat.graphics.drawable.DrawableContainerCompat;
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
    public static final int MAX_HEALTH_HEARTS = 3;
    private int healthHeartNum;
    public static final double SPEED_PIXELS_PER_SECOND = 30.0;
    public static final double MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS; //pixels/s divided by updates/s = pixels/update
    public static final double GRAVITY = 5; // acceleration due to gravity
    public static final double BOUNCE_FACTOR = 0.7; // scale of how much the player bounces off the walls
    public static final double JUMP_SPEED = 60;
    private final ButtonLeft buttonLeft;
    private final ButtonRight buttonRight;
    private final ButtonJump buttonJump;
    private Animator animator;
    private PlayerState playerState;
    private Tilemap tileMap;
    private double previousPositionX;
    private double previousPositionY;
    private boolean isAirborne;
    private boolean gravityOn;
    private double gravity;
    private HealthHearts healthHearts;

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
        this.healthHearts = new HealthHearts(context, this);
        this.healthHeartNum = MAX_HEALTH_HEARTS;
        tileMap = new Tilemap(new SpriteSheet(context));

        paint = new Paint();
        int color = ContextCompat.getColor(context, R.color.player);
        paint.setColor(color);
        gravity = GRAVITY;
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

    public void moveBackX() {
        Log.d("Player.java", "Player moved back in x direction");
        //velocityX += -MAX_SPEED;
        //positionX = previousPositionX;
    }

    public void moveBackY() {
        //Log.d("Player.java", "Player moved back in y direction");
        positionY = previousPositionY;
    }

    public void setIsAirborne(boolean bool) {
        isAirborne = bool;
    }

    public Boolean getIsAirborne() {
        return isAirborne;
    }

    public void setGravityOn(boolean bool) {
        gravityOn = bool;
    }

    public Boolean getGravityOn() {
        return gravityOn;
    }

    @Override
    public void update(GameCamera gameCamera, Tilemap tileMap) {
        //update player velocity based on which button is pressed
        if (buttonLeft.getState()) {
            if(tileMap.isColliding(this, gameCamera, true, false)) {
                positionX = previousPositionX;
                velocityX = -velocityX * BOUNCE_FACTOR;
            } else {
                velocityX += -MAX_SPEED;
            }
        }
         else if (buttonRight.getState()) {
            if(tileMap.isColliding(this, gameCamera, true, false)) {
                positionX = previousPositionX;
                velocityX = -velocityX * BOUNCE_FACTOR;
            } else {
                velocityX += MAX_SPEED;
            }
        } else {
            velocityX = 0;
        }
        if (buttonJump.getState()) {
            if (velocityY == 0) {
                velocityY -= JUMP_SPEED;
            }
        } else {

        }

        //Log.d("Player.java", "VelocityX: " + velocityX + ", VelocityY: " + velocityY);

        //apply gravity
        velocityY += GRAVITY;
        //Log.d("Player.java", "Gravity applied");
        //Log.d("Player.java", "Gravity:" + gravity);

        //update player position
        previousPositionX = positionX;
        previousPositionY = positionY;

        // If there is no collision, update the player's position as normal
        positionX += velocityX;
        positionY += velocityY;
        //Log.d("Player.java", "PositionX: " + positionX + ", PositionY: " + positionY);


        //update player direction
        playerState.update(tileMap, gameCamera);
    }

    public void draw(Canvas canvas, GameCamera gameCamera) {
        animator.draw(canvas, gameCamera, this);

        paint = new Paint();
        paint.setColor(Color.RED);
        //canvas.drawCircle((float) (int) gameCamera.gameToDisplayCoordinatesX(getPositionX()), (float) (int) gameCamera.gameToDisplayCoordinatesY(getPositionY()), 10, paint);
        //canvas.drawRect(getPlayerRect(gameCamera), paint);
        healthHearts.draw(canvas);
    }

    @Override
    public void update() {

    }

    public PlayerState getPlayerState() {
        return playerState;
    }

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

    public Rect getFuturePlayerRect(GameCamera gameCamera){
        Rect playerRect = new Rect(
                (int) (gameCamera.gameToDisplayCoordinatesX(getPositionX()) - SpriteSheet.SPRITE_WIDTH_PIXELS/2 + velocityX),
                (int) (gameCamera.gameToDisplayCoordinatesY(getPositionY()) - SpriteSheet.SPRITE_HEIGHT_PIXELS/2 + velocityY),
                (int) (gameCamera.gameToDisplayCoordinatesX(getPositionX()) + SpriteSheet.SPRITE_WIDTH_PIXELS/2 + velocityX),
                (int) (gameCamera.gameToDisplayCoordinatesY(getPositionY()) + SpriteSheet.SPRITE_HEIGHT_PIXELS/2 + velocityY)
        );
        //Log.d("Player.java", "futurePlayerRect.bottom: " + (int) gameCamera.gameToDisplayCoordinatesY(getPositionY() + velocityY + SpriteSheet.SPRITE_HEIGHT_PIXELS/2+5));
        return playerRect;
    }

    public int getHealthHearts() {
        return healthHeartNum;
    }

    public void setHealthHearts(int healthHeartNum) {
        this.healthHeartNum = healthHeartNum;
    }

}
