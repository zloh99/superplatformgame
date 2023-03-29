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

public class Enemy extends Hitbox {
    public static final double SPEED_PIXELS_PER_SECOND = 30.0;
    public static final double MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS; //pixels/s divided by updates/s = pixels/update
    public static final double BOUNCE_FACTOR = 0.7; // scale of how much the player bounces off the walls
    public static final double JUMP_SPEED = 60; //how high player can jump

    public static final double ENEMY_WIDTH = 10;
    public static final double ENEMY_HEIGHT = 20;
    private Animator animator;
    private EnemyState enemyState;
    private Tilemap tileMap;
    private double previousPositionX;
    private double previousPositionY;

    private double maxPositionX;
    private double minPositionX;
    private double gravity;
    private boolean damageable;
    private int scorePoints;

    /**
     * Constructor for enemy class
     */
    public Enemy(Context context, double positionX, double positionY,
                 double width, double velocity, Animator animator) {
        super(context, ContextCompat.getColor(context, R.color.player), positionX, positionY, 10);
        this.positionX = positionX;
        this.positionY = positionY;

        this.minPositionX = positionX - width/2;
        this.maxPositionX = positionX + width/2;
        this.velocityX = velocity;

        this.animator = animator;
        this.enemyState = new EnemyState(this);
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

//    public void moveBackX() {
//        Log.d("Player.java", "Player moved back in x direction");
//        velocityX += -MAX_SPEED;
//        positionX = previousPositionX;
//    }

    public void moveBackY() {
        //Log.d("Player.java", "Player moved back in y direction");
        positionY = previousPositionY;
    }
    @Override
    public void update(GameCamera gameCamera, Tilemap tileMap) {
        //update player velocity based on which button is pressed
        if(tileMap.isColliding(this, gameCamera, true, false)) {
            positionX = previousPositionX;
            velocityX = -velocityX;
        } else if (this.positionX > this.maxPositionX || this.positionX < this.minPositionX) {
            positionX = previousPositionX;
            velocityX = -velocityX;
        }

        //Log.d("Enemy.java", "VelocityX: " + velocityX);

        //apply gravity
        velocityY += gravity;
        //Log.d("Player.java", "Gravity applied");

        //before player's position is updated, keep track of their current position and store it as the previous position
        previousPositionX = positionX;
        previousPositionY = positionY;

        //update the player's position as normal
        positionX += velocityX;
        positionY += velocityY;
        //Log.d("Enemy.java", "PositionX: " + positionX + ", PositionY: " + positionY);

        //update player state
        enemyState.update(tileMap, gameCamera);
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
    public void draw(Canvas canvas, GameCamera gameCamera) {
        animator.drawEnemy(canvas, gameCamera, this);
//        paint = new Paint();
//        paint.setColor(Color.RED);
//        canvas.drawRect(getPlayerRect(gameCamera), paint);
    }

    public void setGravity(double gravity) {
        this.gravity = gravity;
    }
    public Rect getFuturePlayerRect(GameCamera gameCamera){
        //get a rectangle that is bounded over player's sprite + their velocity
        Rect playerRect = new Rect(
                (int) (gameCamera.gameToDisplayCoordinatesX(getPositionX()) - SpriteSheet.SPRITE_WIDTH_PIXELS/2 + velocityX),
                (int) (gameCamera.gameToDisplayCoordinatesY(getPositionY()) - SpriteSheet.SPRITE_HEIGHT_PIXELS/2 + velocityY),
                (int) (gameCamera.gameToDisplayCoordinatesX(getPositionX()) + SpriteSheet.SPRITE_WIDTH_PIXELS/2 + velocityX),
                (int) (gameCamera.gameToDisplayCoordinatesY(getPositionY()) + SpriteSheet.SPRITE_HEIGHT_PIXELS/2 + velocityY)
        );
        return playerRect;
    }
    public boolean getDamageable() {
        return damageable;
    }
    public void setDamageable(boolean damageable) {
        this.damageable = damageable;
    }
    public int getScorePoints() {
        return this.scorePoints;
    }
    public void setScorePoints(int scorePoints) {
        this.scorePoints = scorePoints;
    }

    public EnemyState getEnemyState() {
        return enemyState;
    }

    @Override
    public void update() {
        //empty method to match superclass
    }

}
