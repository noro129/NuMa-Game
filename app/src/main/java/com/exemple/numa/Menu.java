package com.exemple.numa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Menu extends AppCompatActivity implements View.OnClickListener {


    int levelChosen;
    Button tutorial,start,settings,exit;
    TextView slideLeft,slideRight,
            levelBeginner,levelEasy,levelMedium,levelHard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_menu);

        levelChosen=NuMa_Matrix.BEGINNER;

        levelBeginner = findViewById(R.id.levelBeginner);
        levelEasy = findViewById(R.id.levelEasy);
        levelMedium = findViewById(R.id.levelMedium);
        levelHard = findViewById(R.id.levelHard);

        tutorial = findViewById(R.id.tutorial);
        start = findViewById(R.id.start);
        settings = findViewById(R.id.settings);
        exit = findViewById(R.id.exit);
        slideLeft = findViewById(R.id.slideLeft);
        slideRight = findViewById(R.id.slideRight);

        slideLeft.setText("<");
        slideLeft.setSoundEffectsEnabled(false);
        slideRight.setText(">");
        slideRight.setSoundEffectsEnabled(false);
        slideLeft.setVisibility(View.INVISIBLE);

        tutorial.setOnClickListener(this);
        start.setOnClickListener(this);
        settings.setOnClickListener(this);
        exit.setOnClickListener(this);
        slideLeft.setOnClickListener(this);
        slideRight.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tutorial:
                break;
            case R.id.start:
                Intent intent = new Intent(this,PlayGame.class);
                intent.putExtra("levelChosen",levelChosen+"");
                startActivity(intent);
                break;
            case R.id.settings:
                break;
            case R.id.exit:
                System.exit(0);
                finish();
                break;
            case R.id.slideLeft:
                if (levelChosen==NuMa_Matrix.HARD) {
                    slideRight.setClickable(true);
                    slideRight.setVisibility(View.VISIBLE);
                    translate(-2);
                    levelChosen=NuMa_Matrix.MEDIUM;
                } else if (levelChosen==NuMa_Matrix.MEDIUM) {
                    translate(-1);
                    levelChosen=NuMa_Matrix.EASY;
                } else if(levelChosen==NuMa_Matrix.EASY) {
                    translate(0);
                    levelChosen=NuMa_Matrix.BEGINNER;
                    slideLeft.setClickable(false);
                    slideLeft.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.slideRight:
                if (levelChosen==NuMa_Matrix.BEGINNER) {
                    slideLeft.setClickable(true);
                    slideLeft.setVisibility(View.VISIBLE);
                    translate(-1);
                    levelChosen=NuMa_Matrix.EASY;
                } else if (levelChosen==NuMa_Matrix.EASY) {
                    levelChosen=NuMa_Matrix.MEDIUM;
                    translate(-2);
                } else if (levelChosen==NuMa_Matrix.MEDIUM) {
                    levelChosen=NuMa_Matrix.HARD;
                    translate(-3);
                    slideRight.setClickable(false);
                    slideRight.setVisibility(View.INVISIBLE);
                }
                break;
        }
    }

    public void translate(int x) {
        levelBeginner.animate().translationX(levelBeginner.getWidth() * x).setDuration(500);
        levelEasy.animate().translationX(levelEasy.getWidth() * x).setDuration(500);
        levelMedium.animate().translationX(levelMedium.getWidth() * x).setDuration(500);
        levelHard.animate().translationX(levelHard.getWidth() * x).setDuration(500);
    }
}