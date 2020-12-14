package com.example.kabubufix.Kuis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.kabubufix.R;

public class SplashKuisActivity extends AppCompatActivity {

    private Animation animation;
    private ImageView imageView;

    private static int SPLASH_TIME_OUT = 5000;

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_kuis);

        imageView = findViewById(R.id.img_anim_kuis);
        animation = AnimationUtils.loadAnimation(this,R.anim.splash_kuis_animation);

        mediaPlayer = MediaPlayer.create(SplashKuisActivity.this,R.raw.kabubu_kuis_voice_opening);
        mediaPlayer.start();

        imageView.setAnimation(animation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashKuisActivity.this, KuisActivity.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_TIME_OUT);
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.release();
        finish();
    }
}
