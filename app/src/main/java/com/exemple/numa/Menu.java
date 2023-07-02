package com.exemple.numa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Menu extends AppCompatActivity implements View.OnClickListener {

    Button tutorial,start,settings,exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        tutorial = findViewById(R.id.tutorial);
        start = findViewById(R.id.start);
        settings = findViewById(R.id.settings);
        exit = findViewById(R.id.exit);

        tutorial.setOnClickListener(this);
        start.setOnClickListener(this);
        settings.setOnClickListener(this);
        exit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tutorial:
                break;
            case R.id.start:
                Intent intent = new Intent(this,PlayGame.class);
                intent.putExtra("levelChosen",NuMa_Matrix.HARD+"");
                startActivity(intent);
                break;
            case R.id.settings:
                break;
            case R.id.exit:
                System.exit(0);
                break;
            default:
                break;
        }
    }
}