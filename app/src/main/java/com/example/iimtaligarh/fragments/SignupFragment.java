package com.example.iimtaligarh.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.iimtaligarh.R;
import com.example.iimtaligarh.databinding.FragmentSignupBinding;
import com.example.iimtaligarh.databinding.WarningDialogBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

public class SignupFragment extends Fragment {
    private FragmentSignupBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private Dialog dialog;
    private Dialog dialog2;
    private WarningDialogBinding warningDialogBinding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSignupBinding.inflate(getLayoutInflater(), container, false);

        warningDialogBinding = WarningDialogBinding.inflate(getLayoutInflater());

        dialog2 = new Dialog(getContext());
        dialog2.setContentView(warningDialogBinding.getRoot());
        dialog2.setCancelable(false);
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        mAuth = FirebaseAuth.getInstance();

        binding.signupBtn.setOnClickListener(v -> {
            if (!email() || !password()) {
                return;
            } else
                registerUserdata();
        });

        return binding.getRoot();
    }


    @SuppressLint("ResourceAsColor")
    private boolean password() {
        final Pattern PASSWORD_PATTERN =
                Pattern.compile("^" +
                        "(?=.*[a-z])" +         //at least 1 lower case letter
                        "(?=.*[A-Z])" +         //at least 1 upper case letter
                        "(?=.*[@#$%^&+=])" +    //at least 1 special character
                        "(?=\\S+$)" +           //no white spaces
                        ".{4,}" +               //at least 4 characters
                        "$");

        String password = Objects.requireNonNull(binding.registerPassword.getEditText()).getText().toString().trim();

        if (password.isEmpty()) {
            binding.registerPassword.setError("Password can't be empty!");
            binding.registerPassword.setBoxStrokeColor(android.R.color.holo_red_dark);
            binding.registerPassword.setErrorEnabled(true);
            binding.registerPassword.setBoxStrokeWidth(6);
            binding.registerPassword.setErrorIconDrawable(0);
            return false;
        } else if (!PASSWORD_PATTERN.matcher(password).matches()) {
            binding.registerPassword.setError("Password too weak");
            binding.registerPassword.setErrorEnabled(true);
            binding.registerPassword.setBoxStrokeColor(android.R.color.holo_red_dark);
            binding.registerPassword.setBoxStrokeWidth(6);
            binding.registerPassword.setErrorIconDrawable(0);
            return false;
        } else {
            binding.registerPassword.setBoxStrokeColor(R.color.purple);
            binding.registerPassword.setError(null);
            binding.registerPassword.setBoxStrokeWidth(3);
            binding.registerPassword.setErrorEnabled(false);
            return true;
        }

    }

    @SuppressLint("ResourceAsColor")
    private boolean email() {
        String email = Objects.requireNonNull(binding.registerEmail.getEditText()).getText().toString().trim();
        if (email.isEmpty()) {
            binding.registerEmail.setError("E-mail must be required!");
            binding.registerEmail.setErrorEnabled(true);
            binding.registerEmail.setBoxStrokeColor(android.R.color.holo_red_dark);
            binding.registerEmail.setBoxStrokeWidth(6);
            binding.registerEmail.setErrorIconDrawable(0);
            return false;
        } else if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.registerEmail.setError(null);
            binding.registerEmail.setErrorEnabled(false);
            binding.registerEmail.setBoxStrokeWidth(3);
            binding.registerEmail.setBoxStrokeColor(R.color.purple);
            return true;
        } else {
            binding.registerEmail.setError("Please enter valid e-mail");
            binding.registerEmail.setErrorEnabled(true);
            binding.registerEmail.setBoxStrokeColor(android.R.color.holo_red_dark);
            binding.registerEmail.setBoxStrokeWidth(6);
            binding.registerEmail.setErrorIconDrawable(0);
            return false;
        }
    }

    @SuppressLint("SetTextI18n")
    private void registerUserdata() {
        String email = Objects.requireNonNull(binding.registerEmail.getEditText()).getText().toString();
        String password = Objects.requireNonNull(binding.registerPassword.getEditText()).getText().toString();

        dialog = new Dialog(Objects.requireNonNull(getContext()));
        dialog.setContentView(R.layout.dialog);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        Map<String, Object> data = new HashMap<>();
        data.put("Email id", email);
        data.put("Password", password);

        mAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(authResult -> {
            mUser = mAuth.getCurrentUser();
            mUser.sendEmailVerification().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    dialog.dismiss();
                    FirebaseAuth.getInstance().signOut();
                    Navigation.findNavController(binding.getRoot())
                            .navigate(R.id.action_signupFragment_to_loginFragment);
                    Toast.makeText(getContext(), "Email verification code send to your email address...", Toast.LENGTH_SHORT).show();

                } else {
                    dialog2.show();
                    warningDialogBinding.cutImage.setOnClickListener(v -> dialog2.dismiss());
                    warningDialogBinding.heading.setText("Something Wrong");
                    warningDialogBinding.message.setText("Due to some technical reason code does not send to your email");
                }
            });


        }).addOnFailureListener(e -> {
            dialog.dismiss();
            if (e instanceof FirebaseAuthUserCollisionException) {
                dialog2.show();
                warningDialogBinding.cutImage.setOnClickListener(v -> dialog2.dismiss());
                warningDialogBinding.heading.setText("You're already a member");
                warningDialogBinding.message.setText("This email address is already in used by another account.");
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}