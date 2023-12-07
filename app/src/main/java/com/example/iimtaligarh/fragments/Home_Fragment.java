package com.example.iimtaligarh.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.iimtaligarh.images.ImageLoadTask;
import com.example.iimtaligarh.imageslider.SliderAdapterExample;
import com.example.iimtaligarh.databinding.FragmentHomeBinding;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class Home_Fragment extends Fragment {

    private FragmentHomeBinding binding;
    private SliderAdapterExample adapter;
    private final static String FILE_NAME = "FounderImage" + ".PNG";
    private String url;
    private ImageLoadTask imageLoadTask;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        requireActivity().getWindow().setStatusBarColor(ContextCompat.getColor(requireContext(),
                android.R.color.holo_red_dark));
        binding = FragmentHomeBinding.inflate(getLayoutInflater(), container, false);

        binding.marquee.setSingleLine(true);
        binding.marquee.setSelected(true);

        fetchUrlFromStorage();
        binding.map.setOnClickListener(v -> openMap());
        setData();
        return binding.getRoot();
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
            binding.founderImage.setImageBitmap(bitmap);
            }
            else {
            StorageReference storageRef = FirebaseStorage.getInstance().getReference();
            StorageReference dateRef = storageRef.child("profile/founder.jpg");
            dateRef.getDownloadUrl().addOnSuccessListener(uri -> {
                url = uri.toString();
                imageLoadTask = new ImageLoadTask(url,
                        binding.founderImage, getContext(), binding.progressBar,"FounderImage.PNG");
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

    public void setData() {
        adapter = new SliderAdapterExample(getContext());
        binding.imageSlider.setSliderAdapter(adapter);
        binding.imageSlider.setIndicatorAnimation(IndicatorAnimationType.WORM);
        binding.imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        binding.imageSlider.setAutoCycle(true);
        binding.imageSlider.startAutoCycle();
    }

    @Override
    public void onPause() {
        super.onPause();
        binding.imageSlider.setAutoCycle(false);
        binding.imageSlider.stopAutoCycle();
    }

    private void openMap() {
        Uri uri = Uri.parse("geo:0,0?q=INSTITUTE OF INFORMATION MANAGEMENT & TECHNOLOGY, Panchsheel Colony, Ramghat Road, Aligarh, Uttar Pradesh 202001");
        Intent i = new Intent(Intent.ACTION_VIEW, uri);
        i.setPackage("com.google.android.apps.maps");
        startActivity(i);
    }

}