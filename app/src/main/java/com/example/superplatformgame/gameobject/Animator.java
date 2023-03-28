package com.example.superplatformgame.gameobject;

import static com.example.superplatformgame.graphics.SpriteSheet.ENEMY_HEIGHT_PIXELS;
import static com.example.superplatformgame.graphics.SpriteSheet.ENEMY_WIDTH_PIXELS;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import com.example.superplatformgame.GameCamera;
import com.example.superplatformgame.R;
import com.example.superplatformgame.graphics.Sprite;
import com.example.superplatformgame.graphics.SpriteSheet;

/**
 * Animator is a class that handles drawing the player sprites to screen based on the player's state and player coordinates
 */
public class Animator {

    private static final int MAX_UPDATES_BEFORE_NEXT_MOVE_FRAME = 5; //control how fast different frames are animated
    private Sprite[] playerSpriteArray;
    private Sprite[] enemySpriteArray;
    private int idxNotMovingFrameRight = 1; //index of the idle frame when player is facing right
    private int idxNotMovingFrameLeft = 9; //index of the idle frame when player is facing left
    private int idxMovingFrame = 2; //index of the moving frame

    private int idxMovingFrameEnemy = 0; //index of the moving frame
    private int updatesBeforeNextMoveFrame;

    public Animator(Sprite[] playerSpriteArray, Sprite[] enemySpriteArray) {
        this.playerSpriteArray = playerSpriteArray;
        this.enemySpriteArray = enemySpriteArray;
    }

    public void draw(Canvas canvas, GameCamera gameCamera, Player player) {
        //method to decide which frame to draw to the player
        switch (player.getPlayerState().getState()) {
            case NOT_MOVING_RIGHT:
                drawFrameRight(canvas, gameCamera, player, playerSpriteArray[idxNotMovingFrameRight]);
                break;
            case NOT_MOVING_LEFT:
                drawFrameLeft(canvas, gameCamera, player, playerSpriteArray[idxNotMovingFrameLeft]);
                break;
            case STARTED_MOVING_RIGHT:
                updatesBeforeNextMoveFrame = MAX_UPDATES_BEFORE_NEXT_MOVE_FRAME;
                drawFrameRight(canvas, gameCamera, player, playerSpriteArray[idxMovingFrame]);
                break;
            case STARTED_MOVING_LEFT:
                updatesBeforeNextMoveFrame = MAX_UPDATES_BEFORE_NEXT_MOVE_FRAME;
                drawFrameLeft(canvas, gameCamera, player, playerSpriteArray[idxMovingFrame]);
                break;
            case IS_MOVING_RIGHT:
                updatesBeforeNextMoveFrame--;
                if(updatesBeforeNextMoveFrame == 0) {
                    updatesBeforeNextMoveFrame = MAX_UPDATES_BEFORE_NEXT_MOVE_FRAME;
                    toggleIdxMovingFrameRight();
                }
                drawFrameRight(canvas, gameCamera, player, playerSpriteArray[idxMovingFrame]);
                break;
            case IS_MOVING_LEFT:
                updatesBeforeNextMoveFrame--;
                if(updatesBeforeNextMoveFrame == 0) {
                    updatesBeforeNextMoveFrame = MAX_UPDATES_BEFORE_NEXT_MOVE_FRAME;
                    toggleIdxMovingFrameLeft();
                }
                drawFrameLeft(canvas, gameCamera, player, playerSpriteArray[idxMovingFrame]);
                break;
        }
    }

    private void toggleIdxMovingFrameLeft() {
        //every update loop, if player is moving, then alternate between 2 different frames
        if (idxMovingFrame == 8) {
            idxMovingFrame = 0;
        }
        else
            idxMovingFrame = 8;
    }

    private void toggleIdxMovingFrameRight() {
        //every update loop, if player is moving, then alternate between 2 different frames
        if (idxMovingFrame == 2) {
            idxMovingFrame = 10;
        }
        else
            idxMovingFrame = 2;
    }

