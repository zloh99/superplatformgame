package com.example.superplatformgame.gameobject;

import android.content.Context;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import com.example.superplatformgame.GameLoop;
import com.example.superplatformgame.R;

/**
 * Player is a character in the game controllable by the player with the buttons
 * The Player class is an extension of the Hitbox class, which is an extension of the GameObject class
 */

public class Player extends Hitbox {
    public static final double SPEED_PIXELS_PER_SECOND = 400.0;
    public static final double MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS; //pixels/s divided by updates/s = pixels/update
    public static final int MAX_HEALTH_POINTS = 10;
    //private final Buttons buttons;
    //private HealthBar healthBar;
    //private int healthPoints;
    //private Animator animator;
    //private PlayerState playerState;

    /**
     * Constructor for player class
     * @param context
     * //@param buttons
     * @param positionX
     * @param positionY
     * @param radius
     * //@param animator
     */
    public Player(Context context, /**Buttons buttons,*/ double positionX, double positionY, double radius/**, Animator animator*/) {
        super(context, ContextCompat.getColor(context, R.color.player), positionX, positionY, radius);
        //this.buttons = buttons;
        this.radius = radius;
        //this.animator = animator;
        //this.playerState = new PlayerState(this);
        paint = new Paint();
        int color = ContextCompat.getColor(context, R.color.player);
        paint.setColor(color);

        //this.healthBar = new HealthBar(context,this);
        //this.healthPoints = MAX_HEALTH_POINTS;
    }

    @Override
    public void update() {

    }
}
