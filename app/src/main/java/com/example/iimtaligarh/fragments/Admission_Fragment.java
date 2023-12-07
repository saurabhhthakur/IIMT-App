package com.example.iimtaligarh.fragments;

import android.content.Intent;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.iimtaligarh.R;
import com.example.iimtaligarh.sharedpreferences.SharedPreferencesHelper;
import com.example.iimtaligarh.activity.LoginSplashActivity;
import com.example.iimtaligarh.databinding.FragmentAdmissionBinding;

public class Admission_Fragment extends Fragment {

    FragmentAdmissionBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().getWindow().setStatusBarColor(ContextCompat.getColor(getContext(),
                R.color.admission_fragment));

        binding = FragmentAdmissionBinding.inflate(getLayoutInflater(), container, false);

        SharedPreferencesHelper sharedPreferencesHelper = new SharedPreferencesHelper(getActivity());

        binding.bca.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), LoginSplashActivity.class));
            sharedPreferencesHelper.saveString("course", "BCA");
        });

        binding.bba.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), LoginSplashActivity.class));
            sharedPreferencesHelper.saveString("course", "BBA");
        });

        binding.bcom.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), LoginSplashActivity.class));
            sharedPreferencesHelper.saveString("course", "B.COM");
        });

        binding.mcom.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), LoginSplashActivity.class));
            sharedPreferencesHelper.saveString("course", "M.COM");
        });

        binding.bsc.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), LoginSplashActivity.class));
            sharedPreferencesHelper.saveString("course", "B.SC");
        });

        binding.msc.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), LoginSplashActivity.class));
            sharedPreferencesHelper.saveString("course", "M.SC");
        });

        return binding.getRoot();

    }

    @Override
    public void onDestroy() {
        binding = null;
        super.onDestroy();
    }
}