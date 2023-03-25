package com.example.superplatformgame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class StartScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.start_screen);

        Button start = findViewById(R.id.button);
        Button instruction = findViewById(R.id.instructions);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent switchToStartGame = new Intent(StartScreen.this, MainActivity.class);
                startActivity(switchToStartGame);
            }
        });

        instruction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent switchToInstructions = new Intent(StartScreen.this, Instructions.class);
                startActivity(switchToInstructions);
            }
        });
    }
}