    private void drawFrameRight(Canvas canvas, GameCamera gameCamera, Player player, Sprite sprite) {
        //draw sprite based on player location
        sprite.drawRight(
                canvas,
                (int) gameCamera.gameToDisplayCoordinatesX(player.getPositionX()) - sprite.getWidth()/2, //remember to add the reference to gameCamera when collision and levels are done
                (int) gameCamera.gameToDisplayCoordinatesY(player.getPositionY()) - sprite.getHeight()/2 //remember to add the reference to gameCamera when collision and levels are done
        );
    }

    private void drawFrameLeft(Canvas canvas, GameCamera gameCamera, Player player, Sprite sprite) {
        //draw sprite based on player location
        sprite.drawLeft(
                canvas,
                (int) gameCamera.gameToDisplayCoordinatesX(player.getPositionX()) - sprite.getWidth()/2, //remember to add the reference to gameCamera when collision and levels are done
                (int) gameCamera.gameToDisplayCoordinatesY(player.getPositionY()) - sprite.getHeight()/2 //remember to add the reference to gameCamera when collision and levels are done
        );
    }

    private void drawFrameRightEnemy(Canvas canvas, GameCamera gameCamera, Enemy enemy, Sprite sprite) {
        //draw sprite based on player location
        if (enemy instanceof Wolf) {
            sprite.drawWolfRight(
                    canvas,
                    (int) gameCamera.gameToDisplayCoordinatesX(enemy.getPositionX()) - sprite.getWidth()/2, //remember to add the reference to gameCamera when collision and levels are done
                    (int) gameCamera.gameToDisplayCoordinatesY(enemy.getPositionY()) - sprite.getHeight()/2 - 10 //remember to add the reference to gameCamera when collision and levels are done
            );
        } else if (enemy instanceof Saw) {
            //Log.d("Animator.java", "SawRight");
/*            Paint paint = new Paint();
            paint.setColor(Color.RED);
            canvas.drawRect(
                    (float) gameCamera.gameToDisplayCoordinatesX(enemy.getPositionX()) - sprite.getWidth()/2,
                    (float) gameCamera.gameToDisplayCoordinatesY(enemy.getPositionY()) - sprite.getHeight()/2,
                    (float) gameCamera.gameToDisplayCoordinatesX(enemy.getPositionX()) + sprite.getWidth()/2,
                    (float) gameCamera.gameToDisplayCoordinatesY(enemy.getPositionY()) + sprite.getHeight()/2,
                    paint
            );*/
            sprite.drawSawRight(
                    canvas,
                    (int) gameCamera.gameToDisplayCoordinatesX(enemy.getPositionX()) - sprite.getWidth()/2, //remember to add the reference to gameCamera when collision and levels are done
                    (int) gameCamera.gameToDisplayCoordinatesY(enemy.getPositionY()) - sprite.getHeight()/2 - 10 //remember to add the reference to gameCamera when collision and levels are done
            );
        } else if (enemy instanceof Bird) {
            sprite.drawBirdRight(
                    canvas,
                    (int) gameCamera.gameToDisplayCoordinatesX(enemy.getPositionX()) - sprite.getWidth()/2, //remember to add the reference to gameCamera when collision and levels are done
                    (int) gameCamera.gameToDisplayCoordinatesY(enemy.getPositionY()) - sprite.getHeight()/2 //remember to add the reference to gameCamera when collision and levels are done
            );
        }

    }

