package com.example.iimtaligarh.fragments;

import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.iimtaligarh.R;
import com.example.iimtaligarh.sharedpreferences.SharedPreferencesHelper;
import com.example.iimtaligarh.databinding.FragmentDashboardBinding;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Dashboard extends Fragment {
    FragmentDashboardBinding binding;
    FirebaseUser user;
    Dialog dialog;
    private DatabaseReference myRef;
    String gender;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDashboardBinding.inflate(getLayoutInflater());

        myRef = FirebaseDatabase.getInstance().getReference();

        SharedPreferencesHelper sharedPreferencesHelper = new SharedPreferencesHelper(getActivity());
        String savedUsername = sharedPreferencesHelper.getString("course", "null");

        binding.course.setText(savedUsername);

        user = FirebaseAuth.getInstance().getCurrentUser();
        binding.emailId.setText(user.getEmail());
        binding.logout.setOnClickListener(v -> {
            if (user != null) {
                FirebaseAuth.getInstance().signOut();
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_dashboard_to_login_Splash_Fragment);
            }
        });

        binding.radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton selectedRadioButton = binding.getRoot().findViewById(checkedId);
            if (selectedRadioButton != null)
                gender = selectedRadioButton.getText().toString();
        });


        binding.btnSubmit.setOnClickListener(v -> {
            if (sharedPreferencesHelper.getBoolean("dataSet", false)!=true) {
                String name = binding.editTextName.getText().toString();
                String course = binding.course.getText().toString();
                String email = binding.emailId.getText().toString();
                String phone = binding.editTextPhone.getText().toString();
                String dob = binding.editTextDOB.getText().toString();
                String address = binding.address.getText().toString();

                if (!name.isEmpty() && !course.isEmpty() && !email.isEmpty() && !phone.isEmpty() && !dob.isEmpty()
                        && !address.isEmpty() && !gender.isEmpty()) {

                    dialog = new Dialog(getContext());
                    dialog.setContentView(R.layout.dialog);
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.show();

                    Map<String, Object> data = new HashMap<>();
                    data.put("Full Name", name);
                    data.put("Gender", gender);
                    data.put("Course", course);
                    data.put("Email id", email);
                    data.put("Phone", phone);
                    data.put("Date of birth", dob);
                    data.put("Address", address);

                    myRef.child("Admission Data").child(user.getUid()).setValue(data).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            binding.editTextName.setText("");
                            binding.editTextPhone.setText("");
                            binding.editTextDOB.setText("");
                            binding.address.setText("");
                            dialog.dismiss();
                            sharedPreferencesHelper.saveBoolean("dataSet", true);
                            Toast.makeText(getContext(), "Done", Toast.LENGTH_SHORT).show();
                        } else {
                            dialog.dismiss();
                            try {
                                throw Objects.requireNonNull(task.getException());
                            }
                            catch (FirebaseNetworkException networkException) {
                                Toast.makeText(getActivity(), "Turn on your internet", Toast.LENGTH_SHORT).show();
                            }
                            catch (Exception e){
                                Toast.makeText(getActivity(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }

                    });

                } else {
                    Toast.makeText(getActivity(), "Some fields are empty", Toast.LENGTH_SHORT).show();
                }

            }
            else{
                Toast.makeText(getActivity(), "You are already request for admission", Toast.LENGTH_SHORT).show();
            }
        });


        return binding.getRoot();
    }


    @Override
    public void onDestroy() {
        binding = null;
        super.onDestroy();
    }


}