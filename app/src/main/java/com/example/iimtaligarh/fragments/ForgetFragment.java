package com.example.iimtaligarh.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.iimtaligarh.databinding.FragmentForgetBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class ForgetFragment extends Fragment {
    FragmentForgetBinding binding;
    private FirebaseAuth auth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentForgetBinding.inflate(getLayoutInflater());
        auth = FirebaseAuth.getInstance();

        binding.resetPasswordButton.setOnClickListener(v -> {
            String email = binding.emailEditText.getText().toString().trim();
            if (!email.isEmpty()) {
                auth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
                    if (!email()) {
                        return;
                    } else {

                        if (task.isSuccessful()) {
                            Toast.makeText(getActivity(),
                                    "Password reset email sent",
                                    Toast.LENGTH_SHORT).show();
                            binding.emailEditText.setText("");
                        } else {
                            Toast.makeText(getActivity(),
                                    "Failed to send reset email",
                                    Toast.LENGTH_SHORT).show();
                        }


                    }


                });

            }

        });


        return binding.getRoot();
    }

    private boolean email() {
        String email = Objects.requireNonNull(binding.emailLayout.getEditText()).getText().toString().trim();

        if (email.isEmpty()) {
            binding.emailLayout.setError("E-mail must be required!");
            binding.emailLayout.setErrorEnabled(true);
            return false;
        } else if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.emailLayout.setError(null);
            binding.emailLayout.setErrorEnabled(false);
            return true;
        } else {
            binding.emailLayout.setError("Please enter valid e-mail");
            binding.emailLayout.setErrorEnabled(true);
            return false;
        }
    }


    @Override
    public void onDestroy() {
        binding = null;
        super.onDestroy();
    }
}