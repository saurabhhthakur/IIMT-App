package com.example.iimtaligarh.fragments;

import static androidx.core.content.ContextCompat.getSystemService;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.iimtaligarh.R;
import com.example.iimtaligarh.databinding.FragmentContactBinding;
import com.example.iimtaligarh.images.ImageLoadTask;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Contact_Fragment extends Fragment {

    private ImageLoadTask imageLoadTask;
    FirebaseUser user;
    private final static String FILE_NAME = "CollegePic" + ".PNG";
    private String url;
    private DatabaseReference myRef;
    FragmentContactBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().getWindow().setStatusBarColor(ContextCompat.getColor(getContext(), R.color.login_background));
        binding = FragmentContactBinding.inflate(getLayoutInflater());

        user = FirebaseAuth.getInstance().getCurrentUser();
        myRef = FirebaseDatabase.getInstance().getReference();

        binding.messageBtn.setOnClickListener(v -> {
            sendData();
        });

        fetchUrlFromStorage();

        return binding.getRoot();
    }

    void sendData() {
        if (!binding.FullName.getText().toString().isEmpty() && !binding.email.getText().toString().isEmpty() && !binding.message.getText().toString().isEmpty()) {
            Map<String, Object> data = new HashMap<>();
            data.put("Full Name", binding.FullName.getText().toString());
            data.put("Email id", binding.email.getText().toString());
            data.put("Message", binding.message.getText().toString());

            myRef.child("Feedback").child(binding.FullName.getText().toString()).setValue(data).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(getActivity(), "your feedback send...", Toast.LENGTH_SHORT).show();
                    binding.email.setText("");
                    binding.FullName.setText("");
                    binding.message.setText("");
                }
            });
        } else
            Toast.makeText(getActivity(), "Some fields are empty", Toast.LENGTH_SHORT).show();


    }


    private void fetchUrlFromStorage() {
        if (fileExist()) {
            Bitmap bitmap = null;
            InputStream inputStream = null;
            try {
                inputStream = getContext().openFileInput(FILE_NAME);
                bitmap = BitmapFactory.decodeStream(inputStream);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            binding.progressBar.setVisibility(View.INVISIBLE);
            binding.coverImg.setImageBitmap(bitmap);
        } else {
            StorageReference storageRef = FirebaseStorage.getInstance().getReference();
            StorageReference dateRef = storageRef.child("ImageSlider/one.jpg");
            dateRef.getDownloadUrl().addOnSuccessListener(uri -> {
                url = uri.toString();
                imageLoadTask = new ImageLoadTask(url,
                        binding.coverImg, getContext(), binding.progressBar, "CollegePic.PNG");
                imageLoadTask.execute();
            }).addOnFailureListener(e -> Toast.makeText(getContext(),
                    "Cant load url \n" + e, Toast.LENGTH_LONG).show());
        }

    }

    private boolean fileExist() {
        File path = getContext().getFilesDir();
        File file = new File(path, FILE_NAME);
        return file.exists();
    }


    @Override
    public void onDestroy() {
        binding = null;
        super.onDestroy();
    }
}