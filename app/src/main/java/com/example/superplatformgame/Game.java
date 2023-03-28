package com.example.superplatformgame;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.example.superplatformgame.gameobject.Animator;
import com.example.superplatformgame.gameobject.Bird;
import com.example.superplatformgame.gameobject.Enemy;
import com.example.superplatformgame.gameobject.HealthHearts;
import com.example.superplatformgame.gameobject.Player;
import com.example.superplatformgame.gameobject.Saw;
import com.example.superplatformgame.gameobject.Wolf;
import com.example.superplatformgame.gamepanel.ButtonJump;
import com.example.superplatformgame.gamepanel.ButtonLeft;
import com.example.superplatformgame.gamepanel.ButtonRight;
import com.example.superplatformgame.gamepanel.Performance;
import com.example.superplatformgame.graphics.SkyBox;
import com.example.superplatformgame.graphics.SpriteSheet;
import com.example.superplatformgame.map.MapLayout;
import com.example.superplatformgame.map.Tilemap;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * The game class handles everything in the game, including the game loop, drawing all objects to the canvas,
 * and updating all the relevant objects in the game.
 */

public class Game extends SurfaceView implements SurfaceHolder.Callback {
    private Player player; //player object
    private GameLoop gameLoop; //game loop
    private Performance performance; //game panel object that shows average UPS and FPS
    private GameCamera gameCamera; //game camera object
    //private SkyBox skyBox;
    private List<SkyBox> skyBoxList = new ArrayList<SkyBox>(); //list to keep track of how many skybox objects there are

    private List<Enemy> enemyList = new ArrayList<>();
    private Tilemap tileMap; //tilemap object
    private ButtonLeft buttonLeft; //button to move player left
    private int buttonLeftId = 0; //id to store individual touch events happening on the left button
    private ButtonRight buttonRight; //button to move player right
    private int buttonRightId = 0; //id to store individual touch events happening on the right button
    private ButtonJump buttonJump; //button to make player jump
    private int buttonJumpId = 0; //id to store individual touch events happening on the jump button
    private HealthHearts healthHearts;
    private GameOver gameOver; // check game over



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
        buttonLeft = new ButtonLeft(context, 100, 850, 150, 150);
        buttonRight = new ButtonRight(context, 300, 850, 150, 150);
        buttonJump = new ButtonJump(context, 1850, 860, 140, 140);
        gameOver = new GameOver(getContext());

        //Initialise game objects
        SpriteSheet spriteSheet = new SpriteSheet(context);
        Animator animator = new Animator(spriteSheet.getPlayerSpriteArray(), spriteSheet.getEnemySpriteArray());
        player = new Player(context, buttonLeft, buttonRight, buttonJump, 1000, 200, 32, animator);

        healthHearts = new HealthHearts(context, player);

        //Initialise game display and center it around the player
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        MapLayout mapLayout = new MapLayout(Tilemap.MapType.GRASS_MAP);
        gameCamera = new GameCamera(displayMetrics.widthPixels, displayMetrics.heightPixels, player, mapLayout);

        //Initialise game graphics
        skyBoxList.add(new SkyBox(spriteSheet));
        tileMap = new Tilemap(spriteSheet);

        //Generate enemies
        int numEnemies = 2;
        double probWolves = 0.0;
        double probBirds = 1; //0.3
        double probSaws = 0.0; //0.2

        for (int i = 0; i < numEnemies; i++) {
            double probability = Math.random();
            if (probability < probWolves) {
                Enemy e = new Wolf(context, (double) i * 600, 590, ThreadLocalRandom.current().nextDouble(200, 400),
                        0, animator);
                enemyList.add(e);
            } else if (probability-probWolves < probBirds) {
                Enemy e = new Bird(context, (double) i * 600, 590, ThreadLocalRandom.current().nextDouble(200, 400),
                        0, animator);
                enemyList.add(e);
            } else {
                Enemy e = new Saw(context, (double) i * 600, 590, ThreadLocalRandom.current().nextDouble(200, 400),
                        0, animator);
                enemyList.add(e);
            }
        }

