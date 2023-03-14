package com.example.superplatformgame.gameobject;

import android.graphics.Canvas;

import com.example.superplatformgame.GameCamera;
import com.example.superplatformgame.graphics.Sprite;

/**
 * Animator is a class that handles drawing the player sprites to screen based on the player's state and player coordinates
 */
public class Animator {

    private static final int MAX_UPDATES_BEFORE_NEXT_MOVE_FRAME = 5;
    private Sprite[] playerSpriteArray;
    private int idxNotMovingFrameRight = 1;
    private int idxNotMovingFrameLeft = 9;
    private int idxMovingFrame = 2;
    private int updatesBeforeNextMoveFrame;

    public Animator(Sprite[] playerSpriteArray) {
        this.playerSpriteArray = playerSpriteArray;
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
        if (idxMovingFrame == 8) {
            idxMovingFrame = 0;
        }
        else
            idxMovingFrame = 8;
    }

    private void toggleIdxMovingFrameRight() {
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
                (int) gameCamera.gameToDisplayCoordinatesX(player.getPositionX()) - sprite.getWidth()/4, //remember to add the reference to gameCamera when collision and levels are done
                (int) gameCamera.gameToDisplayCoordinatesY(player.getPositionY()) - sprite.getHeight()/2 //remember to add the reference to gameCamera when collision and levels are done
        );
    }

    private void drawFrameLeft(Canvas canvas, GameCamera gameCamera, Player player, Sprite sprite) {
        //draw sprite based on player location
        sprite.drawLeft(
                canvas,
                (int) gameCamera.gameToDisplayCoordinatesX(player.getPositionX()) - sprite.getWidth()/4, //remember to add the reference to gameCamera when collision and levels are done
                (int) gameCamera.gameToDisplayCoordinatesY(player.getPositionY()) - sprite.getHeight()/2 //remember to add the reference to gameCamera when collision and levels are done
        );
    }
}
