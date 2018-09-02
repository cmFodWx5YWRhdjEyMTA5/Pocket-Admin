package com.example.ais.pocketadmin.Welcome;

/**
 * Created by AIS on 04-Jul-17.
 */

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.example.ais.pocketadmin.R;
import com.example.ais.pocketadmin.Welcome.Logins.LoginActivityMain;

public class Splash extends AppCompatActivity {
    private static final float BYTES_PX = 4.0f;
    private final int SPLASH_DISPLAY_LENGTH = 5000;
    ImageView logo_disp, bg, gyroView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_activity_splash);


        logo_disp = (ImageView) findViewById(R.id.logo_disp);
        gyroView = (ImageView) findViewById(R.id.loading_animation);
        gyroView.setBackgroundResource(R.drawable.loading_animation);


        AnimationDrawable gyroAnimation = (AnimationDrawable) gyroView.getBackground();
        gyroAnimation.start();









/* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(Splash.this, LoginActivityMain.class);
                Splash.this.startActivity(mainIntent);
                Splash.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }



/*
    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        finish();
    } */

}