        setFocusable(true);
    }

    public void reset(Context context) {
        //Get surface holder and add callback
        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        //initialise game loop
        //gameLoop = new GameLoop(this, surfaceHolder);

        //Initialise game panels (all graphical objects that do not interact with any game objects)
        //performance = new Performance(context, gameLoop);
        buttonLeft = new ButtonLeft(context, 100, 850, 150, 150);
        buttonRight = new ButtonRight(context, 300, 850, 150, 150);
        buttonJump = new ButtonJump(context, 1850, 860, 140, 140);
        gameOver = new GameOver(getContext());

        //Initialise game objects
        SpriteSheet spriteSheet = new SpriteSheet(context);
        Animator animator = new Animator(spriteSheet.getPlayerSpriteArray(), spriteSheet.getEnemySpriteArray());
        player = new Player(context, buttonLeft, buttonRight, buttonJump, 1000, 200, 32, animator);

        healthHearts = new HealthHearts(context, player);

        //Initialise game display and center it around the player
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        gameCamera = new GameCamera(displayMetrics.widthPixels, displayMetrics.heightPixels, player, new MapLayout(Tilemap.MapType.GRASS_MAP));

        //Initialise game graphics
        skyBoxList.add(new SkyBox(spriteSheet));
        tileMap = new Tilemap(spriteSheet);

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
                } else if (player.getHealthHearts() <= 0) {
                    //check if there is a game over screen
                    if (gameOver.isPressed((double) event.getX(), (double) event.getY())) {
                        gameOver.setIsPressed(true);
                        Log.d("Game.java", "Game Over");
                        Log.d("Game.java", "isRunning = " + gameLoop.getIsRunning());
                        if (gameLoop.getState().equals(Thread.State.TERMINATED)) {
                            Log.d("Game.java", "Loading new game...");
                            gameLoop = new GameLoop(this, getHolder());
                            reset(getContext());
                            gameLoop.startLoop();//check state of thread to see if it is terminated
                            //then we instantiate a new gameLoop object, because a thread object can only be run once until it is destroyed,
                            // so if we wanna run it again we have to create a new one
                        } else {
                            Log.d("Game.java", "Thread is still running");
                        }
                    }
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

        //draw skybox first before everything else
        for (SkyBox skyBox: skyBoxList) {
            skyBox.draw(canvas, gameCamera, skyBoxList.indexOf(skyBox)* skyBox.getWidth(), 0);
        }
        //Log.d("Game.java", "skyBoxList size: " + skyBoxList.size());

        //Draw game objects
        player.draw(canvas, gameCamera);

        //Draw enemies
        enemyList.forEach(enemy -> enemy.draw(canvas, gameCamera));

        //draw tilemap
        tileMap.draw(canvas, gameCamera);

        //Draw game panels
        performance.draw(canvas);
        buttonLeft.draw(canvas);
        buttonRight.draw(canvas);
        buttonJump.draw(canvas);
        healthHearts.draw(canvas);

        //if player's health = 0, then draw game over screen
        if(player.getHealthHearts() <= 0) {
            gameOver.draw(canvas);
        }

    }

    public void update() {

        // Stop updating the game if player is dead
        if(player.getHealthHearts() <= 0) {
            gameLoop.stopLoop();
            return;
        }

        //update game state
        player.update(gameCamera, tileMap);

        //spawn new skybox if camera isn't wholly contained in one
        if (gameCamera.getScreenRight() >= skyBoxList.size()*2560) {
            skyBoxList.add(new SkyBox(new SpriteSheet(getContext())));
            //Log.d("Game.java", "Add Skybox");
        }

        //Draw enemies
        enemyList.forEach(enemy -> enemy.update(gameCamera, tileMap));

        //Check for collision in X
        /*
        if(tileMap.isColliding(player, gameCamera, true, false)) {
            player.moveBackX();
            Log.d("Game.java", "collisionStatusX = true");

        }
        */

        //check for collision in Y

        if(!tileMap.isColliding(player, gameCamera, false, true)) {
            //Log.d("Game.java", "collisionStatusY = false");
            //player.setIsAirborne(true);
            //Log.d("Game.java", "Airborne = true");
            //player.setHealthHearts(player.getHealthHearts() - 1);
        }

        if(tileMap.isColliding(player, gameCamera, false, true)) {
            //Log.d("Game.java", "collisionStatusY = true");
            player.moveBackY();
            player.setPlayerVelocityY(0);
        }


        enemyList.forEach(enemy -> {
            if(tileMap.isColliding(enemy, gameCamera, false, true)) {
                //Log.d("Game.java", "collisionStatusY = true");
                enemy.moveBackY();
                enemy.setPlayerVelocityY(0);
            }
        });


        //check if player has fallen into a chasm
        if(player.getPositionY() > gameCamera.getMapBottomY()) {
            player.setHealthHearts(0);
        }

        List<Enemy> deadEnemies = new ArrayList<>();
        Rect playerRect = player.getFuturePlayerRect(gameCamera);

        int iFrames = player.getIFrames();

        // If player has invincibility frames then # of those frames are decremented
        if (iFrames > 0) {
            player.setIFrames(iFrames - 1);
        }

        enemyList.forEach(enemy -> {
            // Check to see if enemy has fallen off, so tracking can stop
            if(enemy.getPositionY() > gameCamera.getMapBottomY()) {
                deadEnemies.add(enemy);
            }
            else {
                Rect enemyRect = enemy.getFuturePlayerRect(gameCamera);

                // Check for enemy collision
                if (enemyRect.intersect(playerRect)) {
                    // If collision is from top then the enemy is dead
                    if (enemy.getDamageable() && (enemyRect.top + SpriteSheet.SPRITE_HEIGHT_PIXELS/2) >= playerRect.bottom) {
                        deadEnemies.add(enemy);

                        // PLayer gets points for every enemy they kill
                        player.setScore(player.getScore() + enemy.getScorePoints());
                    }
                    // Otherwise player is hurt and gets invincibility frames
                    else if (iFrames == 0) {
                        player.setHealthHearts(player.getHealthHearts()-1);
                        player.setIFrames(10);
                    }
                }
            }
        });

        // Dead enemies are removed from tracking
        deadEnemies.forEach(enemy -> enemyList.remove(enemy));

        //update game panel

        //update gameCamera after all other updates
        gameCamera.update();
    }

    //Other methods
    public void pause() {
        //pause game loop
        gameLoop.stopLoop();
    }

}
