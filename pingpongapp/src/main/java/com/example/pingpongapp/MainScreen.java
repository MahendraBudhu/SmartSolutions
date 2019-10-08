package com.example.pingpongapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
    }

    public void config(View view){ //Goes to setup screen
        Intent intent = new Intent (this, SetupScreen.class);
        startActivity(intent);
    }

    public void settings(View view){ //Goes to settings screen
        Intent intent = new Intent (this, StatisticsScreen.class);
        startActivity(intent);
    }

    public void random(View view){ //Goes to settings screen
        Intent intent = new Intent (this, RandomScreen.class);
        startActivity(intent);
    }
}
