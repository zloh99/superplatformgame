package com.example.superplatformgame;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.example.superplatformgame.gameobject.GameObject;
import com.example.superplatformgame.gameobject.Player;
import com.example.superplatformgame.gamepanel.Performance;

public class Game extends SurfaceView implements SurfaceHolder.Callback {
    private final Player player;
    private GameLoop gameLoop;
    private Performance performance;
    private GameCamera gameCamera;

    public Game(Context context) {
        super(context);
        //Game constructor - initialise everything

        //Get surface holder and add callback
        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        gameLoop = new GameLoop(this, surfaceHolder);

        //Initialise game panels (all graphical objects that do not interact with any game objects)
        performance = new Performance(context, gameLoop);

        //Initialise game objects
        player = new Player(context, /**buttons,*/ 2*500, 500, 32/**, animator*/);

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
    public void draw(Canvas canvas) {
        super.draw(canvas);

        //Draw game objects
        player.draw(canvas, gameCamera);

        //Draw game panels
        performance.draw(canvas);

    }

    public void update() {

        //update game state
        player.update();

        gameCamera.update();
    }

    //Other methods
    public void pause() {
        //pause game loop
        gameLoop.stopLoop();
    }

}
