package com.devaton.cryptotracker;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        this.moveTaskToBack(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        animation();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreenActivity.this, HomeActivity.class));
                finish();
            }
        }, 3000);
    }

    private void animation() {
        TextView textView = (TextView) findViewById(R.id.zysit_text_logo);
        textView.setAlpha(1.0F);
        Animation localAnimation = AnimationUtils.loadAnimation(this, R.anim.translate_top_to_center);
        textView.startAnimation(localAnimation);
    }
}