    private void drawFrameLeftEnemy(Canvas canvas, GameCamera gameCamera, Enemy enemy, Sprite sprite) {
        if (enemy instanceof Wolf) {
            sprite.drawWolfLeft(
                    canvas,
                    (int) gameCamera.gameToDisplayCoordinatesX(enemy.getPositionX()) - sprite.getWidth() / 2, //remember to add the reference to gameCamera when collision and levels are done
                    (int) gameCamera.gameToDisplayCoordinatesY(enemy.getPositionY()) - sprite.getHeight()/2 - 10//remember to add the reference to gameCamera when collision and levels are done
            );
        } else if (enemy instanceof Saw) {
            sprite.drawSawLeft(
                    canvas,
                    (int) gameCamera.gameToDisplayCoordinatesX(enemy.getPositionX()) - sprite.getWidth() / 2, //remember to add the reference to gameCamera when collision and levels are done
                    (int) gameCamera.gameToDisplayCoordinatesY(enemy.getPositionY()) - sprite.getHeight()/2 - 10 //remember to add the reference to gameCamera when collision and levels are done
            );
        } else if (enemy instanceof Bird) {
            sprite.drawBirdLeft(
                    canvas,
                    (int) gameCamera.gameToDisplayCoordinatesX(enemy.getPositionX()) - sprite.getWidth() / 2, //remember to add the reference to gameCamera when collision and levels are done
                    (int) gameCamera.gameToDisplayCoordinatesY(enemy.getPositionY()) - sprite.getHeight()/2 //remember to add the reference to gameCamera when collision and levels are done
            );
        }
    }


    private void toggleIdxMovingFrameLeftEnemy() {
        //every update loop, if enemy is moving, then alternate between 2 different frames
        if (idxMovingFrameEnemy == 0) {
            idxMovingFrameEnemy = 1;
        }
        else
            idxMovingFrameEnemy = 0;
    }

    private void toggleIdxMovingFrameRightEnemy() {
        //every update loop, if enemy is moving, then alternate between 2 different frames
        if (idxMovingFrameEnemy == 1) {
            idxMovingFrameEnemy = 2;
        }
        else
            idxMovingFrameEnemy = 1;
    }

    public void drawEnemy(Canvas canvas, GameCamera gameCamera, Enemy enemy) {
        //method to decide which frame to draw to the enemy

        switch (enemy.getEnemyState().getState()) {
            case NOT_MOVING_RIGHT:
                drawFrameRightEnemy(canvas, gameCamera, enemy, enemySpriteArray[2]);
                break;
            case NOT_MOVING_LEFT:
                drawFrameLeftEnemy(canvas, gameCamera, enemy, enemySpriteArray[2]);
                break;
            case STARTED_MOVING_RIGHT:
                updatesBeforeNextMoveFrame = MAX_UPDATES_BEFORE_NEXT_MOVE_FRAME;
                drawFrameRightEnemy(canvas, gameCamera, enemy, enemySpriteArray[0]);
                break;
            case STARTED_MOVING_LEFT:
                updatesBeforeNextMoveFrame = MAX_UPDATES_BEFORE_NEXT_MOVE_FRAME;
                drawFrameLeftEnemy(canvas, gameCamera, enemy, enemySpriteArray[0]);
                break;
            case IS_MOVING_RIGHT:
                updatesBeforeNextMoveFrame--;
                if(updatesBeforeNextMoveFrame == 0) {
                    updatesBeforeNextMoveFrame = MAX_UPDATES_BEFORE_NEXT_MOVE_FRAME;
                    toggleIdxMovingFrameRightEnemy();
                }
                drawFrameRightEnemy(canvas, gameCamera, enemy, enemySpriteArray[idxMovingFrameEnemy]);
                break;
            case IS_MOVING_LEFT:
                updatesBeforeNextMoveFrame--;
                if(updatesBeforeNextMoveFrame == 0) {
                    updatesBeforeNextMoveFrame = MAX_UPDATES_BEFORE_NEXT_MOVE_FRAME;
                    toggleIdxMovingFrameLeftEnemy();
                }
                drawFrameLeftEnemy(canvas, gameCamera, enemy, enemySpriteArray[idxMovingFrameEnemy]);
                break;
        }
    }

}



