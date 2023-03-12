package com.example.superplatformgame;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.superplatformgame.gameobject.Animator;
import com.example.superplatformgame.gameobject.GameObject;
import com.example.superplatformgame.gameobject.Player;
import com.example.superplatformgame.gamepanel.ButtonJump;
import com.example.superplatformgame.gamepanel.ButtonLeft;
import com.example.superplatformgame.gamepanel.ButtonRight;
import com.example.superplatformgame.gamepanel.Performance;
import com.example.superplatformgame.graphics.SpriteSheet;

public class Game extends SurfaceView implements SurfaceHolder.Callback {
    private final Player player;
    private GameLoop gameLoop;
    private Performance performance;
    private GameCamera gameCamera;
    private final ButtonLeft buttonLeft;
    private int buttonLeftId = 0;
    private final ButtonRight buttonRight;
    private int buttonRightId = 0;
    private final ButtonJump buttonJump;
    private int buttonJumpId = 0;


    public Game(Context context) {
        super(context);
        //Game constructor - initialise everything

        //Get surface holder and add callback
        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        //initialise game loop
        gameLoop = new GameLoop(this, surfaceHolder);

        //Initialise game panels (all graphical objects that do not interact with any game objects)
        performance = new Performance(context, gameLoop);
        buttonLeft = new ButtonLeft(context, 250, 750, 100);
        buttonRight = new ButtonRight(context, 500, 750, 100);
        buttonJump = new ButtonJump(context, 500, 750, 100);

        //Initialise game objects
        SpriteSheet spriteSheet = new SpriteSheet(context);
        Animator animator = new Animator(spriteSheet.getPlayerSpriteArray());
        player = new Player(context, buttonLeft, buttonRight, buttonJump, 2*500, 500, 32, animator);

        //Initialise game display and center it around the player
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        gameCamera = new GameCamera(displayMetrics.widthPixels, displayMetrics.heightPixels, player);

        setFocusable(true);
    }

    //SurfaceHolder methods
    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        Log.d("Game.java", "surfaceCreated()"); //put message in logcat
        if (gameLoop.getState().equals(Thread.State.TERMINATED)) { //check state of thread to see if it is terminated
            gameLoop = new GameLoop(this, surfaceHolder);
            //then we instantiate a new gameLoop object, because a thread object can only be run once until it is destroyed,
            // so if we wanna run it again we have to create a new one
        }
        gameLoop.startLoop();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int format, int width, int height) {
        Log.d("Game.java", "surfaceChanged()");
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
        Log.d("Game.java", "surfaceDestroyed()");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //handle different touch event actions
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                //do stuff here when button is pressed
                if (buttonLeft.isPressed((double) event.getX(), (double) event.getY())) {
                    //check if left button is pressed
                    buttonLeftId = event.getPointerId(event.getActionIndex());
                    buttonLeft.setIsPressed(true);
                } else if (buttonRight.isPressed((double) event.getX(), (double) event.getY())) {
                    //check if right button is pressed
                    buttonRightId = event.getPointerId(event.getActionIndex());
                    buttonRight.setIsPressed(true);
                } else if (buttonJump.isPressed((double) event.getX(), (double) event.getY())) {
                    //check if the jump button is pressed
                    buttonJumpId = event.getPointerId(event.getActionIndex());
                    buttonJump.setIsPressed(true);
                }
                return true;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                //do stuff here when button is lifted
                if (buttonLeftId == event.getPointerId(event.getActionIndex())) {
                    //check if left button is lifted
                    buttonLeft.setIsPressed(false);
                    }
                if (buttonRightId == event.getPointerId(event.getActionIndex())) {
                    //check if right button is lifted
                    buttonRight.setIsPressed(false);
                }
                if (buttonJumpId == event.getPointerId(event.getActionIndex())) {
                    //check if jump button is lifted
                    buttonJump.setIsPressed(false);
                }
                return true;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        //Draw game objects
        player.draw(canvas, gameCamera);

        //Draw game panels
        performance.draw(canvas);
        buttonLeft.draw(canvas);
        buttonRight.draw(canvas);
        buttonJump.draw(canvas);



    }

    public void update() {

        //update game state
        player.update();

        //update game panel

        gameCamera.update();
    }

    //Other methods
    public void pause() {
        //pause game loop
        gameLoop.stopLoop();
    }

}
