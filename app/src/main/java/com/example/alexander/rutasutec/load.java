package com.example.alexander.rutasutec;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

public class load extends AppCompatActivity {
    private static final long SPLASH_SCREEN_DELAY = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set portrait orientation
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // Hide title bar
        setContentView(R.layout.activity_load);
        ImageView img = (ImageView)findViewById(R.id.loadingView);
        img.setBackgroundResource(R.drawable.loading);
        AnimationDrawable objeto = (AnimationDrawable)img.getBackground();
        objeto.start();

        TimerTask tiempo =  new TimerTask() {
            @Override
            public void run() {
                Intent inicio = new Intent(getApplicationContext(), com.example.alexander.rutasutec.inicio.class);
                startActivity(inicio);
            }
        };

        Timer timer = new Timer();
        timer.schedule(tiempo,SPLASH_SCREEN_DELAY);
    }
}
