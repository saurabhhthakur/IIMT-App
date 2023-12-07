package com.example.iimtaligarh.fragments;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.iimtaligarh.R;
import com.example.iimtaligarh.sharedpreferences.SharedPreferencesHelper;
import com.example.iimtaligarh.databinding.FragmentLoginSplashBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login_Splash_Fragment extends Fragment {

  FragmentLoginSplashBinding binding;
  FirebaseUser user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoginSplashBinding.inflate(getLayoutInflater(),container,false);
        getActivity().getWindow().setStatusBarColor(ContextCompat.getColor(getContext(), R.color.login_background));
        View decor = getActivity().getWindow().getDecorView();
        decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        user = FirebaseAuth.getInstance().getCurrentUser();

        binding.College.setOnClickListener(v -> {
            binding.Students.setBackgroundResource(0);
            binding.buttonCtrl.setBackgroundResource(R.drawable.student_college_layout);
            binding.College.setBackgroundResource(R.drawable.login_splash_student_btn);

            if (user!=null) {
                Navigation.findNavController(binding.getRoot())
                        .navigate(R.id.action_login_Splash_Fragment_to_dashboard);

            }else {

                Navigation.findNavController(binding.getRoot())
                        .navigate(R.id.action_login_Splash_Fragment_to_loginFragment);
            }
            });

        binding.Students.setOnClickListener(v -> {
            binding.College.setBackgroundResource(0);
            binding.Students.setBackgroundResource(R.drawable.login_splash_student_btn);
            binding.buttonCtrl.setBackgroundResource(R.drawable.student_college_layout);

            if (user!=null) {
                Navigation.findNavController(binding.getRoot())
                        .navigate(R.id.action_login_Splash_Fragment_to_dashboard);

            }else {
                Navigation.findNavController(binding.getRoot())
                        .navigate(R.id.action_login_Splash_Fragment_to_loginFragment);
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        SharedPreferencesHelper db = new SharedPreferencesHelper(getActivity());
        db.clearPreferences("course");
        super.onDestroy();
        binding = null;
    }
}