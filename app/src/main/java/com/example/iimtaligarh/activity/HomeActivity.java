package com.example.iimtaligarh.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import com.example.iimtaligarh.fragments.Admission_Fragment;
import com.example.iimtaligarh.fragments.Contact_Fragment;
import com.example.iimtaligarh.fragments.Home_Fragment;
import com.example.iimtaligarh.fragments.Notification_Fragment;
import com.example.iimtaligarh.R;
import com.example.iimtaligarh.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity {

    ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, new Home_Fragment()).commit();

        binding.chipNavigation.setItemSelected(R.id.home, true);

        binding.chipNavigation.setOnItemSelectedListener(i -> {
            if (i == R.id.home) {
                // for white icon of status
                View decor = HomeActivity.this.getWindow().getDecorView();
                decor.setSystemUiVisibility(0);
                loadFragment(new Home_Fragment());
            } else if (i == R.id.admission) {
                View decor = HomeActivity.this.getWindow().getDecorView();
                decor.setSystemUiVisibility(0);
                loadFragment(new Admission_Fragment());
            } else if (i == R.id.notification) {
                // for black icon of status
                View decor = HomeActivity.this.getWindow().getDecorView();
                decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                loadFragment(new Notification_Fragment());
            } else {
                View decor = HomeActivity.this.getWindow().getDecorView();
                decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                loadFragment(new Contact_Fragment());
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (binding.chipNavigation.getSelectedItemId() == R.id.home)
            super.onBackPressed();
        else
            binding.chipNavigation.setItemSelected(R.id.home, true);
    }

    public void loadFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left,
                android.R.anim.slide_in_left, android.R.anim.slide_out_right);

        ft.replace(R.id.container, fragment);

        ft.commit();
    }

    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }


}