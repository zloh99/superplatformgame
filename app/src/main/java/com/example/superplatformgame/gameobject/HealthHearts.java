package com.example.superplatformgame.gameobject;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

/**
 * Health hearts is a class that handles the drawing of hearts on the screen in correspondence with the player's health
 */
public class HealthHearts {

    private final Player player;

    public HealthHearts(Context context, Player player) {
        this.player = player;

    }

    public void draw(Canvas canvas) {
        // get the width and height of the canvas
        int canvasWidth = canvas.getWidth();
        int canvasHeight = canvas.getHeight();

        // define the size of the hearts and the gap between them
        int heartSize = 100;
        int gap = 20;

        // define the paint for the hearts
        Paint paint = new Paint();
        paint.setColor(Color.RED);

        // draw three hearts side by side
        for (int i = 0; i < player.getHealthHearts(); i++) {
            // calculate the position of the heart
            int x = canvasWidth - (i + 1) * (heartSize + gap);
            int y = 0;

            // create a path for the heart
            Path path = new Path();
            path.moveTo(x + heartSize / 2, y + heartSize / 5);
            path.cubicTo(x + heartSize / 8, y, x, y + heartSize / 15,
                    x, y + heartSize / 3);
            path.cubicTo(x, y + heartSize * 2 / 3, x + heartSize / 2,
                    y + heartSize * 5 / 6, x + heartSize / 2,
                    y + heartSize);
            path.cubicTo(x + heartSize / 2, y + heartSize * 5 / 6,
                    x + heartSize, y + heartSize * 2 / 3, x + heartSize,
                    y + heartSize / 3);
            path.cubicTo(x + heartSize, y + heartSize / 15,
                    x + heartSize * 7 / 8, y, x + heartSize / 2,
                    y + heartSize / 5);

            // draw the heart
            canvas.drawPath(path, paint);
        }
    }


}
