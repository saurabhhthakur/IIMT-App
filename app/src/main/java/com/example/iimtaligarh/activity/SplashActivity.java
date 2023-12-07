package com.example.iimtaligarh.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import com.example.iimtaligarh.R;
import com.example.iimtaligarh.databinding.SplashScreenBinding;

public class SplashActivity extends AppCompatActivity {
    SplashScreenBinding binding;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(ContextCompat.getColor(SplashActivity.this, R.color.white_custom));
        View decor = SplashActivity.this.getWindow().getDecorView();
        decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        binding = SplashScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Animation imageViewAnimation = AnimationUtils.loadAnimation(SplashActivity.this,R.anim.splash_image_animate);
        Animation textViewAnimation = AnimationUtils.loadAnimation(SplashActivity.this,R.anim.splash_text_animate);
        binding.splashText.startAnimation(textViewAnimation);
        binding.splashImage.startAnimation(imageViewAnimation);


        Intent iNext = new Intent(SplashActivity.this, HomeActivity.class);
        new Handler().postDelayed(() -> {
            startActivity(iNext);
            finish();
        }, 2200);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}