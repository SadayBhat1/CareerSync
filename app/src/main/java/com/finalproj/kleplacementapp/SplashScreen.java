package com.finalproj.kleplacementapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {
    TextView splashtv1;
    ImageView imageView2,imageView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        splashtv1=findViewById(R.id.splashtv1);
        imageView2=findViewById(R.id.imageView2);
        imageView1=findViewById(R.id.imageView1);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(i);

                finish();
            }
        },4600);
        Animation myanimation= AnimationUtils.loadAnimation(SplashScreen.this,R.anim.animation1);
        splashtv1.startAnimation(myanimation);
        Animation myanimation1= AnimationUtils.loadAnimation(SplashScreen.this,R.anim.animation1);
        imageView2.startAnimation(myanimation1);
        Animation myanimation2= AnimationUtils.loadAnimation(SplashScreen.this,R.anim.animation1);
        imageView1.startAnimation(myanimation2);
    }
}
