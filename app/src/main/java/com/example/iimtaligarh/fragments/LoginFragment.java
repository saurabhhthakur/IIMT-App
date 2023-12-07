package com.example.iimtaligarh.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.iimtaligarh.R;
import com.example.iimtaligarh.databinding.FragmentLoginBinding;
import com.example.iimtaligarh.databinding.WarningDialogBinding;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import java.util.Objects;
import java.util.regex.Pattern;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;
    private Dialog dialog;
    private Dialog dialog2;
    private WarningDialogBinding warningDialogBinding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(getLayoutInflater(), container, false);
        requireActivity().getWindow().setStatusBarColor(ContextCompat
                .getColor(requireContext(), R.color.login_background));
        View decor = requireActivity().getWindow().getDecorView();
        decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        warningDialogBinding = WarningDialogBinding.inflate(getLayoutInflater());

        dialog2 = new Dialog(getContext());
        dialog2.setContentView(warningDialogBinding.getRoot());
        dialog2.setCancelable(false);
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog);
        dialog.setCanceledOnTouchOutside(false);

        binding.forgotBtn.setOnClickListener(v -> {
            Navigation.findNavController(binding.getRoot())
                    .navigate(R.id.action_loginFragment_to_forgetFragment);
        });

        binding.registerBtn.setOnClickListener(v ->
                Navigation.findNavController(binding.getRoot())
                        .navigate(R.id.action_loginFragment_to_signupFragment));

        binding.loginBtn.setOnClickListener(v -> login());


        return binding.getRoot();
    }

    private boolean password() {
        final Pattern PASSWORD_PATTERN =
                Pattern.compile("^" +
                        "(?=.*[a-z])" +         //at least 1 lower case letter
                        "(?=.*[A-Z])" +         //at least 1 upper case letter
                        "(?=.*[@#$%^&+=])" +    //at least 1 special character
                        "(?=\\S+$)" +           //no white spaces
                        ".{4,}" +               //at least 4 characters
                        "$");

   String password = Objects.requireNonNull(binding.passwordLayout.getEditText()).getText().toString().trim();

        if (password.isEmpty()) {
            binding.passwordLayout.setError("Password can't be empty!");
            binding.passwordLayout.setErrorEnabled(true);
            return false;
        } else if (!PASSWORD_PATTERN.matcher(password).matches()) {
            binding.passwordLayout.setError("Password too weak");
            binding.passwordLayout.setErrorEnabled(true);
            return false;
        } else {
            binding.passwordLayout.setError(null);
            binding.passwordLayout.setErrorEnabled(false);
            return true;
        }

    }

    private boolean email() {
        String email = Objects.requireNonNull(binding.usernameLayout.getEditText()).getText().toString().trim();

        if (email.isEmpty()) {
            binding.usernameLayout.setError("E-mail must be required!");
            binding.usernameLayout.setErrorEnabled(true);
            return false;
        } else if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.usernameLayout.setError(null);
            binding.usernameLayout.setErrorEnabled(false);
            return true;
        } else {
            binding.usernameLayout.setError("Please enter valid e-mail");
            binding.usernameLayout.setErrorEnabled(true);
            return false;
        }
    }

    @SuppressLint("SetTextI18n")
    private void login() {
        String password = Objects.requireNonNull(binding.passwordLayout.getEditText()).getText().toString();
        String email = Objects.requireNonNull(binding.usernameLayout.getEditText()).getText().toString();

        if (!email() | !password()) {
            return;
        } else {
        dialog.show();

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                dialog.dismiss();
                if (FirebaseAuth.getInstance().getCurrentUser().isEmailVerified()) {
                    Navigation.findNavController(binding.getRoot())
                            .navigate(R.id.action_loginFragment_to_dashboard);
                } else {
                    dialog2.show();
                    warningDialogBinding.cutImage.setOnClickListener(v -> dialog2.dismiss());
                    warningDialogBinding.heading.setText("In Valid User");
                    warningDialogBinding.message.setText("Please verify your email address...");
                }

            } else {
                warningDialogBinding.cutImage.setOnClickListener(v -> dialog2.dismiss());
                dialog.dismiss();
                try {
                    throw Objects.requireNonNull(task.getException());
                } catch (FirebaseAuthInvalidUserException invalidUserException) {
                    String errorCode = invalidUserException.getErrorCode();
                    if (errorCode.equals("ERROR_USER_NOT_FOUND")) {
                        dialog2.show();
                        warningDialogBinding.heading.setText("User not found");
                        warningDialogBinding.message.setText("Please register to connect with us.");
                    } else if (errorCode.equals("ERROR_USER_DISABLED")) {
                        dialog2.show();
                        warningDialogBinding.heading.setText("User disable");
                        warningDialogBinding.message.setText("Please contact to developer of iimt college");
                    }
                } catch (
                        FirebaseAuthInvalidCredentialsException invalidCredentialsException) {
                    String errorCode = invalidCredentialsException.getErrorCode();
                    if (errorCode.equals("ERROR_WRONG_PASSWORD")) {
                        dialog2.show();
                        warningDialogBinding.heading.setText("Incorrect Password");
                warningDialogBinding.message.setText("The password you entered is incorrect. Please try again.");
                    } else if (errorCode.equals("ERROR_INVALID_EMAIL")) {
                        dialog2.show();
                        warningDialogBinding.heading.setText("Invalid Email");
                        warningDialogBinding.message.setText("Please enter a valid email.");
                    }
                } catch (FirebaseNetworkException networkException) {
                    dialog2.show();
                    warningDialogBinding.heading.setText("Network Problem");
                    warningDialogBinding.message.setText("Something problem in network. Please try again.");
                } catch (Exception e) {
                    dialog2.show();
                    warningDialogBinding.heading.setText("Something went wrong");
                    warningDialogBinding.message.setText(e.getMessage() + " Please try again.");
                }
            }
        });

    }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}