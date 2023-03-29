package com.example.superplatformgame.gamepanel;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.example.superplatformgame.R;
import com.example.superplatformgame.gameobject.Player;

public class GameScore {

    private Context context;
    private Player player;

    public GameScore(Context context, Player player) {
        this.context = context;
        this.player = player;
    }

    public void drawScore(Canvas canvas, Typeface typeFace) {
        String playerScore = Long.toString(player.getScore());
        //Log.d("GameScore.java", "player score: " + playerScore);
        Paint paint = new Paint();
        int color = ContextCompat.getColor(context, R.color.magenta);
        paint.setColor(color);
        paint.setTextSize(50);
        paint.setTypeface(typeFace);

        canvas.drawText("Score: " + playerScore, 100, 100, paint);
    }
}
