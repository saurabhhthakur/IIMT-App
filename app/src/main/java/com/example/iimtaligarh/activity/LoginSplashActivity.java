package com.example.iimtaligarh.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import android.os.Bundle;
import com.example.iimtaligarh.R;
import com.example.iimtaligarh.databinding.ActivityLoginSplashBinding;

public class LoginSplashActivity extends AppCompatActivity {
    ActivityLoginSplashBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginSplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        NavController navController = Navigation.findNavController(this,R.id.setFragment);

    }


    @Override
    protected void onDestroy() {
        binding = null;
        super.onDestroy();
    }
}